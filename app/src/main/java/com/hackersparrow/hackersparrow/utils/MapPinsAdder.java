package com.hackersparrow.hackersparrow.utils;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hackersparrow.hackersparrow.R;

import java.util.List;

public class MapPinsAdder {
    public interface MapPinnable<E> {
        float getLatitude();
        float getLongitude();
        String getPinDescription();
        String getPinImageUrl();
        E getRelatedModelObject();
    }

    public static void addPins(List<MapPinnable> mapPinnables, final GoogleMap googleMap, final Context context) {
        if (mapPinnables == null || googleMap == null) {
            return;
        }

        for (final MapPinnable pinnable: mapPinnables) {
            final LatLng position = new LatLng(pinnable.getLatitude(), pinnable.getLongitude());
            final String profileImageUrl = pinnable.getPinImageUrl();

            final MarkerOptions marker = new MarkerOptions().position(position).icon(BitmapDescriptorFactory.fromResource(R.drawable.chincheta45px_2)).title(pinnable.getPinDescription());
            marker.snippet(context.getString(R.string.map_pin_hint));
            Marker m = googleMap.addMarker(marker);
            m.setTag(pinnable);
        }
    }
}
