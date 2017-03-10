package cepheustheapp.com.cepheus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.security.KeyStore;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Lio on 2016-12-18.
 */
public class RegistrationForm extends Activity implements View.OnClickListener {

    EditText FirstName;
    EditText LastName;
    EditText Email;
    EditText Username;
    EditText Password;
    EditText Height;
    EditText Weight;
    EditText Age;
    EditText Authentication;
    CheckBox GenderM;
    CheckBox GenderF;

    Button result;
    private Button b = null;
    private CheckBox c1 = null;
    private CheckBox c2 = null;
    NumberPicker NoPickerWeight = null;
    NumberPicker NoPickerHeight = null;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    private static final String auth [] = {"BBYUCKFB","S5POBW1B","XYW7KF33","8F9UKIGZ","S79WR3RF","0JPXJ9J6","OK0J8AQ8","N5CF37FD"};



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView text = new TextView(this);
        setContentView(R.layout.registrationform);

        result = (Button) findViewById(R.id.SignUp);
        FirstName = (EditText)findViewById(R.id.Firstname);
        LastName = (EditText)findViewById(R.id.Lastname);
        Email = (EditText)findViewById(R.id.Email);
        //Height = (EditText)findViewById(R.id.Height);
        //Weight = (EditText)findViewById(R.id.Weight);
        Age = (EditText)findViewById(R.id.Age);
        Password = (EditText)findViewById(R.id.Password);
        Username = (EditText)findViewById(R.id.Username);
        Authentication = (EditText)findViewById(R.id.Auth);
        GenderF = (CheckBox) findViewById(R.id.GenderF);
        GenderM = (CheckBox) findViewById(R.id.GenderM);

        b = (Button) findViewById(R.id.SignUp);
        c1 = (CheckBox) findViewById(R.id.GenderM);
        c2 = (CheckBox) findViewById(R.id.GenderF);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        // b.setOnTouchListener(this);
        b.setOnClickListener(this);
        c1.setOnClickListener(this);
        c2.setOnClickListener(this);
        //result.setOnClickListener(this);



    }
    //test variable, displayed in the console
   /* @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        String chaine = FirstName.getText().toString();
        String chaine1 = LastName.getText().toString();
        String chaine2 = Email.getText().toString();
        String chaine3 = Password.getText().toString();
        Boolean chaine4 = GenderF.isChecked();
        Boolean chaine5 = GenderM.isChecked();
        Log.v("Essai",chaine);
        Log.v("Essai",chaine1);
        Log.v("Essai",chaine2);
        Log.v("Essai",chaine3);
        Log.v("", Boolean.toString(chaine4));
        Log.v("", Boolean.toString(chaine5));
        //Log.v("Essai",chaine6);
    }*/

    public void onClick(View v) {

        boolean error=false;

        switch (v.getId()) {
            case R.id.GenderM:
                if(c2.isChecked())
                {
                    c2.toggle();
                }
                break;
            case R.id.GenderF:
                if(c1.isChecked())
                {
                    c1.toggle();
                }
                break;
            case R.id.SignUp:

                //result.setText("OK");
                String SFirstName = FirstName.getText().toString();
                String SLastName = LastName.getText().toString();
                String SPassword = Password.getText().toString();
                String SUsername = Username.getText().toString();
                String SAuthentication = Authentication.getText().toString();
                Boolean Female = GenderF.isChecked();
                Boolean Male = GenderM.isChecked();
                String SEmail = Email.getText().toString();
                double SWeight=0;
                double SHeight=0;
                //Integer SHeight = 0;
                //Integer SWeight = 0;
                Integer SAge = 0;
                Integer i;
                Boolean j = false;
                //Convert String in Integer
                /*if(!Height.getText().toString().equals(""))
                {
                    SHeight = Integer.parseInt( Height.getText().toString() );
                }
                else
                {
                    Toast.makeText(RegistrationForm.this,
                            R.string.Height_empty,
                            Toast.LENGTH_SHORT).show();
                    error=true;
                }
                if(!Weight.getText().toString().equals(""))
                {
                    SWeight = Integer.parseInt( Weight.getText().toString() );
                }
                else
                {
                    Toast.makeText(RegistrationForm.this,
                            R.string.Weight_empty,
                            Toast.LENGTH_SHORT).show();
                    error=true;
                }*/

                // Integer SWeight = Integer.parseInt( Weight.getText().toString() );
                if(!Age.getText().toString().equals(""))
                {
                    SAge = Integer.parseInt( Age.getText().toString() );
                }
                else
                {
                    Toast.makeText(RegistrationForm.this,
                            R.string.Age_empty,
                            Toast.LENGTH_SHORT).show();
                    error=true;
                }

                if (SFirstName.equals("")) {
                    Toast.makeText(RegistrationForm.this,
                            R.string.Firstname_empty,
                            Toast.LENGTH_SHORT).show();
                    error=true;
                }

                if (SLastName.equals("")) {
                    Toast.makeText(RegistrationForm.this,
                            R.string.Lastname_empty,
                            Toast.LENGTH_SHORT).show();
                    error=true;
                }
                if (SEmail.equals("")) {
                    Toast.makeText(RegistrationForm.this,
                            R.string.Email_empty,
                            Toast.LENGTH_SHORT).show();
                    error=true;
                }

                Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
                Matcher m = p.matcher(SEmail);
                if (!m.matches()) {
                    // Toast est une classe fournie par le SDK Android
                    // pour afficher les messages (indications) à l'intention de
                    // l'utilisateur. Ces messages ne possédent pas d'interaction avec l'utilisateur
                    // Le premier argument représente le contexte, puis
                    // le message et à la fin la durée d'affichage du Toast (constante
                    // LENGTH_SHORT ou LENGTH_LONG). Sans oublier d'appeler la méthode
                    //show pour afficher le Toast
                    Toast.makeText(RegistrationForm.this, R.string.email_format_error,
                            Toast.LENGTH_SHORT).show();
                    error=true;
                }

                if (SPassword.equals("")) {
                    Toast.makeText(RegistrationForm.this,
                            R.string.password_empty,
                            Toast.LENGTH_SHORT).show();
                    error=true;
                }


                if ((Male == true && Female == true) || (Male == false && Female == false)) {

                    Toast.makeText(RegistrationForm.this,
                            R.string.Gender_error,
                            Toast.LENGTH_SHORT).show();
                    error=true;
                }
                if (SUsername.equals("")) {
                    Toast.makeText(RegistrationForm.this,
                            R.string.Username_empty,
                            Toast.LENGTH_SHORT).show();
                    error=true;
                }


                for(i=0;i<auth.length;i++)
                {
                    if(auth[i].equals(SAuthentication))
                    {
                        j=true;
                    }
                }
                if(j==false)
                {
                    error=true;
                    Toast.makeText(RegistrationForm.this,
                            R.string.Authentication_code_incorrect,
                            Toast.LENGTH_SHORT).show();
                }

                if(error!=true)
                {
                try {
                    HttpRequest req = HttpRequest.getInstance();
                    req.Signup(SUsername,SPassword,SEmail,"","",SFirstName,SLastName,SAge,SWeight,SHeight);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("username", "Welcome, "+SUsername); // Storing string
                    editor.putString("username",SUsername); // Storing string
                    editor.commit();
                    Intent SignUpActivity = new Intent(getApplicationContext(), Menu.class);
                    startActivity(SignUpActivity);

                }catch (Exception e)
                    {

                    }

                }


                break;
        }
    }
}
