package com.hackersparrow.hackersparrow.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;

import com.hackersparrow.hackersparrow.R;

public class UserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0096C8"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setTitle("Solicitar información");

        setContentView(R.layout.activity_user_info);
    }
}
