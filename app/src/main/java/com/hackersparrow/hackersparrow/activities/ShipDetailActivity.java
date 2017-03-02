package com.hackersparrow.hackersparrow.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.hackersparrow.hackersparrow.R;
import com.hackersparrow.hackersparrow.model.Ship;
import com.hackersparrow.hackersparrow.utils.ShipDetailParserXML;

import java.util.concurrent.ExecutionException;

public class ShipDetailActivity extends AppCompatActivity {

    // STATIC URL FOR DETAIL:
    // http://www.spanishcharters.com/api/barco/id:xx
    // WHERE xx IS THE SHIP IP

    ShipDetailParserXML parserXML = new ShipDetailParserXML();
    final static String detailUrl = "http://www.spanishcharters.com/api/barco/id:";
    private SliderLayout sliderShow;
    TextSliderView textSliderView = new TextSliderView(this);

    private TextView shipPatron;
    private TextView shipCapability;
    private TextView shipRooms;
    private TextView shipMeters;
    private TextView shipWC;
    private TextView shipEquip;
    private TextView shipEspecifications;
    private TextView shipExtras;
    private TextView shipOptionalExtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Ship ship = (Ship) intent.getSerializableExtra("ship");

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0096C8"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setTitle(ship.getName());

        setContentView(R.layout.activity_ship_detail);

        shipPatron = (TextView) findViewById(R.id.detail_ship___ship_patron);
        shipCapability = (TextView) findViewById(R.id.detail_ship___ship_capability);
        shipRooms = (TextView) findViewById(R.id.detail_ship___ship_rooms);
        shipMeters = (TextView) findViewById(R.id.detail_ship___ship_meters);
        shipWC = (TextView) findViewById(R.id.detail_ship___ship_wc);
        shipEquip = (TextView) findViewById(R.id.activity_ship_detail___equipment);
        shipEspecifications = (TextView) findViewById(R.id.activity_ship_detail___specifications);
        shipExtras = (TextView) findViewById(R.id.activity_ship_detail___obligatory_extras);
        shipOptionalExtras = (TextView) findViewById(R.id.activity_ship_detail___opcional_extras);

        //System.out.println(detailUrl + ship.getId()); --> URL_TEST: OK
        parserXML.execute(detailUrl + ship.getId());

        try {
            Ship shipDetail = parserXML.get();

            SliderLayout sliderShow = (SliderLayout) findViewById(R.id.image_slider);

            for (String url: shipDetail.getDetailImages()){
                textSliderView = new TextSliderView(this);
                textSliderView.image(url);
                sliderShow.addSlider(textSliderView);
            }
            System.out.println("PATRON: " + shipDetail.getPatron());
            shipPatron.setText(shipDetail.getPatron());
            shipCapability.setText(shipDetail.getCapability());
            shipMeters.setText(shipDetail.getMeters());
            shipRooms.setText(shipDetail.getRooms());
            shipWC.setText(shipDetail.getWc());
            shipEquip.setText(shipDetail.getEquip());
            shipEspecifications.setText(shipDetail.getEspecifications());
            shipExtras.setText(shipDetail.getExtras());
            shipOptionalExtras.setText(shipDetail.getOptionalExtras());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }




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
