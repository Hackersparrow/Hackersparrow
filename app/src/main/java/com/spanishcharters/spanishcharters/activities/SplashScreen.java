package com.spanishcharters.spanishcharters.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spanishcharters.spanishcharters.R;
import com.spanishcharters.spanishcharters.utils.NetworkChecker;

import net.bohush.geometricprogressview.GeometricProgressView;

public class SplashScreen extends Activity {
    public static Activity maps;
    private NetworkChecker networkChecker = new NetworkChecker();
    private TextView errorText;
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

        errorText = (TextView) findViewById(R.id.splash_error_text);

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
                    boolean tryReconnect = false;
                    int waited = 0;
                    int noConnectionTotal = 0;
                    // Splash screen pause time
                    while (waited < 4000) {
                        sleep(100);
                        waited += 100;
                        if (!networkChecker.isNetworkAvailable(getBaseContext())){
                            noConnectionTotal = noConnectionTotal + 100;
                            waited = 0;
                            if (noConnectionTotal >= 10000){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        errorText.setVisibility(View.VISIBLE);
                                        Animation anim = new AlphaAnimation(0.0f, 1.0f);
                                        anim.setDuration(400); //You can manage the blinking time with this parameter
                                        anim.setStartOffset(20);
                                        anim.setRepeatMode(Animation.REVERSE);
                                        anim.setRepeatCount(Animation.INFINITE);
                                        errorText.startAnimation(anim);
                                    }
                                });
                                noConnectionTotal = 0;
                                tryReconnect = true;
                            }
                        }
                    }
                    if (tryReconnect){
                        Intent intent = new Intent(SplashScreen.this, MapActivity.class);
                        intent.putExtra("rc", true);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                    }else{
                        SplashScreen.this.finish();
                    }

                } catch (InterruptedException e) {
                    // do nothing
                }

            }
        };
        splashTread.start();


    }

}
