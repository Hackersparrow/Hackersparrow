package com.spanishcharters.spanishcharters.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.spanishcharters.spanishcharters.R;
import com.spanishcharters.spanishcharters.model.Port;
import com.spanishcharters.spanishcharters.model.Ship;
import com.spanishcharters.spanishcharters.utils.ShipDetailParserXML;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class ShipDetailActivity extends AppCompatActivity {

    // STATIC URL FOR DETAIL:
    // http://www.spanishcharters.com/api/barco/id:xx
    // WHERE xx IS THE SHIP IP

    ShipDetailParserXML parserXML = new ShipDetailParserXML();
    String detailUrl = "http://www.spanishcharters.com/api/barco/id:";
    private SliderLayout sliderShow;
    TextSliderView textSliderView = new TextSliderView(this);
    private Ship shipDetail;
    private TextView shipPatron;
    private TextView shipCapability;
    private TextView shipRooms;
    private TextView shipMeters;
    private TextView shipWC;
    private TextView shipEquip;
    private TextView shipEspecifications;
    private TextView shipExtras;
    private TextView shipOptionalExtras;

    private TextView titleText;
    private TextView titleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Ship ship = (Ship) intent.getSerializableExtra("ship");
        final Port port = (Port) intent.getSerializableExtra("port");

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0096C8"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setTitle(ship.getName());
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_ships_details);

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
        titleText = (TextView) findViewById(R.id.detail_bar_title_text);
        titleButton = (TextView) findViewById(R.id.detail_bar_title_button);

        if (Locale.getDefault().getLanguage().contentEquals("en")) {
            detailUrl = "http://www.spanishcharters.com/en/api/barco/id:";
        }else{
            detailUrl = "http://www.spanishcharters.com/es/api/barco/id:";
        }

        parserXML.execute(detailUrl + ship.getId());

        try {
            shipDetail = parserXML.get();
            shipDetail.setId(ship.getId());

            titleText.setText(shipDetail.getName());
            SliderLayout sliderShow = (SliderLayout) findViewById(R.id.image_slider);

            for (String url: shipDetail.getDetailImages()){
                textSliderView = new TextSliderView(this);
                textSliderView.image(url);
                sliderShow.addSlider(textSliderView);
            }
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

        titleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ShipDetailActivity.this, UserInfoActivity.class);
                i.putExtra("selected_ship", shipDetail);
                i.putExtra("port", port);
                startActivity(i);
            }
        });


    }
}
