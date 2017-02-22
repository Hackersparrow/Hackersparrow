package com.hackersparrow.hackersparrow.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.hackersparrow.hackersparrow.R;
import com.hackersparrow.hackersparrow.adapters.ShipsAdapter;
import com.hackersparrow.hackersparrow.model.Ship;

import java.util.List;

public class ShipsListActivity extends AppCompatActivity {

    private RecyclerView shipsRecyclerView;
    private List<Ship> shipList;
    private ShipsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ships_list);

    }
}
