package cepheustheapp.com.cepheus;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Chest extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chest);

        final Button Exo1Button = (Button) findViewById(R.id.button_exo_1);
        final Button Exo2Button = (Button) findViewById(R.id.button_exo_2);
        final Button Exo3Button = (Button) findViewById(R.id.button_exo_3);
        final Button Exo4Button = (Button) findViewById(R.id.button_exo_4);

        Exo1Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent Back_1_Activity = new Intent(Chest.this, Exo_1_Chest.class);
                startActivity(Back_1_Activity);

            }

        });
        Exo2Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent Back_2_Activity = new Intent(Chest.this, Exo_2_Chest.class);
                startActivity(Back_2_Activity);

            }


        });
        Exo3Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent Back_3_Activity = new Intent(Chest.this, Exo_3_Chest.class);
                startActivity(Back_3_Activity);

            }


        });

        Exo4Button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent Back_4_Activity = new Intent(Chest.this, Exo_4_Chest.class);
                startActivity(Back_4_Activity);

            }


        });
    }
}
