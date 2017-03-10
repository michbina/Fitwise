package cepheustheapp.com.cepheus;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;


public class Routine  extends Activity implements View.OnClickListener     {
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    String Username;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routine);

        try{

            populate();

        }catch(Exception e){


        }
        final ImageButton favorite = (ImageButton) findViewById(R.id.favorite);
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
        });
    }







    public void populate() throws Exception {

        try {

            //ImageView image = (ImageView) findViewById(R.id.title);

            TextView text = (TextView) findViewById(R.id.Rout1);
            TextView text2 = (TextView) findViewById(R.id.Rout2);
            TextView text3 = (TextView) findViewById(R.id.Rout3);

            HttpRequest req = HttpRequest.getInstance();

            //if (!req.isLoggedIn()) req.login("main", "password");

            //Bitmap object = (Bitmap) req.getMachine(, "photo1" );


            //image.setImageBitmap(object);

            //JSONArray data = (JSONArray) req.getMachine("backX", "data");
            Object data =  req.getRoutine("routinegg", "data");
            JSONArray Jdata= (JSONArray)data;
            Object data2 =  req.getRoutine("routineLL", "data");
            JSONArray Jdata2= (JSONArray)data2;
            Object data3 =  req.getRoutine("routineCof", "data");
            JSONArray Jdata3= (JSONArray)data3;
            //JSONArray data = (JSONArray) req.getMachine("backX", "data");
            JSONObject obj= (JSONObject) Jdata.get(0);
            JSONObject obj2= (JSONObject) Jdata2.get(0);
            JSONObject obj3= (JSONObject) Jdata3.get(0);


            text.setText(Html.fromHtml(obj.getString("name")));
            text2.setText(Html.fromHtml(obj2.getString("name")));
            text3.setText(Html.fromHtml(obj3.getString("name")));
            //Display the blue star if the routine is already favorite
            /*
            final ImageButton favorite2 = (ImageButton) findViewById(R.id.favorite);
            favorite2.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.starimage));
            Integer i;
            JSONObject obj1 = req.getFav(getUsername());
            JSONArray fav = obj1.getJSONArray("fav");
            String listFav [] = new String [fav.length()];
            for(i=0;i<fav.length();i++)
            {
                listFav[i]= (String) fav.get(i);
               if(listFav[i].equals("routinegg"))
               {
                   favorite2.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.starblue));
               }
            }*/


        }  catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        Intent RoutineActivity = new Intent(Routine.this, Routine_scan.class);
        String RoutineSelected="";


        //RoutineActivity.putExtra("id", "backX");
        HttpRequest req = HttpRequest.getInstance();


        switch (v.getId()) {
            case R.id.Rout1:
                RoutineSelected="routine1";
                break;
            case R.id.Rout2:
                RoutineSelected="routine2";
                break;
            case R.id.Rout3:
                RoutineSelected="routinegg";
                break;
        }
        int i;
        JSONArray j;
        JSONObject a;
        String b;
        String k;
        //Extract the name of the machines from the routine
        try
        {
            //JSONArray routine= req.getRoutine("routinegg","data");
            Object obj= req.getRoutine(RoutineSelected,"data");
            JSONArray routine= (JSONArray)obj;
            JSONObject machine =  (JSONObject) routine.get(0);
            j=machine.getJSONArray("machines");

            req.inc("routine",getUsername(),RoutineSelected);

            String  machineRoutine [] = new String[j.length()] ;

            for(i=0;i<j.length();i++)
            {
                machineRoutine [i] = (String) j.get(i);
            }
            RoutineActivity.putExtra("nb_exercise", j.length());
            RoutineActivity.putExtra("id_machines", machineRoutine);
            RoutineActivity.putExtra("i", 1 );


        }catch(Exception e)
        {

        }

        startActivity(RoutineActivity);
    }
    private String getUsername()
    {
        SharedPreferences settings;
        settings = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Username = settings.getString("username", null);

        return Username;
    }
}


