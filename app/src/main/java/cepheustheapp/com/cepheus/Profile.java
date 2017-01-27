package cepheustheapp.com.cepheus;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Lio on 2016-12-19.
 */
public class Profile extends Activity{



    private TextView myText = null;
    TextView myAwesomeTextView;
    TextView text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);


    }

}
