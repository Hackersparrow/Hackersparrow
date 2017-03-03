package com.hackersparrow.hackersparrow.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
        //System.out.println(ship.getId() + " " + ship.getName());

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0096C8"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setTitle("Solicitar información");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar);
        setContentView(R.layout.activity_user_info);

        nameText = (TextView) findViewById(R.id.info_name);
        emailText = (TextView) findViewById(R.id.info_email);
        phoneText = (TextView) findViewById(R.id.info_phone);
        sendButton = (Button) findViewById(R.id.info_send_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameText.getText().toString();
                String email = emailText.getText().toString();
                String phone = phoneText.getText().toString();

                String snackBarText =
                        "Datos enviados al server...\n\n"
                        + "Nombre: " + name + "\n"
                        + "Email: " + email + "\n"
                        + "Phone: " + phone + "\n"
                        + "ShipID: " + ship.getId() + "\n"
                        + "ShipName: " + ship.getName();

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
