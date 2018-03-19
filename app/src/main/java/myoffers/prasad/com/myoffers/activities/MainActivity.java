package myoffers.prasad.com.myoffers.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;
import myoffers.prasad.com.myoffers.models.MyOffer;
import myoffers.prasad.com.myoffers.adapters.OffersCardAdapter;
import myoffers.prasad.com.myoffers.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static int REQUEST_NEW_OFFER = 0;

    //View binding
   /* @BindView(R.id.lvOffers)
    ListView listView;*/

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout mShimmerViewContainer;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.main_content)
    CoordinatorLayout main_content;

    //Variables
    OffersCardAdapter adapter;
    List<MyOffer> offersList;
    private SearchView searchView;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_card);
        ButterKnife.bind(this);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        offersList = new ArrayList<>();
        adapter = new OffersCardAdapter(this, offersList, new OffersCardAdapter.ClickListener() {
            @Override
            public void onPositionClicked(int position) {
              MyOffer myOffer = offersList.get(position);
                Intent intent = new Intent(MainActivity.this, OfferDetails.class);
                intent.putExtra("myOffer", myOffer);
                startActivity(intent);
            }

            @Override
            public void onLongClicked(int position) {

            }
        });

        // white background notification bar
        whiteNotificationBar(recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        /*recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                Log.i(TAG, view.getId() +"");
                *//*MyOffer myOffer = offersList.get(position);
                Intent intent = new Intent(MainActivity.this, OfferDetails.class);
                intent.putExtra("myOffer", myOffer);
                startActivity(intent);*//*
            }

            @Override
            public void onLongClick(View view, int position) {
                testRestCall();
            }
        }));*/
        prepareOffers();

/*        try {
            Glide.with(this).load(R.drawable.red_vector_bg).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewOfferActivity.class);
                startActivityForResult(intent, REQUEST_NEW_OFFER);
            }
        });
    }

    private void testRestCall() {
        AndroidNetworking.get("https://devops.ltimosaic.com/DevOpsPortalService/api/presentation/projects")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // do anything with response
                        Log.i(TAG, "JSON Response" + response.length());
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_NEW_OFFER) {
            if (resultCode == RESULT_OK) {
                MyOffer newOffer = (MyOffer) data.getSerializableExtra("myOffer");
                offersList.add(newOffer);
                adapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    /**
     * Adding few albums for testing
     */
    private void prepareOffers() {
        final int[] covers = new int[]{R.drawable.dog_outline,
                R.drawable.gorilla_outline,
                R.drawable.alligator_outline,
                R.drawable.butterfly_outline,
                R.drawable.dragon_outline,
                R.drawable.german_shepherd_outline,
                R.drawable.dog_outline,
                R.drawable.gorilla_outline,
                R.drawable.alligator_outline,
                R.drawable.butterfly_outline,
                R.drawable.dragon_outline,
                R.drawable.german_shepherd_outline};

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        Log.i(TAG, "Simulating JSON Load");
                        MyOffer a = new MyOffer("Merchant1", "Offer 1", "This is Offer 1", "Category 1", new Date(), new Date(), covers[0]);
                        offersList.add(a);

                        a = new MyOffer("Merchant2", "Offer 2", "This is Offer 1", "Category 1", new Date(), new Date(), covers[1]);
                        offersList.add(a);

                        a = new MyOffer("Merchant3", "Offer 3", "This is Offer 1", "Category 1", new Date(), new Date(), covers[2]);
                        offersList.add(a);

                        a = new MyOffer("Merchant4", "Offer 4", "This is Offer 1", "Category 1", new Date(), new Date(), covers[3]);
                        offersList.add(a);

                        a = new MyOffer("Merchant5", "Offer 5", "This is Offer 1", "Category 1", new Date(), new Date(), covers[4]);
                        offersList.add(a);

                        a = new MyOffer("Merchant6", "Offer 6", "This is Offer 1", "Category 1", new Date(), new Date(), covers[5]);
                        offersList.add(a);

                        a = new MyOffer("Merchant7", "Offer 7", "This is Offer 1", "Category 1", new Date(), new Date(), covers[6]);
                        offersList.add(a);

                        a = new MyOffer("Merchant8", "Offer 8", "This is Offer 1", "Category 1", new Date(), new Date(), covers[7]);
                        offersList.add(a);

                        a = new MyOffer("Merchant9", "Offer 9", "This is Offer 1", "Category 1", new Date(), new Date(), covers[8]);
                        offersList.add(a);

                        a = new MyOffer("Merchant10", "Offer 10", "This is Offer 1", "Category 1", new Date(), new Date(), covers[9]);
                        offersList.add(a);
                        adapter.notifyDataSetChanged();
                        // stop animating Shimmer and hide the layout
                        mShimmerViewContainer.stopShimmerAnimation();
                        mShimmerViewContainer.setVisibility(View.GONE);
                    }
                }, 1000);
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                Log.i(TAG, query);
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                Log.i(TAG, query);
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Snackbar.make(main_content, "Press BACK again to exit", Snackbar.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
