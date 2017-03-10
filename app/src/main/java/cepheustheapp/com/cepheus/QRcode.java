package cepheustheapp.com.cepheus;

        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
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

        import org.json.JSONArray;
        import org.json.JSONObject;

        import java.io.InputStream;

        import static android.R.attr.id;

public class QRcode  extends AppCompatActivity implements View.OnClickListener   {

    final String EXTRA_ExerciseName = "ExerciseName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode);

        Button mybutton = (Button) findViewById(R.id.scan_button);
        mybutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)  {
        if(v.getId() == R.id.scan_button){

            //Intent intent2 = new Intent(QRcode.this, Freestyle_machine.class);
            //intent2.putExtra("id", "backX");
            //startActivity(intent2);
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

        // get scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);



        if (scanningResult != null)
        {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            TextView scan_format = (TextView) findViewById(R.id.scan_format);
            TextView scan_format2 = (TextView) findViewById(R.id.scan_format2);
            TextView scan_content = (TextView) findViewById(R.id.scan_content);
            TextView scan_content2 = (TextView) findViewById(R.id.scan_content2);
            JSONObject obj=null;
            try{

                obj= new JSONObject(scanContent);

            }catch(Exception e){

            }
            String id= null;
            Boolean machineFound = false;
           // scan_format.setText("FORMAT: " + scanFormat);
           // scan_content.setText("CONTENT: " + scanContent);

            try
            {
                HttpRequest req = HttpRequest.getInstance();
                Integer i;
                JSONArray allMachines= (JSONArray) req.getMachine("all", "data");
                JSONObject machi [] = new JSONObject[allMachines.length()];
                String listMachine [] = new String[allMachines.length()];

                for(i=0;i<allMachines.length();i++)
                {
                    machi [i] = allMachines.getJSONObject(i);
                    listMachine [i] =  machi[i].getString("id");
                    if(obj.get("id").equals(listMachine[i]))
                    {
                        machineFound = true;
                        id=listMachine[i];
                    }
                }

            }
            catch(Exception e)
            {
                String b;
            }

            if(machineFound==true)
            {
                Intent intent2 = new Intent(QRcode.this, Freestyle_machine.class);
                intent2.putExtra("id", id);
                startActivity(intent2);
            }
            else
            {
                Toast.makeText(getApplicationContext(), R.string.Try_again,
                        Toast.LENGTH_SHORT).show();
                scan_format.setText(R.string.Uncorrect_machine);
                scan_format2.setText(R.string.Scan_again);
            }

            return;



        }
        else{
            Toast.makeText(getApplicationContext(), R.string.Try_again,
                    Toast.LENGTH_SHORT).show();

        }

    }}