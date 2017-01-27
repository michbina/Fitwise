package cepheustheapp.com.cepheus;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Exo_1_Chest extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bench_press);
        final Button Video_bench_pressButton = (Button) findViewById(R.id.Video_bench_press);
        Video_bench_pressButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String url = "https://www.youtube.com/watch?v=TPqdrWcffdk";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }


        });
    }
}
