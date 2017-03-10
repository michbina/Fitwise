package cepheustheapp.com.cepheus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


// on importe les classes IntentIntegrator et IntentResult de la librairie zxing

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

public class Routine_scan extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routine_scan);
        Button mybutton2 = (Button) findViewById(R.id.scan_button);
        mybutton2.setOnClickListener(this);
        TextView NameExercise = (TextView) findViewById(R.id.NameExercise);


        try{

            populate();

        }catch(Exception e){


        }



    }
    public void populate() throws Exception {

        try {

            ImageView image = (ImageView) findViewById(R.id.title);
            TextView NameExercise = (TextView) findViewById(R.id.NameExercise);
            TextView title = (TextView) findViewById(R.id.Title);
            HttpRequest req = HttpRequest.getInstance();

            if (!req.isLoggedIn()) req.login("main", "password");

            String a = getIDMachine();
            JSONArray data = (JSONArray) req.getMachine(a, "data");
            JSONObject machine =  (JSONObject) data.get(0);
            NameExercise.setText(Html.fromHtml(machine.getString("title")));
            title.setText("Exercise: "+getIndex()+"/"+getNbMachines());
            //Bitmap object = (Bitmap) req.getMachine("backX", "photo1");
            Bitmap object = (Bitmap) req.getMachine(a, "photo1");
            image.setImageBitmap(object);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.scan_button) {

            new IntentIntegrator(this).initiateScan();
        }
    }

    // this is a test function. You can add zxing qr code images holding various data
    // in the drawable directory, and them import them through this function to parse
    // their data
    public Result getFromDrawable() throws Exception {

        // edit the following line with the desired resource you want to test
        Uri uri = Uri.parse(("android.resource://cepheustheapp.com.cepheus/" + R.drawable.back));
        InputStream inputStream = getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        if (bitmap == null) {
            //  not a bitmap
            return null;
        }
        int width = bitmap.getWidth(), height = bitmap.getHeight();
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        bitmap.recycle();
        bitmap = null;
        RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
        BinaryBitmap bBitmap = new BinaryBitmap(new HybridBinarizer(source));
        MultiFormatReader reader = new MultiFormatReader();

        // result will hold the actual content
        Result result = reader.decode(bBitmap);


        return result;

    }

    // after receiving QR data, this function will be triggered
    // all the logic populating the new UI with the data should be here
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        // get scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {


            // get content
            String scanContent = scanningResult.getContents();

            // get format
            String scanFormat = scanningResult.getFormatName();
            TextView scan_format = (TextView) findViewById(R.id.scan_format);
            TextView scan_format2 = (TextView) findViewById(R.id.scan_format2);
            TextView scan_content = (TextView) findViewById(R.id.scan_content);
            TextView scan_content2 = (TextView) findViewById(R.id.scan_content2);
            JSONObject obj=null;
            try{

                obj= new JSONObject(scanContent);

                if (obj.get("id").equals(getIDMachine())) {

                    Intent Routine_machine = new Intent(getApplicationContext(), Routine_machine.class);//Create new Intenet

                    Routine_machine.putExtra("nb_exercise", getNbMachines());
                    Routine_machine.putExtra("id_machines", getListMachines());
                    Routine_machine.putExtra("i",getIndex());

                    startActivity(Routine_machine);
                    finish();
                } else {
                    Toast.makeText(Routine_scan.this, R.string.Try_again,
                            Toast.LENGTH_SHORT).show();
                    scan_format.setText(R.string.Uncorrect_machine);
                    scan_format2.setText(R.string.Scan_again);

                }

            }catch(Exception e){
                Toast.makeText(Routine_scan.this, R.string.Try_again,
                        Toast.LENGTH_SHORT).show();
                scan_format.setText(R.string.Uncorrect_machine);
                scan_format2.setText(R.string.Scan_again);

            }


            //if (scanContent.equals("Exo1_back")) {



            //scan_format.setText("FORMAT: " + scanFormat);
            //scan_content.setText("CONTENT: " + scanContent);

            // this could be redundant?

        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Aucune donnée reçu!", Toast.LENGTH_SHORT);
            toast.show();
        }

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
}