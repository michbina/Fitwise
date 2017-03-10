package cepheustheapp.com.cepheus;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Freestyle_machine extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{


    private YouTubePlayerView youtubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    //public static final String YOUTUBE_API_KEY ="AIzaSyCqqMjoWO3TzBE97d87SyNH-o9TEOBEWio";
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    String Username;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freestyle_machine);
        youtubePlayerView= (YouTubePlayerView) findViewById(R.id.view);
        youtubePlayerView.initialize("AIzaSyCqqMjoWO3TzBE97d87SyNH-o9TEOBEWio",this);


        try{
            populate();
        }catch(Exception e){


        }
        final Button Scan_again = (Button) findViewById(R.id.Scan_again);
        final Button Menu = (Button) findViewById(R.id.Menu);

        Scan_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent QRActivity = new Intent(getApplicationContext(), QRcode.class);
                startActivity(QRActivity);
            }
        });
        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MenuActivity = new Intent(getApplicationContext(), Menu.class);
                startActivity(MenuActivity);
            }
        });

    }

    public void populate() throws Exception{

        try {
            SharedPreferences settings;
            settings = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            Username = settings.getString("username", null);
            ImageView image = (ImageView) findViewById(R.id.image);
            TextView machineName = (TextView) findViewById(R.id.machineName);



            TextView desc = (TextView) findViewById(R.id.desc);
            TextView step = (TextView) findViewById(R.id.step);

            HttpRequest req = HttpRequest.getInstance();

            //if (!req.isLoggedIn()) req.login(Username, "password");

            //Bitmap object = (Bitmap) req.getMachine(, "photo1" );
            //favorites = req.getFav(Username);

            JSONArray data = (JSONArray) req.getMachine(getIDMachine(), "data" );
            JSONObject machine =  (JSONObject) data.get(0);
            machineName.setText(Html.fromHtml(machine.getString("title")));

            Bitmap object = (Bitmap) req.getMachine(getIDMachine(), "photo1" );
            image.setImageBitmap(object);
            desc.setText(Html.fromHtml(machine.getString("content")));
            step.setText(Html.fromHtml(machine.getString("steps")));
            req.inc("machine",Username,getIDMachine());
       //     JSONArray data = (JSONArray) req.getMachine("backX", "data" );

       /*    Boolean incrementMachine = req.inc("machine", "main", "abX"); //
            Boolean favo= req.fav("main","routinegg"); // add to favourites
            Boolean addRecent= req.addToRecent("routine", "main", "chestX");
            JSONArray machineTimes= req.getTimes("machine", "main");
            JSONArray allRecent= req.getRecent("machine", "main");
            Object allMachines= req.getMachine("all", "data"); // place 'id' instead of all if you want a specific machine
            JSONArray allRoutines= req.getRoutine("all", "data"); // routine always has data
*/

            JSONObject info= req.getFav("zambo");
            String gg="";

        }catch (Exception e){

            e.printStackTrace();
        }



    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            try
            {
                HttpRequest req = HttpRequest.getInstance();
                JSONArray data = (JSONArray) req.getMachine(getIDMachine(), "data" );
                JSONObject link =  (JSONObject) data.get(0);
                String delims = "[=]+";
                String u[] = link.getString("video").split(delims,20);
                String r = u[1];
                youTubePlayer.cueVideo(r);
            }catch (Exception e){

                e.printStackTrace();
            }

        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            // Retry initialization if user performed a recovery action
            youtubePlayerView.initialize("AIzaSyCqqMjoWO3TzBE97d87SyNH-o9TEOBEWio",onInitializedListener);

    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youtubePlayerView;
    }
    private String getIDMachine ()
    {
        Intent in = getIntent();
        String id;
        id= in.getStringExtra("id");
        return id;
    }
}
