package myoffers.prasad.com.myoffers.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import myoffers.prasad.com.myoffers.R;

public class SignupActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "SignupActivity";
    int PLACE_PICKER_REQUEST = 1;
    private GoogleApiClient mGoogleApiClient;

    @BindView(R.id.input_mobile_number)
    EditText _mobileNumber;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.input_store_name)
    EditText _storeName;
    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_store_address)
    EditText _storeAddress;
    @BindView(R.id.input_store_location)
    EditText _storeLocation;
    @BindView(R.id.btn_signup)
    Button _signupButton;
    @BindView(R.id.link_login)
    TextView _loginLink;

    //Variables
    double latitude;
    double longitude;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        final String mobileNumber = _mobileNumber.getText().toString();
        final String password = _passwordText.getText().toString();
        final String storeName = _storeName.getText().toString();
        final String email = _emailText.getText().toString();
        final String storeAddress = _storeAddress.getText().toString();
        final double latitude = this.latitude;
        final double longitude = this.longitude;
        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed 
                        // depending on success
                        /*if (signupMerchant(mobileNumber, password, storeName, email, storeAddress, latitude, longitude)) {
                            onSignupSuccess();
                        } else
                            onSignupFailed();*/
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public boolean signupMerchant(String mobileNumber, String password, String storeName, String emailID, String storeAddress, double latitude, double longitude) {
        boolean status = false;
        AndroidNetworking.post("URL for Merchant Signup")
                .addBodyParameter("mobileNo", mobileNumber)
                .addBodyParameter("password", password)
                .addBodyParameter("storeName", storeName)
                .addBodyParameter("emailID", emailID)
                .addBodyParameter("storeAddress", storeAddress)
                .addBodyParameter("latitude", latitude+"")
                .addBodyParameter("longitude", longitude+"")
                .setTag("Merchant SignUp")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        try {
                            if(response.get("status").equals("SUCCESS")){

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG,"Encountered error while sign up: " + e.getMessage());
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e(TAG,"Encountered error while sign up:" + error.getMessage() );
                    }
                });
        return status;
    }

    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "SignUp failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String mobileNumber = _mobileNumber.getText().toString();
        String password = _passwordText.getText().toString();
        String storeName = _storeName.getText().toString();
        String email = _emailText.getText().toString();
        String storeAddress = _storeAddress.getText().toString();
        String storeLocation = _storeLocation.getText().toString();


        if (mobileNumber.isEmpty() || !Patterns.PHONE.matcher(mobileNumber).matches()) {
            _mobileNumber.setError("invalid phone number");
            valid = false;
        } else {
            _mobileNumber.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (storeName.isEmpty() || storeName.length() < 3) {
            _storeName.setError("at least 3 characters");
            valid = false;
        } else {
            _storeName.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (storeAddress.isEmpty()) {
            _storeAddress.setError("can not be empty");
            valid = false;
        } else {
            _storeAddress.setError(null);
        }

        if (storeLocation.isEmpty()) {
            _storeLocation.setError("can not be empty");
            valid = false;
        } else {
            _storeLocation.setError(null);
        }

        return valid;
    }

    public void showPlacePicker(View v) {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        //builder.setLatLngBounds(new LatLngBounds());
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String placeLatLng = place.getLatLng().toString();
                //Toast.makeText(this, placeLatLng, Toast.LENGTH_LONG).show();
                _storeLocation.setText(placeLatLng);

                this.latitude = place.getLatLng().latitude;
                this.longitude = place.getLatLng().longitude;
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, connectionResult.getErrorMessage() + "", Toast.LENGTH_LONG).show();
    }
}