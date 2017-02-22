package com.hackersparrow.hackersparrow.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hackersparrow.hackersparrow.R;
import com.hackersparrow.hackersparrow.adapters.ShipsAdapter;
import com.hackersparrow.hackersparrow.model.Ship;

import java.util.LinkedList;
import java.util.List;

public class ShipsListActivity extends AppCompatActivity {

    private RecyclerView shipsRecyclerView;
    private List<Ship> shipList = new LinkedList<>();
    private ShipsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ships_list);

        for (int i = 0; i < 20; i++){
            Ship newShip = new Ship();
            newShip.setName("Barquito " + i);
            newShip.setCapability(i + "metros");
            shipList.add(newShip);
        }

        shipsRecyclerView = (RecyclerView) findViewById(R.id.ships_lis_recyclerView);
        shipsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ShipsAdapter(shipList, this);
        shipsRecyclerView.setAdapter(adapter);
    }
}
