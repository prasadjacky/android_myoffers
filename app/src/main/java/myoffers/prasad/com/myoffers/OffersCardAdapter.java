package myoffers.prasad.com.myoffers;

/**
 * Created by prasad on 9/3/18.
 */

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OffersCardAdapter extends RecyclerView.Adapter<OffersCardAdapter.MyViewHolder> implements Filterable {

    private final String TAG = "OfferCardAdapter";
    private Context mContext;
    private List<MyOffer> offersList;
    private List<MyOffer> offersListFiltered;
    private final ClickListener listener;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    offersListFiltered = offersList;
                } else {
                    List<MyOffer> filteredList = new ArrayList<>();
                    for (MyOffer row : offersList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getCaption().toLowerCase().contains(charString.toLowerCase()) || row.getCategory().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    offersListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = offersListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                offersListFiltered = (ArrayList<MyOffer>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView offerName, merchantID, category, validity;
        public ImageView thumbnail, overflow;
        private WeakReference<ClickListener> listenerRef;

        public MyViewHolder(View view, ClickListener listener) {
            super(view);
            listenerRef = new WeakReference<>(listener);

            offerName = view.findViewById(R.id.offerName);
            merchantID = view.findViewById(R.id.merchantID);
            category = view.findViewById(R.id.offerCategory);
            validity = view.findViewById(R.id.offerValidity);
            thumbnail = view.findViewById(R.id.thumbnail);
            overflow = view.findViewById(R.id.overflow);

            thumbnail.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == thumbnail.getId()) {
                listenerRef.get().onPositionClicked(getAdapterPosition());
            }
        }
    }


    public OffersCardAdapter(Context mContext, List<MyOffer> offersList, ClickListener listener) {
        this.mContext = mContext;
        this.offersList = offersList;
        this.offersListFiltered = offersList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offers_card_view, parent, false);

        return new MyViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        MyOffer offer = offersListFiltered.get(position);
        holder.offerName.setText(offer.getCaption());
        holder.merchantID.setText(offer.getMerchantID());
        holder.category.setText(offer.getCategory());
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("dd/MM/yyyy");
        holder.validity.setText("Valid Till " + simpleDateFormat.format(offer.getValidTo()).toString());

        // loading album cover using Glide library
        Glide.with(mContext).load(offer.getThumbnail()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
        /*holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCardSelected(position, holder.thumbnail);
                Log.i(TAG, "Clicked "+position);
            }
        });*/
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_offer, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_not_interested:
                    Toast.makeText(mContext, "Removing offer", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return offersListFiltered.size();
    }

    public interface ClickListener {

        void onPositionClicked(int position);

        void onLongClicked(int position);
    }
}
