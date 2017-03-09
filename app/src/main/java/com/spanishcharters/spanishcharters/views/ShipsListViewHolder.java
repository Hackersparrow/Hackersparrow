package com.spanishcharters.spanishcharters.views;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.spanishcharters.spanishcharters.R;
import com.spanishcharters.spanishcharters.model.Ship;
import com.squareup.picasso.Picasso;

public class ShipsListViewHolder extends RecyclerView.ViewHolder{

    private Ship ship;
    private TextView shipPatron;
    private ImageView shipImage;
    private TextView shipName;
    private TextView shipCapability;
    private TextView shipMeters;
    private TextView shipRooms;
    private TextView shipType;
    private TextView shipPrice;

    public ShipsListViewHolder(View itemView) {
        super(itemView);
        shipImage = (ImageView) itemView.findViewById(R.id.row_ship___ship_image);
        shipName = (TextView) itemView.findViewById(R.id.row_ship___ship_name);
        shipCapability = (TextView) itemView.findViewById(R.id.row_ship___ship_capability);
        shipMeters = (TextView) itemView.findViewById(R.id.row_ship___ship_meters);
        shipPatron = (TextView) itemView.findViewById(R.id.row_ship___ship_patron);
        shipRooms = (TextView) itemView.findViewById(R.id.row_ship___ship_rooms);
        shipType = (TextView) itemView.findViewById(R.id.row_ship___ship_type);
        shipPrice = (TextView) itemView.findViewById(R.id.row_ship___ship_price);
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
        Picasso.with(itemView.getContext()).load(ship.getImgURL()).into(shipImage);
        shipPatron.setText(ship.getPatron());
        shipName.setText(ship.getName());
        shipCapability.setText(ship.getCapability());
        shipMeters.setText("" + ship.getMeters());
        shipRooms.setText(ship.getRooms());
        shipType.setText(ship.getType());
        shipPrice.setText(ship.getPrice());
    }

}
