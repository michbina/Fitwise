package cepheustheapp.com.cepheus;
import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ForgotPassword extends Activity implements View.OnClickListener{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);//Display

        Button Send =(Button) findViewById(R.id.send);
        Send.setOnClickListener(this);

        TextView a = (TextView) findViewById(R.id.ForgotText);
        a.setText(R.string.forgotPassword);

    }
    @Override
    public void onClick(View v) {
        View parent = (View) v.getParent();

        switch (v.getId()) {
            case R.id.send:
                Intent Menu = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(Menu);
                break;
        }


    }
}
