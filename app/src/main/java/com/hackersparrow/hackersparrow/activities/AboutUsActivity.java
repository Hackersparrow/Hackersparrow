package com.hackersparrow.hackersparrow.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hackersparrow.hackersparrow.MainActivity;
import com.hackersparrow.hackersparrow.R;

public class AboutUsActivity extends AppCompatActivity {


    TextView linkedinTextAdri;
    TextView linkedinTextSaul;
    TextView linkedinTextMatias;
    TextView linkedinTextMarcos;
    TextView linkedinTextAlberto;
    TextView linkedinTextAlex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        linkedinTextAdri = (TextView) findViewById(R.id.linkedin_adri_tarjeta);
        linkedinTextSaul = (TextView) findViewById(R.id.linkedin_adri_tarjeta);
        linkedinTextMatias = (TextView) findViewById(R.id.linkedin_adri_tarjeta);
        linkedinTextMarcos = (TextView) findViewById(R.id.linkedin_adri_tarjeta);
        linkedinTextAlberto = (TextView) findViewById(R.id.linkedin_adri_tarjeta);
        linkedinTextAlex = (TextView) findViewById(R.id.linkedin_adri_tarjeta);

      /*  linkedinTextAlberto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(urlCommets));
                startActivity(viewIntent);
            }
        });*/



    }
}
