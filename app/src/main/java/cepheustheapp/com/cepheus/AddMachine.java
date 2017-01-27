package cepheustheapp.com.cepheus;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Lio on 2016-12-19.
 */
public class AddMachine extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView text = new TextView(this);
        setContentView(R.layout.addmachine);

    }
}
