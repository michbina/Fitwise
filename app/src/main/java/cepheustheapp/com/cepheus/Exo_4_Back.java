package cepheustheapp.com.cepheus;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Exo_4_Back extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rowing_bar_t);
        final Button Video_cable_rowButton = (Button) findViewById(R.id.Video_row_bar);
        Video_cable_rowButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String url = "https://www.youtube.com/watch?v=zRAnr9oOTmE";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }


        });

    }
}
