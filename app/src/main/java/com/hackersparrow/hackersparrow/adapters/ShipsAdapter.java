package com.hackersparrow.hackersparrow.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hackersparrow.hackersparrow.R;
import com.hackersparrow.hackersparrow.activities.MapActivity;
import com.hackersparrow.hackersparrow.activities.ShipDetailActivity;
import com.hackersparrow.hackersparrow.activities.ShipsListActivity;
import com.hackersparrow.hackersparrow.activities.SplashScreen;
import com.hackersparrow.hackersparrow.model.Ship;
import com.hackersparrow.hackersparrow.views.ShipsListViewHolder;

import java.util.List;

public class ShipsAdapter extends RecyclerView.Adapter<ShipsListViewHolder>{

    private List<Ship> ships;
    private LayoutInflater layoutInflater;

    public ShipsAdapter(List<Ship> ships, Context context) {
        this.ships = ships;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ShipsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.row_ship, parent, false);
        return new ShipsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ShipsListViewHolder holder, int position) {
        final Ship ship = ships.get(position);
        holder.setShip(ship);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ShipDetailActivity.class);
                holder.itemView.getContext().startActivity(intent);
            }

        });
    }

    @Override
    public int getItemCount() {
        return ships.size();
    }

}
