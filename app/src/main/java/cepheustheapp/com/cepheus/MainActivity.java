package cepheustheapp.com.cepheus;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    //Define button
    private Button b = null;
    private Button b1 = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//Display
        //Create 2 buttons SignUp and Create an account
        b = (Button) findViewById(R.id.create_Account);
        b.setOnClickListener(this);

        b1 = (Button) findViewById(R.id.logIn);

        b1.setOnClickListener(this);
    }
    /*
    @Override
    public boolean onTouch(View v, MotionEvent event) {
    //Listener, when user touch a button this function is called
        //Detect which button has been touched
        switch (v.getId()) {
            case R.id.create_Account:
                Intent RegistrationActivity = new Intent(getApplicationContext(), RegistrationForm.class);
                startActivity(RegistrationActivity);//Launch another activity, the class must be declare in the AndroidManifest.xml
                break;
            case R.id.logIn:
                Intent LogInActivity = new Intent(getApplicationContext(), Menu.class);
                startActivity(LogInActivity);//Launch another activity, the class must be declare in the AndroidManifest.xml
                break;
        }
        return true;
    }*/
    //Useless ...
    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.create_Account:
                Intent RegistrationActivite2 = new Intent(getApplicationContext(), RegistrationForm.class);
                startActivity(RegistrationActivite2);
                break;
            case R.id.logIn:
                Intent LogInActivity = new Intent(getApplicationContext(), Menu.class);
                startActivity(LogInActivity);//Launch another activity, the class must be declare in the AndroidManifest.xml
                break;


        }
    }
}
