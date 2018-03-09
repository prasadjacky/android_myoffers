package myoffers.prasad.com.myoffers;

import android.view.View;

/**
 * Created by prasad on 9/3/18.
 */

public interface ClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
