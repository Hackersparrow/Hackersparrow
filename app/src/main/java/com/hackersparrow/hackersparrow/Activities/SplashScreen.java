package com.hackersparrow.hackersparrow.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import com.hackersparrow.hackersparrow.R;

import net.bohush.geometricprogressview.GeometricProgressView;

public class SplashScreen extends Activity {
    public static Activity maps;
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
    /** Called when the activity is first created. */
    Thread splashTread;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        maps=this;
        StartAnimations();
    }
    private void StartAnimations() {
        GeometricProgressView progressView = (GeometricProgressView) findViewById(R.id.progressView);
        progressView.setType(GeometricProgressView.TYPE.KITE);
        progressView.setNumberOfAngles(15);
        progressView.setColor(Color.parseColor("#0096C8"));
        progressView.setDuration(1200);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        //ImageView iv = (ImageView) findViewById(R.id.splash);
        //iv.clearAnimation();
        //iv.startAnimation(anim);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 5000) {
                        sleep(100);
                        waited += 100;
                        if (!isNetworkAvailable(getBaseContext())){
                            waited = 0;
                        }

                    }
                    //Intent intent = new Intent(SplashScreen.this,
                    //        MapActivity.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    //startActivity(intent);
                    //SplashScreen.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    SplashScreen.this.finish();
                }

            }
        };
        splashTread.start();


    }
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected() && info.isAvailable();
    }
}
