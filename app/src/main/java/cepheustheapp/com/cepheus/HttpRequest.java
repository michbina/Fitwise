package cepheustheapp.com.cepheus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import static android.R.attr.id;
import static android.R.attr.password;
import static android.R.attr.type;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

import static cepheustheapp.com.cepheus.R.id.textView;
import static java.lang.System.in;

/**
 * Created by Main on 1/12/2017.
 */

public class HttpRequest  {

    private String token;
    private HttpURLConnection urlConnection;
    private URI request_url ;
    private Object data;




    private class GetRoutines extends AsyncTask<String, Void, Object> {

        private String id;
        private String type;
        public GetRoutines(String id, String type) {

            this.id=id;
            this.type=type;
        }

        @Override
        protected Object doInBackground(String... params) {

     //       android.os.Debug.waitForDebugger();

            URL url=null;
            try {

                Uri.Builder builder = new Uri.Builder();
                builder.path(request_url.toString());

                if(id=="all"){

                    builder.appendPath("api/routines");

                }else {
                    builder.appendPath("api/routine");
                    if (type == "data") {

                        builder.appendQueryParameter("type", type);
                    } else {

                        builder.appendQueryParameter("type", type + ".jpg");
                    }

                    builder.appendQueryParameter("id", id);
                }

                String str = URLDecoder.decode(builder.toString(), "UTF-8");

                url = new URL(str);

                urlConnection = (HttpURLConnection) url.openConnection();
                HttpURLConnection.setFollowRedirects(true);
                urlConnection.setConnectTimeout(100000);
                urlConnection.setReadTimeout(100000);
                urlConnection.setDoInput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("x-access-token", token);
                //urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                if(type!="data"){


                    BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    Bitmap image = BitmapFactory.decodeStream(in);

                    return image;



                }else {
                    // int ff= urlConnection.getResponseCode();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    //BufferedReader r = new BufferedReader(new InputStreamReader(in));
                    StringBuilder total = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        total.append(line).append('\n');
                    }

                    String result = total.toString();

                    JSONArray jsonObject = new JSONArray(result);


                    return jsonObject;

                }
            }catch (Exception e){


                e.printStackTrace();

            }



            return null;
        }
    };

    private class getRecent extends AsyncTask<String, Void, JSONArray> {

        private String type;
        private String username;

        public getRecent(String type, String username) {

            this.type = type;
            this.username = username;
        }

        @Override
        protected JSONArray doInBackground(String... strings) {

            URL url = null;

            try {

                Uri.Builder builder = new Uri.Builder();
                builder.path(request_url.toString());
                if (type == "machine") {
                    builder.appendPath("api/recentMachine");

                } else {
                    builder.appendPath("api/recentRoutine");

                }
                builder.appendQueryParameter("userid", username);


                String str = URLDecoder.decode(builder.toString(), "UTF-8");

                url = new URL(str);

                urlConnection = (HttpURLConnection) url.openConnection();
                HttpURLConnection.setFollowRedirects(true);
                urlConnection.setConnectTimeout(100000);
                urlConnection.setReadTimeout(100000);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestProperty("x-access-token", token);
                urlConnection.setRequestMethod("GET");


                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line).append('\n');
                }

                String result = total.toString();
                JSONArray jsonObject = new JSONArray(result);

                return jsonObject;

            }catch(Exception e){

            e.printStackTrace();

            }

            return null;
        }


    };

    private class incMachine extends AsyncTask<String, Void, Boolean> {
        private String username;
        private String id;
        private String type;

        public incMachine(String type, String username, String id) {

            this.type=type;
            this.username = username;
            this.id = id;

        }

        @Override
        protected Boolean doInBackground(String... strings) {

            // android.os.Debug.waitForDebugger();

            URL url = null;

            try {

                Uri.Builder builder = new Uri.Builder();
                builder.path(request_url.toString());
                if(type=="machine") {
                    builder.appendPath("api/incrementMachine");
                    builder.appendQueryParameter("machineid", id);
                }else{
                    builder.appendPath("api/incrementRoutine");
                    builder.appendQueryParameter("routineid", id);
                }
                builder.appendQueryParameter("userid", username);


                String str = URLDecoder.decode(builder.toString(), "UTF-8");

                url = new URL(str);

                urlConnection = (HttpURLConnection) url.openConnection();
                HttpURLConnection.setFollowRedirects(true);
                urlConnection.setConnectTimeout(100000);
                urlConnection.setReadTimeout(100000);
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("x-access-token", token);
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestMethod("POST");

                urlConnection.connect();

                PrintWriter wr = null;

                try {
                    wr = new PrintWriter(urlConnection.getOutputStream(), true);

                } catch (Exception e) {

                    e.printStackTrace();
                }
                wr.write("");
                wr.flush();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            }catch(Exception e){

                e.printStackTrace();
            }

            return true;

        }

    };

    private class getUserPhoto extends AsyncTask<String, Void, Object> {
        private String username;

        public getUserPhoto(String username) {

            this.username = username;

        }

        @Override
        protected Object doInBackground(String... strings) {

            // android.os.Debug.waitForDebugger();

            URL url = null;

            try {

                Uri.Builder builder = new Uri.Builder();
                builder.path(request_url.toString());
                builder.appendPath("api/user/photo");
                builder.appendQueryParameter("id", username);

                String str = URLDecoder.decode(builder.toString(), "UTF-8");

                url = new URL(str);

                urlConnection = (HttpURLConnection) url.openConnection();
                HttpURLConnection.setFollowRedirects(true);
                urlConnection.setConnectTimeout(100000);
                urlConnection.setReadTimeout(100000);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestProperty("x-access-token", token);
                urlConnection.setRequestMethod("GET");

                BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
                Bitmap image = BitmapFactory.decodeStream(in);

                return image;


            } catch (Exception e) {

                    e.printStackTrace();
            }

            return null;

        }

    }

    private class getFavRoutine extends AsyncTask<String, Void, JSONObject> {
        private String username;

        public getFavRoutine(String username) {

            this.username = username;

        }

        @Override
        protected JSONObject doInBackground(String... strings) {

            // android.os.Debug.waitForDebugger();

            URL url = null;

            try {

                Uri.Builder builder = new Uri.Builder();
                builder.path(request_url.toString());
                builder.appendPath("api/favRoutine");
                builder.appendQueryParameter("userid", username);

                String str = URLDecoder.decode(builder.toString(), "UTF-8");

                url = new URL(str);

                urlConnection = (HttpURLConnection) url.openConnection();
                HttpURLConnection.setFollowRedirects(true);
                urlConnection.setConnectTimeout(100000);
                urlConnection.setReadTimeout(100000);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestProperty("x-access-token", token);
                urlConnection.setRequestMethod("GET");


                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line).append('\n');
                }

                String result = total.toString();
                JSONArray jsonObject = new JSONArray(result);
                JSONObject object = jsonObject.getJSONObject(0);

                return object;


            } catch (Exception e) {


                e.printStackTrace();
            }

            return null;


        }
    }

    private class favRoutine extends AsyncTask<String, Void, Boolean> {
        private String username;
        private String routineid;

        public favRoutine(String username, String routineid) {

            this.routineid = routineid;
            this.username = username;


        }

        @Override
        protected Boolean doInBackground(String... strings) {

            // android.os.Debug.waitForDebugger();

            URL url = null;

            try {

                Uri.Builder builder = new Uri.Builder();
                builder.path(request_url.toString());
                builder.appendPath("api/favRoutine");
                builder.appendQueryParameter("routineid", routineid);
                builder.appendQueryParameter("userid", username);


                String str = URLDecoder.decode(builder.toString(), "UTF-8");

                url = new URL(str);

                urlConnection = (HttpURLConnection) url.openConnection();
                HttpURLConnection.setFollowRedirects(true);
                urlConnection.setConnectTimeout(100000);
                urlConnection.setReadTimeout(100000);
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestProperty("x-access-token", token);
                urlConnection.setRequestMethod("POST");

                urlConnection.connect();

                PrintWriter wr = null;

                try {
                    wr = new PrintWriter(urlConnection.getOutputStream(), true);

                } catch (Exception e) {

                    e.printStackTrace();
                    return false;
                }
                wr.write("");
                wr.flush();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());



            }catch (Exception e){


                e.printStackTrace();
            }

            return true;


        }

    };


    private class getTimes extends AsyncTask<String, Void, JSONArray> {
        private String username;

        private String type;

        public getTimes(String type, String username) {

            this.type = type;
            this.username = username;


        }

        @Override
        protected JSONArray doInBackground(String... strings) {

            // android.os.Debug.waitForDebugger();

            URL url = null;

            try {

                Uri.Builder builder = new Uri.Builder();
                builder.path(request_url.toString());
                if (type == "machine") {
                    builder.appendPath("api/machineTimes");

                } else {
                    builder.appendPath("api/routineTimes");

                }
                builder.appendQueryParameter("userid", username);


                String str = URLDecoder.decode(builder.toString(), "UTF-8");

                url = new URL(str);

                urlConnection = (HttpURLConnection) url.openConnection();
                HttpURLConnection.setFollowRedirects(true);
                urlConnection.setConnectTimeout(100000);
                urlConnection.setReadTimeout(100000);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("x-access-token", token);
                urlConnection.setRequestMethod("GET");



                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line).append('\n');
                }

                String result = total.toString();
                JSONArray jsonObject = new JSONArray(result);

                return jsonObject;


            } catch (Exception e) {


            }

            return null;

        }

    };




     private class recentMachine extends AsyncTask<String, Void, Boolean> {
        private String username;
        private String id;
        private String type;

        public recentMachine(String type, String username, String id) {

            this.type=type;
            this.username = username;
            this.id = id;

        }

        @Override
        protected Boolean doInBackground(String... strings) {

            // android.os.Debug.waitForDebugger();

            URL url = null;

            try {

                Uri.Builder builder = new Uri.Builder();
                builder.path(request_url.toString());
                if(type=="machine") {
                    builder.appendPath("api/recentMachine");
                    builder.appendQueryParameter("machineid", id);
                }else{
                    builder.appendPath("api/recentRoutine");
                    builder.appendQueryParameter("routineid", id);
                }
                builder.appendQueryParameter("userid", username);


                String str = URLDecoder.decode(builder.toString(), "UTF-8");

                url = new URL(str);

                urlConnection = (HttpURLConnection) url.openConnection();
                HttpURLConnection.setFollowRedirects(true);
                urlConnection.setConnectTimeout(100000);
                urlConnection.setReadTimeout(100000);
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestProperty("x-access-token", token);
                urlConnection.setRequestMethod("POST");


                PrintWriter wr = null;

                try {
                    wr = new PrintWriter(urlConnection.getOutputStream(), true);

                } catch (Exception e) {

                    e.printStackTrace();
                }
                wr.write("");
                wr.flush();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());




            }catch(Exception e){

                e.printStackTrace();
            }

            return true;

        }

    };



    private class Login extends AsyncTask<String, Void, Boolean> {
        private String username;
        private String password;
        public Login(String username, String password) {

            this.username= username;
            this.password= password;

        }
        @Override
        protected Boolean doInBackground(String... strings)  {

           // android.os.Debug.waitForDebugger();

            URL url=null;
            try {

                Uri.Builder builder = new Uri.Builder();
                builder.path(request_url.toString());
                builder.appendPath("token/signin");

                String str= URLDecoder.decode(builder.toString(), "UTF-8");

                url = new URL(str);

                urlConnection = (HttpURLConnection) url.openConnection();
                HttpURLConnection.setFollowRedirects(true);
                urlConnection.setConnectTimeout(100000);
                urlConnection.setReadTimeout(100000);
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestMethod("POST");

                JSONObject body = new JSONObject();

                body.put("username", username);
                body.put("password", password);
                PrintWriter wr = null;

                try {
                    wr = new PrintWriter(urlConnection.getOutputStream(), true);

                } catch (Exception e) {

                    e.printStackTrace();
                }
                wr.write(body.toString());
                wr.flush();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line).append('\n');
                }

                String result = total.toString();
                JSONObject jsonObject = new JSONObject(result);


                if (jsonObject.get("type") != null) {

                    if ((boolean) jsonObject.get("type") == true) {

                        String gg= (String) jsonObject.get("token");

                        token = (String) jsonObject.get("token");

                        return true;

                    }
                }

            }catch (Exception e){

                e.printStackTrace();
            }

            return false;
        }



    }


    private class Signup extends AsyncTask<String, Void, Boolean> {

        private String username;
        private String password;
        private String email;
        private String address;
        private String city;
        private String firstName;
        private String lastName;
        private int age;
        private double weight;
        private double height;


        public Signup(String username, String password, String email, String address,
                      String city, String firstName, String lastName, int age, double weight,
                      double height) {

            this.username = username;
            this.password = password;
            this.height= height;
            this.weight= weight;
            this.address= address;
            this.city= city;
            this.firstName= firstName;
            this.lastName= lastName;
            this.age= age;
            this.email= email;

        }

        @Override
        protected Boolean doInBackground(String... params) {

            URL url = null;
            try {

                Uri.Builder builder = new Uri.Builder();
                builder.path(request_url.toString());
                builder.appendPath("token/signup");
                String str= URLDecoder.decode(builder.toString(), "UTF-8");

                url = new URL(str);

                urlConnection = (HttpURLConnection) url.openConnection();
                HttpURLConnection.setFollowRedirects(true);
                urlConnection.setConnectTimeout(100000);
                urlConnection.setReadTimeout(100000);
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestMethod("POST");

                JSONObject body = new JSONObject();

                body.put("username", username);
                body.put("password", password);
                body.put("height", height);
                body.put("weight", weight);
                body.put("address", address);
                body.put("city", city);
                body.put("firstName", firstName);
                body.put("lastName", lastName);
                body.put("age", age);
                body.put("email", email);

                PrintWriter wr = null;

                try {
                    wr = new PrintWriter(urlConnection.getOutputStream(), true);

                } catch (Exception e) {

                    e.printStackTrace();
                }
                wr.write(body.toString());
                wr.flush();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line).append('\n');
                }

                String result = total.toString();
                JSONObject jsonObject = new JSONObject(result);

                if (jsonObject.get("type") != null) {

                    if ((boolean) jsonObject.get("type") == true) {

                        token = (String) jsonObject.get("token");

                        return true;

                    }
                }

            }catch(Exception e ){



            }



            return null;


        }
    }

    private class UserInfo extends AsyncTask<String, Void, Object> {

        String userid;


        public UserInfo(String userid) {

            this.userid = userid;  // type will need adhere to the standard of "photo1, photo2, etc." to get all photos

        }

        @Override
        protected Object doInBackground(String... params) {

            URL url = null;
            try {

                Uri.Builder builder = new Uri.Builder();
                builder.path(request_url.toString());
                builder.appendPath("api/getUserInfo");
                builder.appendQueryParameter("username", userid);



                String str = URLDecoder.decode(builder.toString(), "UTF-8");

                url = new URL(str);

                urlConnection = (HttpURLConnection) url.openConnection();
                HttpURLConnection.setFollowRedirects(true);
                urlConnection.setConnectTimeout(100000);
                urlConnection.setReadTimeout(100000);
                urlConnection.setDoInput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("x-access-token", token);
                //urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                //BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    total.append(line).append('\n');
                }

                String result = total.toString();

                JSONObject jsonObject = new JSONObject(result);


                return jsonObject;


            } catch (Exception e) {

                e.printStackTrace();
            }


            return false;

        }

    }

    private class GetMachine extends AsyncTask<String, Void, Object> {

        String type;
        String id;

        public GetMachine(String id, String type) {

            this.type= type;  // type will need adhere to the standard of "photo1, photo2, etc." to get all photos
            this.id= id;

        }

        @Override
        protected Object doInBackground(String... params) {

            URL url = null;
            try {

                Uri.Builder builder = new Uri.Builder();
                builder.path(request_url.toString());
                if(id=="all"){

                    builder.appendPath("api/machines");

                }else {
                    builder.appendPath("api/machine");
                    if (type == "data") {

                        builder.appendQueryParameter("type", type);
                    } else {

                        builder.appendQueryParameter("type", type + ".jpg");
                    }

                    builder.appendQueryParameter("id", id);
                }


                String str = URLDecoder.decode(builder.toString(), "UTF-8");

                url = new URL(str);

                urlConnection = (HttpURLConnection) url.openConnection();
                HttpURLConnection.setFollowRedirects(true);
                urlConnection.setConnectTimeout(100000);
                urlConnection.setReadTimeout(100000);
                urlConnection.setDoInput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("x-access-token", token);
                //urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                if(type!="data"){


                    BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    Bitmap image = BitmapFactory.decodeStream(in);

                    return image;



                }else {

                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    //BufferedReader r = new BufferedReader(new InputStreamReader(in));
                    StringBuilder total = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        total.append(line).append('\n');
                    }

                    String result = total.toString();

                    JSONArray jsonObject = new JSONArray(result);


                    return jsonObject;
                }
            } catch (Exception e) {

            }

            return null;
        }

    };


    private HttpRequest()  {

        try {

            request_url = new URI("https://fitwisetheapp.com"); // http://10.0.2.2:3001

        }catch(Exception e){

            e.printStackTrace();
        }

    }



    private static class Holder {
        private static final HttpRequest connection = new HttpRequest();
    }

    public static HttpRequest getInstance() {

        return Holder.connection;
    }

    public Object getRoutine(String id, String type) throws Exception {


        Object obj= new GetRoutines(id, type).execute().get();

        return obj;


    }

    public Object getMachine(String id, String type) throws Exception{ // type is either "data" or "photo"


            Object obj = new GetMachine(id, type).execute().get();


            return obj;

    }


    public boolean isLoggedIn(){


        if(token==null){
            return false;
        }else{
            return true;
        }

    }

    public boolean login(String username, String password) throws Exception{

        Boolean bool = new Login(username, password).execute().get();

        return bool;
    }

    public boolean inc(String type, String username, String id) throws Exception{

        // increment machine/routine
        Boolean bool= new incMachine(type, username, id).execute().get();

        return bool;
    }

    public boolean addToRecent(String type, String username, String id) throws Exception{

        // machine or routine
        Boolean bool= new recentMachine(type, username, id).execute().get();

        return bool;
    }


    public boolean Signup(String username, String password, String email, String address,
                          String city, String firstName, String lastName, int age, double weight,
                          double height) throws Exception{

        // machine or routine
        Boolean bool= new Signup( username,  password,  email,  address,
                 city,  firstName,  lastName, age, weight,
         height).execute().get();

        return bool;
    }


    public JSONArray getTimes(String type, String username)throws Exception{

        JSONArray n= new getTimes(type, username).execute().get();

        return n;

    }

    public JSONArray getRecent(String type, String username)throws Exception{

        JSONArray n= new getRecent(type, username).execute().get();

        return n;

    }


    public JSONObject getFav(String username)throws Exception{

        JSONObject n= new getFavRoutine(username).execute().get();

        return n;

    }

    public JSONObject getUserInfo(String username) throws Exception{

        Object n= new UserInfo(username).execute().get();


        return (JSONObject)n;



    }

    public boolean fav(String username, String routineid) throws Exception{

        Boolean bool= new favRoutine(username, routineid).execute().get();

        return bool;
    }

    public Bitmap getPhoto(String username) throws Exception{

        Object bool= new getUserPhoto(username).execute().get();

        return (Bitmap)bool;
    }



}
