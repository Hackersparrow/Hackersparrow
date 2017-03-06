package com.hackersparrow.hackersparrow.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import com.hackersparrow.hackersparrow.R;
import com.hackersparrow.hackersparrow.model.Ship;

import java.util.Date;

public class UserInfoActivity extends AppCompatActivity {

    private TextView nameText;
    private TextView emailText;
    private TextView phoneText;
    private Button sendButton;
    private Date date = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        final Ship ship = (Ship) intent.getSerializableExtra("selected_ship");

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0096C8"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setTitle(R.string.userInfo_Title);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar);
        setContentView(R.layout.activity_user_info);

        nameText = (TextView) findViewById(R.id.info_name);
        emailText = (TextView) findViewById(R.id.info_email);
        phoneText = (TextView) findViewById(R.id.info_phone);
        sendButton = (Button) findViewById(R.id.info_send_button);
        VideoView videoview = (VideoView) findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.intro);
        videoview.setVideoURI(uri);
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
        videoview.start();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameText.getText().toString();
                String email = emailText.getText().toString();
                String phone = phoneText.getText().toString();

                String snackBarText =
                        getString(R.string.userInfo_sent_title)
                        + getString(R.string.userInfo_sent_name) + name + "\n"
                        + getString(R.string.userInfo_sent_email) + email + "\n"
                        + getString(R.string.userInfo_sent_phone) + phone + "\n"
                        + getString(R.string.userInfo_sent_ship_id) + ship.getId() + "\n"
                        + getString(R.string.userInfo_sent_ship_name) + ship.getName();

                View rootView = findViewById(android.R.id.content);
                Snackbar mySnackBar = Snackbar.make(rootView, snackBarText, Snackbar. LENGTH_LONG);

                mySnackBar.show();

                View snackbarView = mySnackBar.getView();
                TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setMaxLines(7);
            }
        });
    }
}
