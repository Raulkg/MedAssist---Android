package info.theh2o.rahul.medassist;

/**
 * Created by Rahulkumat on 4/19/2016.
 */


        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.graphics.Path;
        import android.graphics.PixelFormat;
        import android.graphics.Typeface;
        import android.os.Bundle;
        import android.view.View;
        import android.view.Window;
        import android.view.WindowManager;
        import android.view.animation.AccelerateDecelerateInterpolator;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;

public class SpalshScreenActivity extends Activity {
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);

        Typeface blockFonts = Typeface.createFromAsset(getAssets(),"fonts/Caviar_Dreams_Bold.ttf");
        TextView txtSampleTxt = (TextView) findViewById(R.id.splashtext);
        TextView txtSampleTxt1 = (TextView) findViewById(R.id.copyrt);
        txtSampleTxt.setTypeface(blockFonts);
        txtSampleTxt1.setTypeface(blockFonts);
        StartAnimations();


        Thread timer = new Thread(){

            public void run(){

                try{
                    sleep(10000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent openMainActivity = new Intent(SpalshScreenActivity.this, LoginActivity.class);
                    startActivity(openMainActivity);
                }
            }

        };
        timer.start();



    }
    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.logo);
        iv.clearAnimation();
        iv.startAnimation(anim);


        TextView txtSampleTxt = (TextView) findViewById(R.id.splashtext);
        TextView txtSampleTxt1 = (TextView) findViewById(R.id.copyrt);
        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        txtSampleTxt.clearAnimation();
        txtSampleTxt.startAnimation(anim);




    }





}