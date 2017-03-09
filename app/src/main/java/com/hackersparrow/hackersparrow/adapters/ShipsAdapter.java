package com.hackersparrow.hackersparrow.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hackersparrow.hackersparrow.R;
import com.hackersparrow.hackersparrow.activities.ShipDetailActivity;
import com.hackersparrow.hackersparrow.model.Port;
import com.hackersparrow.hackersparrow.model.Ship;
import com.hackersparrow.hackersparrow.views.ShipsListViewHolder;

import java.io.Serializable;
import java.util.List;

public class ShipsAdapter extends RecyclerView.Adapter<ShipsListViewHolder>{

    private List<Ship> ships;
    private Port port;
    private LayoutInflater layoutInflater;

    public ShipsAdapter(List<Ship> ships, Context context, Port port) {
        this.ships = ships;
        this.port = port;
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
                intent.putExtra("ship", (Serializable) ship);
                intent.putExtra("port", port);
                holder.itemView.getContext().startActivity(intent);
            }

        });
    }

    @Override
    public int getItemCount() {
        return ships.size();
    }

}
