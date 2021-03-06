package com.zybooks.androidslotmachine.ImageViewScrolling;


import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.zybooks.androidslotmachine.Common;
import com.zybooks.androidslotmachine.MainActivity;
import com.zybooks.androidslotmachine.R;


public class ImageViewScrolling extends FrameLayout {

    private static final int ANIMATION_DUR = 150;
    ImageView current_image;
    ImageView next_image;

    int last_result=0,old_value=0;

    IEventEnd eventEnd;

    public void setEventEnd(IEventEnd eventEnd) {
        this.eventEnd = eventEnd;
    }

    public ImageViewScrolling( Context context)  {
        super(context);
        init (context);
    }

    public ImageViewScrolling (Context context, AttributeSet attrs) {
        super(context, attrs);
        init (context);
    }
    private void init(Context context) {

        LayoutInflater.from(context).inflate(R.layout.image_view_scrolling,this);
        current_image = (ImageView)getRootView().findViewById(R.id.current_image);
        next_image = (ImageView)getRootView().findViewById(R.id.next_image);

        next_image.setTranslationY(getHeight());
    }

    public void setValueRandom(int image, int rotate_count)
    {
        current_image.animate().translationY(-getHeight()).setDuration(ANIMATION_DUR).start();
        next_image.setTranslationY(next_image.getHeight());
        next_image.animate().translationY(0)
                .setDuration(ANIMATION_DUR)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                       setImage(current_image,old_value%6);//because i have 6 Images
                        current_image.setTranslationY(0);
                        if (old_value != rotate_count)
                        {
                            //if old value is not equal rotate count
                            setValueRandom(image,rotate_count);
                            old_value++;
                        }
                        else// If rotate is reached
                        {
                            last_result = 0;
                            old_value = 0;
                            setImage(next_image,image);
                            eventEnd.eventEnd(image%6,rotate_count);
                        }
                    }


                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
    }

    private void onAnimationRepeat(int old_value , int last_result) {
    }


    private void setImage(ImageView image_View, int value) {
        if(value == Util.BAR)
            image_View.setImageResource(R.drawable.bar_done);
        else if(value == Util.SEVEN)
            image_View.setImageResource(R.drawable.sevent_done);
        else if(value == Util.LEMON)
            image_View.setImageResource(R.drawable.lemon_done);
        else if(value == Util.ORANGE)
            image_View.setImageResource(R.drawable.orange_done);
        else if(value == Util.TRIPLE)
            image_View.setImageResource(R.drawable.triple_done);
        else
            image_View.setImageResource(R.drawable.watermelon_done );
        //Set tage for images to compare result
        image_View.setTag(value);
        last_result = value;


    }
    public int getValue() {
       return Integer.parseInt(next_image.getTag().toString());

    }

    private void SetImage (ImageView current_image, int i){
    }

    //public void setEventEnd(MainActivity mainActivity) {}

}
