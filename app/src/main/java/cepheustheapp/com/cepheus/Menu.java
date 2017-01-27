package cepheustheapp.com.cepheus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by Lio on 2016-12-18.
 */
public class Menu extends Activity implements View.OnClickListener {
//Declare each button on this activity
    private Button bprofile = null;//Button to launch profile
    private Button bsettings = null;//Button to launch settings
    private Button bfreeworkout = null;
    private Button bprogram = null;
    private Button broutine = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        //Detect a touch on the buttons
        bprofile = (Button) findViewById(R.id.buttonProfile);
        bprofile.setOnClickListener(this);
        bsettings = (Button) findViewById(R.id.buttonSettings);
        bsettings.setOnClickListener(this);
        bfreeworkout = (Button) findViewById(R.id.buttonFreeWorkout);
        bfreeworkout.setOnClickListener(this);
        bprogram = (Button) findViewById(R.id.buttonProgram);
        bprogram.setOnClickListener(this);
        broutine = (Button) findViewById(R.id.buttonRoutine);
        broutine.setOnClickListener(this);

    }


    /*public boolean onTouch(View v, MotionEvent event) {

        switch (v.getId()) {
            case R.id.buttonProfile:
                //result.setText("OK");
                Intent ProfileActivity = new Intent(getApplicationContext(), Profile.class);
                startActivity(ProfileActivity);
                break;
            case R.id.buttonSettings:
                Intent SettingsActivity = new Intent(getApplicationContext(), Settings.class);
                startActivity(SettingsActivity);
                break;
            case R.id.buttonFreeWorkout:
                Intent FreeWorkoutActivity = new Intent(getApplicationContext(), QRcode.class);
                startActivity(FreeWorkoutActivity);
                break;
            case R.id.buttonProgram:
                Intent ProgramActivity = new Intent(getApplicationContext(), Program.class);
                startActivity(ProgramActivity);
                break;
            case R.id.buttonRoutine:
                Intent RoutineActivity = new Intent(getApplicationContext(), Routine.class);
                startActivity(RoutineActivity);
                break;
        }
        return true;
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonProfile:
                //result.setText("OK");
                Intent ProfileActivity = new Intent(getApplicationContext(), Profile.class);
                startActivity(ProfileActivity);
                break;
            case R.id.buttonSettings:
                Intent SettingsActivity = new Intent(getApplicationContext(), Settings.class);
                startActivity(SettingsActivity);
                break;
            case R.id.buttonFreeWorkout:
                Intent FreeWorkoutActivity = new Intent(getApplicationContext(), QRcode.class);
                startActivity(FreeWorkoutActivity);
                break;
            case R.id.buttonProgram:
                Intent ProgramActivity = new Intent(getApplicationContext(), Program.class);
                startActivity(ProgramActivity);
                break;
            case R.id.buttonRoutine:
                Intent RoutineActivity = new Intent(getApplicationContext(), Routine.class);
                startActivity(RoutineActivity);
                break;

    }}
}