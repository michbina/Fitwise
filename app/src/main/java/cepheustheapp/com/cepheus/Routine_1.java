package cepheustheapp.com.cepheus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

public class Routine_1  extends AppCompatActivity implements View.OnClickListener   {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routine_1);

        Button mybutton = (Button) findViewById(R.id.scan_button);
        mybutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)  {
        if(v.getId() == R.id.scan_button){

            new IntentIntegrator(this).initiateScan();
        }
    }

    // this is a test function. You can add zxing qr code images holding various data
    // in the drawable directory, and them import them through this function to parse
    // their data
    public Result getFromDrawable() throws Exception{

        // edit the following line with the desired resource you want to test
        Uri uri= Uri.parse(("android.resource://cepheustheapp.com.cepheus/"+ R.drawable.back));
        InputStream inputStream= getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        if (bitmap == null)
        {
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
        Result result= reader.decode(bBitmap);


        return result;

    }

    // after receiving QR data, this function will be triggered
    // all the logic populating the new UI with the data should be here
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        /*if (resultCode == Exo1_back) {
            Intent TestActivity = new Intent(Routine.this, Exo_2_Back.class);
            startActivity(TestActivity);


        }*/
        // get scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {



            // get content
            String scanContent = scanningResult.getContents();

            // get format
            String scanFormat = scanningResult.getFormatName();
            TextView scan_format = (TextView) findViewById(R.id.scan_format);
            TextView scan_content = (TextView) findViewById(R.id.scan_content);
            TextView scan_content2 = (TextView) findViewById(R.id.scan_content2);

                if (scanContent.equals("Exo1_back")) {
                    Intent TestActivity = new Intent(Routine_1.this, Exo_1_Back.class);
                    startActivity(TestActivity);
                } else {
                    Toast.makeText(Routine_1.this, R.string.Try_again,
                            Toast.LENGTH_SHORT).show();
                    scan_format.setText(R.string.Try_again);

                }



                //scan_format.setText("FORMAT: " + scanFormat);
                //scan_content.setText("CONTENT: " + scanContent);

            // this could be redundant?

        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Aucune donnée reçu!", Toast.LENGTH_SHORT);
            toast.show();
        }

    }}