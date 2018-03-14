package myoffers.prasad.com.myoffers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
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
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewOfferActivity extends AppCompatActivity {

    final static String TAG = "NewOfferActivity";

    @BindView(R.id.input_offer_caption)
    EditText etOfferCaption;
    //@BindView(R.id.input_offer_validity)
    static EditText etOfferValidity;
    @BindView(R.id.input_offer_category)
    EditText etOfferCategory;
    @BindView(R.id.input_offer_desc)
    EditText etOfferDesc;
    @BindView(R.id.btn_create_offer)
    Button btnCreateOffer;
    @BindView(R.id.view_new_offer)
    ScrollView createOfferView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_offer);
        ButterKnife.bind(this);
        etOfferValidity = findViewById(R.id.input_offer_validity);

        //Create Offer
        btnCreateOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createOffer();
            }
        });
    }

    private void createOffer() {
        if (!validate()) {
            createOfferFailed();
            return;
        }

        btnCreateOffer.setEnabled(false);
        //API call to create offer
        String offerCaption = etOfferCaption.getText().toString();
        Date offerValidity = null;
        try {
            SimpleDateFormat format =
                    new SimpleDateFormat("dd/MM/yyyy");
            if (!etOfferValidity.getText().toString().isEmpty()) {
                offerValidity = format.parse(etOfferValidity.getText().toString());
            } else {
                offerValidity = null;
                Log.i(TAG, "Setting valid till to null");
            }
        } catch (ParseException pe) {
            Log.e(TAG, "Couldn't parse date");
            Snackbar.make(createOfferView, "Invalid date format. Try dd/mm/yyyy", Snackbar.LENGTH_SHORT).show();
        }
        String offerCategory = etOfferCategory.getText().toString();
        String offerDesc = etOfferDesc.getText().toString();
        MyOffer myOffer = new MyOffer("Merchant1", offerCaption, offerCategory, offerDesc, offerValidity, offerValidity, R.drawable.alligator_outline);
        Log.i(TAG, myOffer.getCaption());
        Intent intent = new Intent();
        intent.putExtra("myOffer", myOffer);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void createOfferFailed() {
        //Snackbar.make(createOfferView, "Unable to create offer", Snackbar.LENGTH_SHORT).show();
        btnCreateOffer.setEnabled(true);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public boolean validate() {
        boolean valid = true;

        String offerCaption = etOfferCaption.getText().toString();
        Date offerValidity = null;
        try {
            SimpleDateFormat format =
                    new SimpleDateFormat("dd/MM/yyyy");
            if (!etOfferValidity.getText().toString().isEmpty()) {
                offerValidity = format.parse(etOfferValidity.getText().toString());
            } else {
                offerValidity = null;
                Log.i(TAG, "Setting valid till to null");
            }
        } catch (ParseException pe) {
            Log.e(TAG, "Couldn't parse date");
            Snackbar.make(createOfferView, "Invalid date format. Try dd/mm/yyyy", Snackbar.LENGTH_SHORT).show();
        }

        String offerCategory = etOfferCategory.getText().toString();
        String offerDesc = etOfferDesc.getText().toString();

        if (offerCaption.isEmpty() || offerCaption.length() < 3) {
            etOfferCaption.setError("at least 3 characters");
            valid = false;
        } else {
            etOfferCaption.setError(null);
        }

        if (offerValidity == null) {
            Log.i(TAG, "can not be empty");
            etOfferValidity.setError("can not be empty");
            valid = false;
        } else if (offerValidity.compareTo(new Date()) < 0) {
            Log.i(TAG, "can not be past date");
            etOfferValidity.setError("can not be past date");
            valid = false;
        } else {
            etOfferValidity.setError(null);
        }

        if (offerCategory.isEmpty() || offerCategory.length() < 3) {
            etOfferCategory.setError("at least 3 characters");
            valid = false;
        } else {
            etOfferCategory.setError(null);
        }

        if (offerDesc.isEmpty() || offerDesc.length() < 20) {
            etOfferDesc.setError("at least 20 characters");
            valid = false;
        } else {
            etOfferDesc.setError(null);
        }

        return valid;
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
