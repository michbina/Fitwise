package cepheustheapp.com.cepheus;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;


public class Profile extends Activity{



    private TextView myText = null;
    TextView myAwesomeTextView;
    TextView text;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    String Username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        SharedPreferences settings;
        settings = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Username = settings.getString("username", null);
        try{

            populate();

        }catch(Exception e){


        }


    }
    public void populate() throws Exception {

        try {

            //ImageView image = (ImageView) findViewById(R.id.title);
            TextView SBio = (TextView) findViewById(R.id.user_profile_short_bio);
            TextView SUsername = (TextView) findViewById(R.id.username_user);
            TextView SAddress = (TextView) findViewById(R.id.address_user);
            TextView SCity = (TextView) findViewById(R.id.city_user);
            TextView SWeight = (TextView) findViewById(R.id.weight_user);
            TextView SHeight = (TextView) findViewById(R.id.height_user);
            TextView SFirstname = (TextView) findViewById(R.id.user_profile_firstname);
            //TextView SLastname = (TextView) findViewById(R.id.user_profile_lastname);
            TextView SEmail = (TextView) findViewById(R.id.email_user);
            TextView SAge = (TextView) findViewById(R.id.age_user);
            ImageView photoUser = (ImageView) findViewById(R.id.user_profile_photo);




            HttpRequest req = HttpRequest.getInstance();



           // JSONArray data = (JSONArray) req.getMachine("backX", "data");


            //JSONArray data = (JSONArray) req.getMachine("backX", "data");
            JSONObject info= req.getUserInfo(Username);

            Bitmap object = (Bitmap) req.getPhoto(info.getString("username"));
            photoUser.setImageBitmap(object);

            if(!info.getString("email").equals("null"))
                SBio.setText(Html.fromHtml(info.getString("email")));
            if(!info.getString("username").equals("null"))
                SUsername.setText(Html.fromHtml(info.getString("username")));
            if(!info.getString("address").equals("null"))
                SAddress.setText(Html.fromHtml(info.getString("address")));
            if(!info.getString("city").equals("null"))
                SCity.setText(Html.fromHtml(info.getString("city")));
            if(!info.getString("weight").equals("null"))
                SWeight.setText(Html.fromHtml(info.getString("weight")));
            if(!info.getString("height").equals("null"))
                SHeight.setText(Html.fromHtml(info.getString("height")));
            //if(!info.getString("firstName").equals("null"))

            //String name = info.getString("fi")
                SFirstname.setText(Html.fromHtml(info.getString("firstName")+" "+info.getString("lastName")));
            //if(!info.getString("lastName").equals("null"))
                //SLastname.setText(Html.fromHtml(info.getString("lastName")));
            if(!info.getString("email").equals("null"))
                SEmail.setText(Html.fromHtml(info.getString("email")));
            if(!info.getString("age").equals("null"))
                SAge.setText(Html.fromHtml(info.getString("age")));



        }  catch (Exception e) {

            e.printStackTrace();
        }
    }


}
