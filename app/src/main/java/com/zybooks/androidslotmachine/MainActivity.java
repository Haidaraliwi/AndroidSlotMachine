package com.zybooks.androidslotmachine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import com.zybooks.androidslotmachine.ImageViewScrolling.IEventEnd;
import com.zybooks.androidslotmachine.ImageViewScrolling.ImageViewScrolling;

public class MainActivity extends AppCompatActivity implements IEventEnd {

    ImageView btn_up,btn_down;
    ImageViewScrolling image,image2,image3;
    TextView txt_score;
    int count_done=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        btn_down = (ImageView) findViewById(R.id.btn_down);
        btn_up = (ImageView) findViewById(R.id.btn_up);

        image = (ImageViewScrolling) findViewById(R.id.image);
        image2 = (ImageViewScrolling) findViewById(R.id.image2);
        image3 = (ImageViewScrolling) findViewById(R.id.image3);

        txt_score = (TextView) findViewById(R.id.txt_score);

        //set event
        image.setEventEnd(MainActivity.this);
        image2.setEventEnd(MainActivity.this);
        image3.setEventEnd(MainActivity.this);

        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Common.SCORE >= 50) // 50 is min price to roll
                {
                    btn_up.setVisibility(View.GONE);
                    btn_down.setVisibility(View.VISIBLE);

                    image.setValueRandom(new Random().nextInt(6),// because i have 6 images to random
                            new Random().nextInt((15 - 5) + 1) + 5);// to random from range 5-15
                    image2.setValueRandom(new Random().nextInt(6),// because i have 6 images to random
                            new Random().nextInt((15 - 5) + 1) + 5);// to random from range 5-15
                    image3.setValueRandom(new Random().nextInt(6),// because i have 6 images to random
                            new Random().nextInt((15 - 5) + 1) + 5);// to random from range 5-15
                    Common.SCORE = 50;
                    txt_score.setText(String.valueOf(Common.SCORE));

                } else {

                    Toast.makeText(MainActivity.this, "You not enough money", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //public MainActivity() {
       // super();}


    public void eventEnd(int result, int count) {
        if (count_done < 2) //if still slot has been rolling
            count_done++;
        else {
            btn_down.setVisibility(View.GONE);
            btn_up.setVisibility(View.VISIBLE);

            count_done = 0;

            //Calculate result
            if (image.getValue() == image2.getValue() && image2.getValue() == image3.getValue()) {
                Toast.makeText(this, "You Win Big Prize", Toast.LENGTH_SHORT).show();
                Common.SCORE += 300;
                txt_score.setText(String.valueOf(Common.SCORE));
            } else if (image.getValue() == image2.getValue() ||
                    image2.getValue() == image3.getValue() ||
                    image.getValue() == image3.getValue()) {
                Toast.makeText(this, "You Win Small Prize", Toast.LENGTH_SHORT).show();
                Common.SCORE += 300;
                txt_score.setText(String.valueOf(Common.SCORE));
            } else {
                Toast.makeText(this, "You Lose", Toast.LENGTH_SHORT).show();
            }
        }

        }
        }