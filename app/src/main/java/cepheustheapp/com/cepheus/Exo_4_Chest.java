package cepheustheapp.com.cepheus;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Exo_4_Chest extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.peck_deck);
        final Button Video_peck_deckButton = (Button) findViewById(R.id.Video_peck_deck);
        Video_peck_deckButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String url = "https://www.youtube.com/watch?v=Qr7dstCeYtw";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }


        });
    }
}
