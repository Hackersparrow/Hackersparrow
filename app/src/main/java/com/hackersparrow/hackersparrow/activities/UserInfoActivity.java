package com.hackersparrow.hackersparrow.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.VideoView;

import com.hackersparrow.hackersparrow.R;
import com.hackersparrow.hackersparrow.model.Ship;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;
import java.util.Date;

public class UserInfoActivity extends AppCompatActivity {

    private TextView nameText;
    private TextView emailText;
    private TextView phoneText;
    private Button sendButton;
    private MaterialCalendarView materialCalendarView;
    private RadioGroup radioGroup;
    private Calendar calendar = Calendar.getInstance();
    CalendarDay selectedDate = null;
    private int activeButton = 0;
            // 0 > WEEK
            // 1 > SHARE
            // 2 > DAY

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
        materialCalendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
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
        materialCalendarView.setDateSelected(calendar, true);


        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                if (activeButton ==0){
                        selectedDate = materialCalendarView.getSelectedDate();
                        long ltime = selectedDate.getDate().getTime() + 6*24*60*60*1000;
                        Date end_range_date = new Date(ltime);
                        materialCalendarView.selectRange(CalendarDay.from(selectedDate.getDate()), CalendarDay.from(end_range_date));
                }
            }
        });

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

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        CalendarDay firstDay;
        materialCalendarView.setVisibility(View.VISIBLE);
        switch(view.getId()) {
            case R.id.rb_week:
                if (checked)
                    activeButton = 0;
                    selectedDate = materialCalendarView.getSelectedDate();
                    long ltime = selectedDate.getDate().getTime() + 6*24*60*60*1000;
                    Date end_range_date = new Date(ltime);
                    materialCalendarView.selectRange(CalendarDay.from(selectedDate.getDate()), CalendarDay.from(end_range_date));
                    break;
            case R.id.rb_share:
                if (checked)
                    activeButton = 1;
                    Log.d("OPTION", "" + materialCalendarView.getSelectedDates());
                    firstDay = materialCalendarView.getSelectedDates().get(0);
                    materialCalendarView.clearSelection();
                    materialCalendarView.setDateSelected(firstDay, true);
                    break;
            case R.id.rb_day:
                if (checked)
                    activeButton = 2;
                    firstDay = materialCalendarView.getSelectedDates().get(0);
                    materialCalendarView.clearSelection();
                    materialCalendarView.setDateSelected(firstDay, true);
                    break;
        }
    }

}
