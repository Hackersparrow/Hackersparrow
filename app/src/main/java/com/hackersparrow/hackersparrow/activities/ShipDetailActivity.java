package com.hackersparrow.hackersparrow.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.hackersparrow.hackersparrow.R;
import com.hackersparrow.hackersparrow.model.Ship;
import com.hackersparrow.hackersparrow.utils.ShipDetailParserXML;

public class ShipDetailActivity extends AppCompatActivity {

    // STATIC URL FOR DETAIL:
    // http://www.spanishcharters.com/api/barco/id:xx
    // WHERE xx IS THE SHIP IP

    ShipDetailParserXML parserXML = new ShipDetailParserXML();
    final static String detailUrl = "http://www.spanishcharters.com/api/barco/id:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Ship ship = (Ship) intent.getSerializableExtra("ship");

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0096C8"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setTitle(ship.getName());

        setContentView(R.layout.activity_ship_detail);



        //System.out.println(detailUrl + ship.getId()); --> URL_TEST: OK

        parserXML.execute(detailUrl + ship.getId());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.detail_menu_solicitar) {
            Intent i = new Intent(ShipDetailActivity.this, UserInfoActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
