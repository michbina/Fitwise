package cepheustheapp.com.cepheus;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class AddMachine extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView text = new TextView(this);
        setContentView(R.layout.addmachine);

    }
}
