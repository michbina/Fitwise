package cepheustheapp.com.cepheus;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by Lio on 2017-01-13.
 */



public class Generic_exercise_free extends YouTubeBaseActivity {

    Button b;
    private YouTubePlayerView youtubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;

    //Name of the exercise, get by the QRcode.class
    final String EXTRA_Name = "ExerciseName";
    TextView NewTextName;
    TextView NewTextDescription;
    ImageView ExerciseImage;
    Drawable marker;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_exercise_free);
        Intent intent = getIntent();

        //Get the image of the exercise in the database

        //Display the image of the exercise
        ExerciseImage = (ImageView)findViewById(R.id.Exercise_Image);
        marker = getResources().getDrawable(R.drawable.bench_press);
        ExerciseImage.setImageDrawable(marker);


        //Display the name of the exercise
        NewTextName  = (TextView)findViewById(R.id.Exercise_Name);
        NewTextName.setText(intent.getStringExtra(EXTRA_Name));

        NewTextDescription  = (TextView)findViewById(R.id.Exercise_Descrition);
        NewTextDescription.setText("Description of the machine");


        final Button Scan_again = (Button) findViewById(R.id.Scan_again);
        final Button Menu = (Button) findViewById(R.id.Menu);

        youtubePlayerView= (YouTubePlayerView) findViewById(R.id.view);

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("iUNoLR0pYjY");

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        b = (Button) findViewById(R.id.PlayVideo);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youtubePlayerView.initialize("AIzaSyCqqMjoWO3TzBE97d87SyNH-o9TEOBEWio",onInitializedListener);
            }
        });
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
        final ImageButton favorite = (ImageButton) findViewById(R.id.favorite);
        favorite.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.starimage));

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());

                if (v.isSelected()) {
                    favorite.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.starblue));

                } else {
                    favorite.setImageDrawable(getBaseContext().getResources().getDrawable(R.drawable.starimage));
                }

            }
        });


    }
}





