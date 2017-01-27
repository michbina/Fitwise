package cepheustheapp.com.cepheus;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Routine  extends Activity   {
    CheckBox Routine1;
    CheckBox Routine2;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routine2);
        Routine1 = (CheckBox) findViewById(R.id.checkbox_R1);
        Routine2 = (CheckBox) findViewById(R.id.checkbox_R2);



        final Button Routine1Button = (Button) findViewById(R.id.buttonStartWork);
        Routine1Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if ((Routine1.isChecked() )) {
                    Intent R1Activity = new Intent(Routine.this, Routine_1.class);
                    startActivity(R1Activity);
                }
                if(Routine1.isChecked()==false && Routine2.isChecked()==false ){
                    Toast.makeText(Routine.this,
                            R.string.Checked_error,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                return;

            }

        });
    }

    public void onCheckboxClicked(View view) {

        /*Routine1 = (CheckBox) findViewById(R.id.checkbox_R1);
        Routine2 = (CheckBox) findViewById(R.id.checkbox_R2);*/
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.checkbox_R1:
                if (checked) {
                    Routine2.setChecked(false);
                    Routine1.setChecked(true);

                }
                break;
            case R.id.checkbox_R2:
                if (checked) {
                    Routine1.setChecked(false);
                    Routine2.setChecked(true);
                }
                break;
        }
    }






}
