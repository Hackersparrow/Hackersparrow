package com.spanishcharters.spanishcharters.activities;

import android.content.Context;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.spanishcharters.spanishcharters.R;
import com.spanishcharters.spanishcharters.model.Port;
import com.spanishcharters.spanishcharters.model.Ship;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserInfoActivity extends AppCompatActivity {

    private TextView nameText;
    private TextView emailText;
    private TextView phoneText;
    private ImageView phoneButton;
    private EditText editText;
    private Button sendButton;
    private CheckBox checkBox;
    private TextView termsText;
    private MaterialCalendarView materialCalendarView;
    private RadioGroup radioGroup;
    private Calendar calendar = Calendar.getInstance();
    CalendarDay selectedDate = null;
    private int activeButton = 0;
    private String activeButtonText = "";
    private int activeButtonDuration = 0;
            // 0 > WEEK
            // 1 > SHARE
            // 2 > DAY

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        final Ship ship = (Ship) intent.getSerializableExtra("selected_ship");
        final Port port = (Port) intent.getSerializableExtra("port");

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0096C8"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setTitle(R.string.userInfo_Title);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar);
        setContentView(R.layout.activity_user_info);

        phoneButton = (ImageView) findViewById(R.id.info_phone_button);
        nameText = (TextView) findViewById(R.id.info_name);
        emailText = (TextView) findViewById(R.id.info_email);
        phoneText = (TextView) findViewById(R.id.info_phone);
        editText = (EditText) findViewById(R.id.textArea_information);
        sendButton = (Button) findViewById(R.id.info_send_button);
        materialCalendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        checkBox = (CheckBox) findViewById(R.id.info_checkbox);
        termsText = (TextView) findViewById(R.id.info_condition);

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

        termsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://www.spanishcharters.com/condiciones-generales-contratacion.html";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = "+34952020946";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });

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

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (checkBox.isChecked() == true){
                    sendButton.setTextColor(0xFFFFFFFF);
                }else{
                    sendButton.setTextColor(0xFFAAAAAA);
                }
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()) {
                    String name = nameText.getText().toString();
                    String email = emailText.getText().toString();
                    String phone = phoneText.getText().toString();
                    String additionalInfo = editText.getText().toString();
                    String startDate = materialCalendarView.getSelectedDates().get(0).toString();

                    String myStartDate = startDate.substring(startDate.indexOf("{") + 1, startDate.indexOf("}"));

                    String month = myStartDate.substring(myStartDate.indexOf("-") + 1, myStartDate.lastIndexOf("-"));
                    String correctMonth = String.valueOf((Integer.parseInt(month) + 1));

                    String correctStartDate = myStartDate.replace("-"+month+"-", "-"+correctMonth+"-");

                    Log.d("FECHA", startDate);


                    sendPost(view.getContext(), name, email, phone, activeButtonText, correctStartDate, String.valueOf(activeButtonDuration), additionalInfo, ship, port);

                    String snackBarText = "Datos enviados correctamente";

                    View rootView = findViewById(android.R.id.content);
                    Snackbar mySnackBar = Snackbar.make(rootView, snackBarText, Snackbar.LENGTH_LONG);

                    mySnackBar.show();

                    View snackbarView = mySnackBar.getView();
                    TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setMaxLines(7);
                }
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        CalendarDay firstDay;
        materialCalendarView.setVisibility(View.VISIBLE);
        editText.setVisibility(View.VISIBLE);
        checkBox.setVisibility(View.VISIBLE);
        termsText.setVisibility(View.VISIBLE);
        switch(view.getId()) {
            case R.id.rb_week:
                if (checked)
                    activeButton = 0;
                    activeButtonDuration = 7;
                    activeButtonText = "Una semana";
                    selectedDate = materialCalendarView.getSelectedDate();
                    long ltime = selectedDate.getDate().getTime() + 6*24*60*60*1000;
                    Date end_range_date = new Date(ltime);
                    materialCalendarView.selectRange(CalendarDay.from(selectedDate.getDate()), CalendarDay.from(end_range_date));
                    break;
            case R.id.rb_share:
                if (checked)
                    activeButton = 1;
                    activeButtonDuration = 1;
                    activeButtonText = "Compartir barco";
                    Log.d("OPTION", "" + materialCalendarView.getSelectedDates());
                    firstDay = materialCalendarView.getSelectedDates().get(0);
                    materialCalendarView.clearSelection();
                    materialCalendarView.setDateSelected(firstDay, true);
                    break;
            case R.id.rb_day:
                if (checked)
                    activeButton = 2;
                    activeButtonDuration = 1;
                    activeButtonText = "Un día";
                    firstDay = materialCalendarView.getSelectedDates().get(0);
                    materialCalendarView.clearSelection();
                    materialCalendarView.setDateSelected(firstDay, true);
                    break;
        }
    }

    public void sendPost(Context context, String name, String email, String phone, String reservationType, String startDate, String numberDays, String additionalInfo, final Ship myShip, final Port destination){
        String URL = "http://www.spanishcharters.com/api/request_info";
        final String sendName = name;
        final String sendEmail = email;
        final String sendPhone = phone;
        final String sendReservationType = reservationType;
        final String sendStartDate = startDate;
        final String sendNumberDays = numberDays;
        final String sendAdditionalInfo = additionalInfo;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("REGISTER_USER", "Todo ha ido bien");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("REGISTER_USER", "Algo ha ido mal" + error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                //params.put("Content-Type","application/json");
                params.put("name", sendName);
                params.put("email", sendEmail);
                params.put("phone", sendPhone);
                params.put("reservation_type", sendReservationType);
                params.put("Dias", sendNumberDays);
                params.put("start_date", sendStartDate);
                params.put("additional_info", sendAdditionalInfo);
                params.put("ship", myShip.getName());
                params.put("destination", destination.getName());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

}
