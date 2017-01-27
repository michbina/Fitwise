package cepheustheapp.com.cepheus;

import android.app.Activity;
import android.content.Intent;
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
public class RegistrationForm extends Activity implements View.OnTouchListener,View.OnClickListener {

    EditText FirstName;
    EditText LastName;
    EditText Email;
    EditText Username;
    EditText Password;
    CheckBox GenderM;
    CheckBox GenderF;

    Button result;
    private Button b = null;
    NumberPicker NoPickerWeight = null;
    NumberPicker NoPickerHeight = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView text = new TextView(this);
        setContentView(R.layout.registrationform2);

        NoPickerWeight = (NumberPicker)findViewById(R.id.numberPickerWeight);
        NoPickerWeight.setMaxValue(200);
        NoPickerWeight.setMinValue(0);
        NoPickerWeight.setValue(70);
        NoPickerWeight.setWrapSelectorWheel(false);
        NoPickerHeight = (NumberPicker)findViewById(R.id.numberPickerHeight);
        NoPickerHeight.setMaxValue(250);
        NoPickerHeight.setMinValue(0);
        NoPickerHeight.setValue(150);
        NoPickerHeight.setWrapSelectorWheel(false);


        result = (Button) findViewById(R.id.SignUp);
        FirstName = (EditText)findViewById(R.id.FirstName);
        LastName = (EditText)findViewById(R.id.LastName);
        Email = (EditText)findViewById(R.id.Email);
        Password = (EditText)findViewById(R.id.Password);
        Username = (EditText)findViewById(R.id.Username);
        GenderF = (CheckBox) findViewById(R.id.GenderF);
        GenderM = (CheckBox) findViewById(R.id.GenderM);

        b = (Button) findViewById(R.id.SignUp);
        b.setOnTouchListener(this);
        b.setOnClickListener(this);
        //result.setOnClickListener(this);



    }
    //test variable, displayed in the console
    @Override
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
    }

    public boolean onTouch(View v, MotionEvent event) {

        boolean error=false;

        switch (v.getId()) {
            case R.id.SignUp:

                //result.setText("OK");
                String SFirstName = FirstName.getText().toString();
                String SLastName = LastName.getText().toString();
                String SPassword = Password.getText().toString();
                String SUsername = Username.getText().toString();
                Boolean Female = GenderF.isChecked();
                Boolean Male = GenderM.isChecked();
                String SEmail = Email.getText().toString();


                if (SFirstName.equals("")) {
                    Toast.makeText(RegistrationForm.this,
                            R.string.Firstname_empty,
                            Toast.LENGTH_SHORT).show();
                    return true;
                }

                if (SLastName.equals("")) {
                    Toast.makeText(RegistrationForm.this,
                            R.string.Lastname_empty,
                            Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (SEmail.equals("")) {
                    Toast.makeText(RegistrationForm.this,
                            R.string.Email_empty,
                            Toast.LENGTH_SHORT).show();
                    return true;
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
                    return true;
                }

                if (SPassword.equals("")) {
                    Toast.makeText(RegistrationForm.this,
                            R.string.password_empty,
                            Toast.LENGTH_SHORT).show();
                    return true;
                }


                if ((Male == true && Female == true) || (Male == false && Female == false)) {

                    Toast.makeText(RegistrationForm.this,
                            R.string.Gender_error,
                            Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (SUsername.equals("")) {
                    Toast.makeText(RegistrationForm.this,
                            R.string.Username_empty,
                            Toast.LENGTH_SHORT).show();
                    return true;
                }
                Intent SignUpActivity = new Intent(getApplicationContext(), Menu.class);
                startActivity(SignUpActivity);


                break;
        }
        return true;
    }


}
