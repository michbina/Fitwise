package cepheustheapp.com.cepheus;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONObject;

public class Routine_machine extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{


    Button b;
    private YouTubePlayerView youtubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    public static final String YOUTUBE_API_KEY ="AIzaSyCqqMjoWO3TzBE97d87SyNH-o9TEOBEWio";
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routine_machine);



        youtubePlayerView= (YouTubePlayerView) findViewById(R.id.view);
        youtubePlayerView.initialize("AIzaSyCqqMjoWO3TzBE97d87SyNH-o9TEOBEWio",this);
        try{

            populate();

        }catch(Exception e){


        }

        final Button Scan_again = (Button) findViewById(R.id.Scan_again);
        final Button Menu = (Button) findViewById(R.id.Menu);
        Intent in = getIntent();
        int nb_machine,i;
        nb_machine= in.getIntExtra("nb_exercise",0);
        i = in.getIntExtra("i",0);

        if(i==nb_machine)
        {
            Scan_again.setText("Menu");
        }
        if(getIndex()!=getNbMachines())
        {
            Menu.setVisibility(View.VISIBLE);
        }else
        {
            Menu.setVisibility(View.GONE);
        }

        Scan_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getIndex()!=getNbMachines())
                {

                    Intent Routine_scan = new Intent(Routine_machine.this, Routine_scan.class);
                    Routine_scan.removeExtra("i");
                    Routine_scan.putExtra("i", getIndex()+1);
                    Routine_scan.putExtra("nb_exercise", getNbMachines());
                    Routine_scan.putExtra("id_machines", getListMachines());
                    startActivity(Routine_scan);
                    finish();
                }
                else
                {
                    Intent Menu = new Intent(getApplicationContext(), Menu.class);
                    startActivity(Menu);
                }

            }
        });



        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Menu = new Intent(getApplicationContext(), Menu.class);
                startActivity(Menu);
            }
        });
        /*final ImageButton favorite = (ImageButton) findViewById(R.id.favorite);
        favorite.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.starimage));

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());

                if (v.isSelected()) {
                    favorite.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.starblue));

                    try
                    {
                        HttpRequest req1 = HttpRequest.getInstance();
                        //Boolean favo= req1.fav("main","routinegg"); // add to favourites
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                } else {
                    favorite.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.starimage));
                }

            }
        });*/

    }

    public void populate() throws Exception{

        try {

            ImageView image = (ImageView) findViewById(R.id.image);
            TextView desc = (TextView) findViewById(R.id.desc);
            TextView step = (TextView) findViewById(R.id.step);
            TextView machineName = (TextView) findViewById(R.id.machineName);
            HttpRequest req = HttpRequest.getInstance();

            if (!req.isLoggedIn()) req.login(getUsername(), "password");

            //Bitmap object = (Bitmap) req.getMachine(, "photo1" );
            //image.setImageBitmap(object);
            //     JSONArray data = (JSONArray) req.getMachine("backX", "data" );
            Bitmap object = (Bitmap) req.getMachine(getIDMachine(), "photo1" );
            Boolean incrementMachine = req.inc("machine", "main", "abX"); //
            Boolean favo= req.fav("main","routinegg"); // add to favourites
            Boolean addRecent= req.addToRecent("routine", "main", "chestX");
            JSONArray machineTimes= req.getTimes("machine", "main");
            JSONArray allRecent= req.getRecent("machine", "main");
            Object allMachines= req.getMachine("all", "data"); // place 'id' instead of all if you want a specific machine
            Object obj= req.getRoutine("all", "data"); // routine always has data
            JSONArray allRoutines= (JSONArray)obj;



            image.setImageBitmap(object);
            //JSONObject obj= (JSONObject) data.get(0);
            String IDMachine = getIDMachine();
            JSONArray data = (JSONArray) req.getMachine(getIDMachine(), "data" );
            JSONObject machine =  (JSONObject) data.get(0);
            //String a = (Html.fromHtml(machine.getString(4)));


            machineName.setText(Html.fromHtml(machine.getString("title")));
            desc.setText(Html.fromHtml(machine.getString("content")));
            step.setText(Html.fromHtml(machine.getString("steps")));

            req.inc("machine",getUsername(),getIDMachine());




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
                //
                /*Intent in = getIntent();
                int nb_machine,i;
                nb_machine= in.getIntExtra("nb_exercise",0);
                String list_machines [] = new String[nb_machine];
                i = in.getIntExtra("i",0);
                list_machines = in.getStringArrayExtra("id_machines");*/
                JSONArray data = (JSONArray) req.getMachine(getIDMachine(), "data" );
                //
                //JSONArray data = (JSONArray) req.getMachine("backX", "data" );
                JSONObject link =  (JSONObject) data.get(0);
                String delims = "[=]+";
                String u[] = link.getString("video").split(delims,20);
                String r = u[1];
                //youTubePlayer.loadVideo(r);
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
    private int getNbMachines()
    {
        int nb;
        Intent in = getIntent();
        nb= in.getIntExtra("nb_exercise",0);
        return nb;
    }
    private int getIndex()
    {
        int i;
        Intent in = getIntent();
        i= in.getIntExtra("i",0);
        return i;
    }
    private String[] getListMachines()
    {
        String list_machines [] = new String[getNbMachines()];
        Intent in = getIntent();
        list_machines = in.getStringArrayExtra("id_machines");
        return list_machines;
    }
    private String getIDMachine ()
    {
        String nameMachine;
        String List [] = new String[getNbMachines()];
        List = getListMachines();
        nameMachine= List[getIndex()-1];

        return nameMachine;
    }
    private String getUsername()
    {
        SharedPreferences settings;
        String Username;
        settings = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Username = settings.getString("username", null);

        return Username;
    }
}
