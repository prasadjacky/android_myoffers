package myoffers.prasad.com.myoffers.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import myoffers.prasad.com.myoffers.models.MyOffer;
import myoffers.prasad.com.myoffers.R;

public class OfferDetails extends AppCompatActivity {

    final static String TAG = "OfferDetails";

    //Views
    @BindView(R.id.input_offer_caption)
    EditText etOfferCaption;
    //@BindView(R.id.input_offer_validity)
    static EditText etOfferValidity;
    @BindView(R.id.input_offer_category)
    EditText etOfferCategory;
    @BindView(R.id.input_offer_desc)
    EditText etOfferDesc;
    @BindView(R.id.btn_update_offer)
    Button btnUpdateOffer;
    @BindView(R.id.btn_delete_offer)
    Button btnDeleteOffer;
    @BindView(R.id.view_new_offer)
    ScrollView createOfferView;

    //Variables
    MyOffer myOffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_details);
        ButterKnife.bind(this);
        etOfferValidity = findViewById(R.id.input_offer_validity);
        myOffer = (MyOffer) getIntent().getSerializableExtra("myOffer");
        Log.i(TAG, "My offer object " + myOffer);

        etOfferCaption.setText(myOffer.getCaption());

        try {
            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat("dd/MM/yyyy");
            if (!myOffer.getValidTo().toString().isEmpty()) {
                etOfferValidity.setText(simpleDateFormat.format(myOffer.getValidTo()));
            } else {
                Log.i(TAG, "Setting valid till to null");
            }
        } catch (Exception pe) {
            Log.e(TAG, "Couldn't parse date");
            Snackbar.make(createOfferView, "Invalid date format. Try dd/mm/yyyy", Snackbar.LENGTH_SHORT).show();
        }

        etOfferCategory.setText(myOffer.getCategory());
        etOfferDesc.setText(myOffer.getOfferDesc());

        btnUpdateOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Update clicked");
            }
        });

        btnDeleteOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Delete clicked");
            }
        });
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new NewOfferActivity.DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            etOfferValidity.setText(view.getDayOfMonth() + "/" + (view.getMonth() + 1) + "/" + view.getYear());
        }
    }
}
