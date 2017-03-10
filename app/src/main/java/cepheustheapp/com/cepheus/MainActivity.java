package cepheustheapp.com.cepheus;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class MainActivity extends Activity implements android.view.View.OnClickListener {

    //Define button
    private Button b1 = null;
    EditText Username;
    EditText Password;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//Display

        Username = (EditText)findViewById(R.id.Username);
        Password = (EditText)findViewById(R.id.Password);
        TextView CreateAccount = (TextView) findViewById(R.id.create_Account);
        TextView PasswordForgot = (TextView) findViewById(R.id.forgot_password);
        CreateAccount.setOnClickListener(this);
        PasswordForgot.setOnClickListener(this);


        b1 = (Button) findViewById(R.id.logIn);
        b1.setOnClickListener(this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public void onClick(View v) {
        View parent = (View)v.getParent();

        switch(v.getId())
        {
            case R.id.create_Account:
                    Intent RegistrationActivite2 = new Intent(getApplicationContext(), RegistrationForm.class);
                    startActivity(RegistrationActivite2);
                break;
            case R.id.forgot_password:
                    Intent ForgotPassword = new Intent(getApplicationContext(),ForgotPassword.class);
                    startActivity(ForgotPassword);
                break;
            case R.id.logIn:
                String SUsername = Username.getText().toString();
                String SPassword = Password.getText().toString();
                try {
                        if(SignIn(SUsername, SPassword))
                        {
                            SharedPreferences.Editor editor = sharedpreferences.edit();

                            editor.putString("username", "Welcome, "+SUsername); // Storing string

                            editor.putString("username",SUsername); // Storing string


                            editor.commit();
                            Intent LogInActivity = new Intent(getApplicationContext(), Menu.class);
                            startActivity(LogInActivity);//Launch another activity, the class must be declare in the AndroidManifest.xml
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,
                                    "Username or password incorrect",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                catch(Exception e)
                {

                }
                break;
        }
    }


    private boolean SignIn(String username, String password) throws Exception{

        HttpRequest req = HttpRequest.getInstance();
        return req.login(username,password);
    }
}
