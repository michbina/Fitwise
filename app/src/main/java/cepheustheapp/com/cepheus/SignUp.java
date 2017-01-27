package cepheustheapp.com.cepheus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Lio on 2016-12-18.
 */

public class SignUp extends Activity {

    private TextView myText = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView text = new TextView(this);
        setContentView(R.layout.signup);


    }


}


