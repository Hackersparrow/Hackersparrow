package com.hackersparrow.hackersparrow.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.maps.model.Marker;
import com.hackersparrow.hackersparrow.R;
import com.hackersparrow.hackersparrow.adapters.ShipsAdapter;
import com.hackersparrow.hackersparrow.model.Ship;
import com.hackersparrow.hackersparrow.utils.ShipsParserXML;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ShipsListActivity extends AppCompatActivity {

    private RecyclerView shipsRecyclerView;
    private List<Ship> shipList = new LinkedList<>();
    private ShipsAdapter adapter;
    private ShipsParserXML xmlParser = new ShipsParserXML();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String markerTitle = intent.getSerializableExtra("marker_title").toString();

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0096C8"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportActionBar().hide();
        }
        else {
            getSupportActionBar().show();
        }

        xmlParser.execute("http://spanishcharters.com/api/barcos");

        try {
            shipList = xmlParser.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        getSupportActionBar().setTitle(markerTitle + " (" +shipList.size() + ")");

        setContentView(R.layout.activity_ships_list);


        shipsRecyclerView = (RecyclerView) findViewById(R.id.ships_lis_recyclerView);
        shipsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ShipsAdapter(shipList, this);
        shipsRecyclerView.setAdapter(adapter);
    }
}
