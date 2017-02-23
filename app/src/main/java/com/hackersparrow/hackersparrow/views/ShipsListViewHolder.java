package com.hackersparrow.hackersparrow.views;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hackersparrow.hackersparrow.R;
import com.hackersparrow.hackersparrow.model.Ship;
import com.squareup.picasso.Picasso;

public class ShipsListViewHolder extends RecyclerView.ViewHolder{

    private Ship ship;
    private ImageView shipImage;
    private TextView shipName;
    private TextView shipCapability;
    private TextView shipMeters;

    public ShipsListViewHolder(View itemView) {
        super(itemView);
        shipImage = (ImageView) itemView.findViewById(R.id.row_ship___ship_image);
        shipName = (TextView) itemView.findViewById(R.id.row_ship___ship_name);
        shipCapability = (TextView) itemView.findViewById(R.id.row_ship___ship_capability);
        shipMeters = (TextView) itemView.findViewById(R.id.row_ship___ship_meters);

    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
        shipName.setText(ship.getName());
        shipCapability.setText(ship.getCapability());
        shipMeters.setText("" + ship.getMeters());
        Picasso.with(itemView.getContext()).load(ship.getImgURL()).into(shipImage);
    }

}
