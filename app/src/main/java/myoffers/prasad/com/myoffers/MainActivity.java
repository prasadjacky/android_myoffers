package myoffers.prasad.com.myoffers;

import android.content.res.Resources;
import android.graphics.Rect;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;

public class MainActivity extends AppCompatActivity implements OffersCardAdapter.OffersAdapterListener {
    private static final String TAG = "MainActivity";
    String[] nameArray = {"Dog", "Gorilla", "Alligator", "Butterfly", "Dragon", "German Shepherd",
            "Dog", "Gorilla", "Alligator", "Butterfly", "Dragon", "German Shepherd"};

    String[] infoArray = {
            "Our loyal friend",
            "Debut in King Kong",
            "Beware of Jaws",
            "Fly away you!",
            "Breathes fire",
            "Another loyal friend",
            "Our loyal friend",
            "Debut in King Kong",
            "Beware of Jaws",
            "Fly away you!",
            "Breathes fire",
            "Another loyal friend"
    };

    /*Integer[] imageArray = {R.drawable.dog,
            R.drawable.gorilla,
            R.drawable.alligator,
            R.drawable.butterfly,
            R.drawable.dragon,
            R.drawable.germanshepherd,
            R.drawable.dog,
            R.drawable.gorilla,
            R.drawable.alligator,
            R.drawable.butterfly,
            R.drawable.dragon,
            R.drawable.germanshepherd};*/
    Integer[] imageArray = {R.drawable.dog_outline,
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

   /* @BindView(R.id.lvOffers)
    ListView listView;*/

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    OffersCardAdapter adapter;
    List<MyOffer> offersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_card);
        ButterKnife.bind(this);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        /*OffersListAdapter whatever = new OffersListAdapter(this, nameArray, infoArray, imageArray);

        listView.setAdapter(whatever);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(MainActivity.this, OfferDetails.class);
                String message = nameArray[position];
                intent.putExtra("animal", message);
                startActivity(intent);
            }
        });*/
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        offersList = new ArrayList<>();
        adapter = new OffersCardAdapter(this, offersList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "You selected "+position+1+" offer..",Toast.LENGTH_SHORT);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        prepareAlbums();

/*        try {
            Glide.with(this).load(R.drawable.red_vector_bg).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "Login Success");
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
    private void prepareAlbums() {
        int[] covers = new int[]{R.drawable.dog_outline,
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
    }

    @Override
    public void onAddToFavoriteSelected(int position) {

    }

    @Override
    public void onNotInterestedSelected(int position) {

    }

    @Override
    public void onCardSelected(int position, ImageView thumbnail) {
        Toast.makeText(this, "You selected "+position+1+" offer",Toast.LENGTH_SHORT);
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
}
