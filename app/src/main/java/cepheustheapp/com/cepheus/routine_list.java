package cepheustheapp.com.cepheus;

/**
 * Created by ryanlapensee on 2017-03-04.
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.content.Context;
import android.os.Environment;
import android.text.Html;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ImageView;
import android.app.Activity;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

public class routine_list extends Activity {
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    String Username;
    private ArrayList<routine_list_item> routineItems = new ArrayList<>();

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.routine_list);
        final ImageButton favorite = (ImageButton) findViewById(R.id.favorite);
        //favorite.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.starimage));

        try {

        //Get the Routines
        HttpRequest req = HttpRequest.getInstance();
        //JSONArray data =  req.getRoutine("all", "data")
            Object objText= req.getRoutine("all","data");
            JSONArray data= (JSONArray)objText;

        for (int i=0; i < data.length(); i++) {
            JSONObject objetText= (JSONObject) data.get(i);
            String nm = objetText.getString("name");
            String desc = objetText.getString("content");
            String id = objetText.getString("id");
            String machines = "";

            Bitmap image = (Bitmap) req.getRoutine(id,"photo");
            //Bitmap image =null;


            JSONArray routineMachines = objetText.getJSONArray("machines");
            for (int j=0; j < machines.length(); j++) {
                JSONObject mach = (JSONObject) routineMachines.get(j);
                //machines += Integer.toString(j) + ". " + mach.getString("id") + "\n";
                machines += mach.getString("id") + ", ";
            }

            routineItems.add(new routine_list_item(nm, desc, routineMachines,id,image));

        }


        //routineItems.add(new routine_list_item("Routine 1", "This is a routine"));
        //routineItems.add(new routine_list_item("Routine 2", "This is a different routine"));


        }  catch (Exception e) {

            e.printStackTrace();
        }

        //create our new array adapter
        ArrayAdapter<routine_list_item> adapter = new routineListArrayAdapter(this, 0, routineItems);

        //Find list view and bind it with the custom adapter
        ListView listView = (ListView) findViewById(R.id.routine_list);
        listView.setAdapter(adapter);


        //add event listener so we can handle clicks
        AdapterView.OnItemClickListener adapterViewListener = new AdapterView.OnItemClickListener() {

            //on click
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent RoutineActivity = new Intent(routine_list.this, Routine_scan.class);
                //String RoutineSelected="";

                try {
                    routine_list_item routine = routineItems.get(position);
                    JSONArray j = routine.getMachines();

                    JSONObject m;
                    //String machineRoutine[] = new String[j.length()];
                    /********************/
                    HttpRequest req = HttpRequest.getInstance();
                    req.inc("routine",getUsername(),routine.getID());

                    String  machineRoutine [] = new String[j.length()] ;

            for(int i=0;i<j.length();i++)
            {
                machineRoutine [i] = (String) j.get(i);
            }
                    /*for (int i = 0; i < j.length(); i++) {
                        m = (JSONObject) j.get(i);
                        machineRoutine[i] = m.getString("id");
                    }*/

                    RoutineActivity.putExtra("nb_exercise", j.length());
                    RoutineActivity.putExtra("id_machines", machineRoutine);
                    RoutineActivity.putExtra("i", 1);

                } catch(Exception e)
                {

                }

                startActivity(RoutineActivity);
            }
        };


        /*favorite.setOnClickListener(new View.OnClickListener() {
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
//set the listener to the list view
        listView.setOnItemClickListener(adapterViewListener);

    }


    //custom ArrayAdapter
    class routineListArrayAdapter extends ArrayAdapter<routine_list_item>{

        private Context context;
        private List<routine_list_item> routines;

        //constructor, call on creation
        public routineListArrayAdapter(Context context, int resource, ArrayList<routine_list_item> objects) {
            super(context, resource, objects);

            this.context = context;
            this.routines = objects;
        }


        //called when rendering the list
        public View getView(int position, View convertView, ViewGroup parent) {

            //get the property we are displaying
            routine_list_item routine = routines.get(position);

            //get the inflater and inflate the XML layout for each item
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.routine_list_item, null);

            TextView name = (TextView) view.findViewById(R.id.routine_name);
            TextView description = (TextView) view.findViewById(R.id.routine_desc);
            TextView machines = (TextView) view.findViewById(R.id.machines);
            ImageView image = (ImageView) view.findViewById(R.id.image);
            //ImageView photoRoutine = (ImageView) findViewById(R.id.image);


            //Set Name, Description, and Image
            if(routine.getImage()==null)
            {
                image.setImageResource(R.drawable.pull_up);
            }else{
                image.setImageBitmap(routine.getImage());
            }

            name.setText(routine.getName());
            description.setText(Html.fromHtml(routine.getDesc()));
            try {
                String machineText = "";
                for (int i = 0; i < routine.getMachines().length(); i++) {
                    //machineText += Integer.toString(i) + ". " + routine.getMachines().get(i).toString();
                    machineText += routine.getMachines().get(i).toString()+", ";
                }
                machines.setText(machineText);
            } catch (Exception e) {

            }
            //int imageID = context.getResources().getIdentifier("pull_up", "drawable", context.getPackageName());
            //image.setImageResource(R.drawable.pull_up);

            return view;
        }
    }
    private String getUsername()
    {
        SharedPreferences settings;
        settings = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Username = settings.getString("username", null);

        return Username;
    }

}
