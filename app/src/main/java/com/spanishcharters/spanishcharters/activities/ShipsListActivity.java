package com.spanishcharters.spanishcharters.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.spanishcharters.spanishcharters.R;
import com.spanishcharters.spanishcharters.adapters.ShipsAdapter;
import com.spanishcharters.spanishcharters.model.Port;
import com.spanishcharters.spanishcharters.model.Ship;
import com.spanishcharters.spanishcharters.utils.ShipsParserXML;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class ShipsListActivity extends AppCompatActivity {

    private RecyclerView shipsRecyclerView;
    private List<Ship> shipList = new LinkedList<>();
    private ShipsAdapter adapter;
    private ShipsParserXML xmlParser = new ShipsParserXML();
    Port myPort = new Port();
    String myPortName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        myPort = (Port) intent.getSerializableExtra("port");

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0096C8"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportActionBar().hide();
        }
        else {
            getSupportActionBar().show();
        }
        if (Locale.getDefault().getLanguage().contentEquals("en")) {
            xmlParser.execute("http://spanishcharters.com/en/api/barcos");
        }else{
            xmlParser.execute("http://spanishcharters.com/es/api/barcos");
        }

        try {
            shipList = xmlParser.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        getSupportActionBar().setTitle(myPort.getName() + " (" +shipList.size() + ")");

        setContentView(R.layout.activity_ships_list);


        shipsRecyclerView = (RecyclerView) findViewById(R.id.ships_lis_recyclerView);
        shipsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ShipsAdapter(shipList, this, myPort);
        shipsRecyclerView.setAdapter(adapter);
    }
}
