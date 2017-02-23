package com.hackersparrow.hackersparrow.activities;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.hackersparrow.hackersparrow.R;
import com.hackersparrow.hackersparrow.adapters.ShipsAdapter;
import com.hackersparrow.hackersparrow.model.Ship;
import com.hackersparrow.hackersparrow.utils.DownloadXML;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ShipsListActivity extends AppCompatActivity {

    private RecyclerView shipsRecyclerView;
    private List<Ship> shipList = new LinkedList<>();
    private ShipsAdapter adapter;
    private DownloadXML xmlParser = new DownloadXML();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0096C8"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportActionBar().hide();
        }
        else {
            getSupportActionBar().show();
        }

        setContentView(R.layout.activity_ships_list);


        xmlParser.execute("http://spanishcharters.com/api/barcos");

        try {
            shipList = xmlParser.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        shipsRecyclerView = (RecyclerView) findViewById(R.id.ships_lis_recyclerView);
        shipsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ShipsAdapter(shipList, this);
        shipsRecyclerView.setAdapter(adapter);
    }
}
