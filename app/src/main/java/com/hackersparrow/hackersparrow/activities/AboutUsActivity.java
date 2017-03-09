package com.hackersparrow.hackersparrow.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hackersparrow.hackersparrow.R;

public class AboutUsActivity extends AppCompatActivity {


    ImageView linkedin_url_saul;
    ImageView linkedin_url_matias;
    ImageView linkedin_url_marcos;
    ImageView linkedin_url_adri;
    ImageView linkedin_url_alex;
    ImageView linkedin_url_alberto;
    ImageView github_url_saul;
    ImageView github_url_matias;
    ImageView github_url_marcos;
    ImageView github_url_adri;
    ImageView github_url_alex;
    ImageView github_url_alberto;
    TextView web_url_marcos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0096C8"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setTitle("About us");
        setContentView(R.layout.activity_about_us);

        linkedin_url_saul= (ImageView) findViewById(R.id.card_view_linkedin_saul);
        linkedin_url_matias= (ImageView) findViewById(R.id.card_view_linkedin_matias);
        linkedin_url_marcos= (ImageView) findViewById(R.id.card_view_linkedin_marcos);
        linkedin_url_adri= (ImageView) findViewById(R.id.card_view_linkedin_adri);
        linkedin_url_alex= (ImageView) findViewById(R.id.card_view_linkedin_alex);
        linkedin_url_alberto= (ImageView) findViewById(R.id.card_view_linkedin_alberto);
        github_url_saul= (ImageView) findViewById(R.id.card_view_github_saul);
        github_url_matias= (ImageView) findViewById(R.id.card_view_github_matias);
        github_url_marcos= (ImageView) findViewById(R.id.card_view_github_marcos);
        github_url_adri= (ImageView) findViewById(R.id.card_view_github_adri);
        github_url_alex= (ImageView) findViewById(R.id.card_view_github_alex);
        github_url_alberto= (ImageView) findViewById(R.id.card_view_github_alberto);
        web_url_marcos = (TextView) findViewById(R.id.web_marcos_tarjeta);

        web_url_marcos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://marcosvaldi.com"));
                startActivity(viewIntent);
            }
        });

        github_url_saul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://github.com/smappdev"));
                startActivity(viewIntent);
            }
        });


        github_url_matias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://github.com/jmajyo"));
                startActivity(viewIntent);
            }
        });


        github_url_marcos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://github.com/markinhos3"));
                startActivity(viewIntent);
            }
        });


        github_url_adri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://github.com/adriidm"));
                startActivity(viewIntent);
            }
        });


        github_url_alex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://github.com/alexkassabian"));
                startActivity(viewIntent);
            }
        });

        github_url_alberto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://github.com/Alber81"));
                startActivity(viewIntent);
            }
        });


        linkedin_url_saul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.linkedin.com/in/saulmarinquerido"));
                startActivity(viewIntent);

            }
        }); linkedin_url_matias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.linkedin.com/in/josematiasalcaidejimenez"));
                startActivity(viewIntent);

            }
        }); linkedin_url_marcos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.linkedin.com/in/marcosvaldi"));
                startActivity(viewIntent);

            }
        }); linkedin_url_adri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.linkedin.com/in/adrian-diaz-mancera"));
                startActivity(viewIntent);

            }
        }); linkedin_url_alex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.linkedin.com/in/alejandro-rodríguez-sanz"));
                startActivity(viewIntent);

            }
        });
        linkedin_url_alberto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.linkedin.com/in/alberto-martinez-quintana-b1306b130"));
                startActivity(viewIntent);

            }
        });



    }
}
