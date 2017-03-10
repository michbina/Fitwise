package cepheustheapp.com.cepheus;

/**
 * Created by ryanlapensee on 2017-03-04.
 */

import android.graphics.Bitmap;
import android.media.Image;

import org.json.JSONArray;
import org.json.JSONObject;


public class routine_list_item {

    //routine item attributes
    private String routineName;
    private String routineDescription;
    private JSONArray routineMachines;
    private String routineID;
    private Bitmap routineImage;

    //constructor
    public routine_list_item(String name, String desc, JSONArray machines, String id, Bitmap image){

        this.routineDescription = desc;
        this.routineName = name;
        this.routineMachines = machines;
        this.routineID = id;
        this.routineImage = image;
    }

    //getters
    public String getName() { return routineName; }
    public String getDesc() {return routineDescription; }
    public JSONArray getMachines() {return routineMachines; }
    public String getID() {return routineID; }
    public Bitmap getImage() {return routineImage;}
}
