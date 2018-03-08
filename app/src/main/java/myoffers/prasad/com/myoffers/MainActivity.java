package myoffers.prasad.com.myoffers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.BindView;
public class MainActivity extends AppCompatActivity {
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

    @BindView(R.id.lvOffers)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        OffersListAdapter whatever = new OffersListAdapter(this, nameArray, infoArray, imageArray);

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
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "Login Success");
    }
}
