package cepheustheapp.com.cepheus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Program extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program);
        final Button BackButton = (Button) findViewById(R.id.button_back);
        final Button ChestButton = (Button) findViewById(R.id.button_chest);
        final Button BicepsButton = (Button) findViewById(R.id.button_biceps);
        final Button TricepsButton = (Button) findViewById(R.id.button_triceps);
        final Button LegsButton = (Button) findViewById(R.id.button_legs);
        final Button ShouldersButton = (Button) findViewById(R.id.button_shoulders);
        BackButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent BackActivity = new Intent(Program.this, Back.class);
                startActivity(BackActivity);

            }

        });
        ChestButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent ChestActivity = new Intent(Program.this, Chest.class);
                startActivity(ChestActivity);

            }

        });
    }
}
