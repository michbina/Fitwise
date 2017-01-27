package cepheustheapp.com.cepheus;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Exo_2_Chest extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incline_press);
        final Button Video_incline_pressButton = (Button) findViewById(R.id.Video_incline_press);
        Video_incline_pressButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String url = "https://www.youtube.com/watch?v=DbFgADa2PL8&t=168s";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }


        });
    }
}
