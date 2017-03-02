package com.hackersparrow.hackersparrow.activities;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.hackersparrow.hackersparrow.R;
import com.hackersparrow.hackersparrow.model.Port;
import com.hackersparrow.hackersparrow.utils.MapPinsAdder;
import com.hackersparrow.hackersparrow.utils.PortsParserXML;
import com.hackersparrow.hackersparrow.utils.Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.hackersparrow.hackersparrow.utils.Utils.MessageType.DIALOG;
import static com.hackersparrow.hackersparrow.utils.Utils.MessageType.TOAST;

public class MapActivity extends AppCompatActivity implements GoogleMap.OnInfoWindowClickListener {

    private static final int REQUEST_CODE_ASK_FOR_LOCATION_PERMISSION = 10;
    private SupportMapFragment mapFragment;
    private GoogleMap myGoogleMap;
    private List<Port> listOfPorts = new LinkedList<>();
    private String[] arrayPortsNames;
    private float[] arrayPortsLat;
    private float[] arrayPortsLon;
    private PortsParserXML xmlParser = new PortsParserXML();
    private ImageView dialogButton;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent intent = new Intent(MapActivity.this,
                SplashScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);

        builder = new AlertDialog.Builder(this);

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0096C8"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_map);

        dialogButton = (ImageView) findViewById(R.id.map_bar_title_button);

        initializeMap();
        xmlParser.execute("http://spanishcharters.com/api/destinos");

        try {
            listOfPorts = xmlParser.get();

            arrayPortsNames = new String [listOfPorts.size()];
            //arrayPortsLat = new float [listOfPorts.size()];
            //arrayPortsLon = new float [listOfPorts.size()];

            for(int i=0; i<listOfPorts.size(); i++) {
                arrayPortsNames[i] = listOfPorts.get(i).getName();
                //arrayPortsLat[i] = listOfPorts.get(i).getLatitude();
                //arrayPortsLon[i] = listOfPorts.get(i).getLongitude();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("Selecciona un destino");
                builder.setItems(arrayPortsNames, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        //centerMapInPosition(myGoogleMap, arrayPortsLat[item], arrayPortsLon[item]);
                        Port port = listOfPorts.get(item);
                        Intent intent = new Intent(MapActivity.this, ShipsListActivity.class);
                        intent.putExtra("port", port.getName());
                        startActivity(intent);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    private void initializeMap() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.activity_map___map_fragment);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                // check if map is created successfully or not
                if (googleMap == null) {
                    Toast.makeText(getApplicationContext(),
                            "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    setupMap(googleMap);
                    addpins(googleMap);
                }
            }
        });
    }

    private void setupMap(GoogleMap googleMap) {
        myGoogleMap = googleMap;
        centerMapInPosition(googleMap, 36.7166667, -4.4166667);
        //TODO 1: Esto hay que terminarlo, verificar que cuando cargue el mapa se cierra el splash
        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                SplashScreen.maps.finish();
            }
        });
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);                //Activa el mapa hibrido
        googleMap.getUiSettings().setCompassEnabled(true);              //Activa la orientación de la brújula
        googleMap.getUiSettings().setZoomControlsEnabled(true);         //Activa los botones de control de zoom
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);     //Activa el boton de mi localización.
        int isGPSTrackingEnabled = ActivityCompat.checkSelfPermission(getBaseContext(), ACCESS_FINE_LOCATION);
        int assistedGPSEnabled = ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
        if (isGPSTrackingEnabled != PackageManager.PERMISSION_GRANTED && assistedGPSEnabled != PackageManager.PERMISSION_GRANTED) {
            enableUserLocationOnMap();

            return;
        }
        googleMap.setMyLocationEnabled(true);
        myGoogleMap.setOnInfoWindowClickListener(this);
    }

    public static void centerMapInPosition(GoogleMap googleMap, double latitude, double longitude) {
        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(latitude, longitude)).zoom(13).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void enableUserLocationOnMap() {
        // Starting on API 23, it is necessary to perform a runtime check to see
        // if the app has been granted permission to do something, before doing it.
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            myGoogleMap.setMyLocationEnabled(true);
        }
        // If the app has not been granted permission yet, ask the user to grant it
        // (if he already rejected this in the past, show him a short explanation first)
        else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_FINE_LOCATION)) {
                String title = getString(R.string.permission_location_rationale_title);
                String msg = getString(R.string.permission_location_rationale_msg);
                Utils.showAcceptDialog(this, title, msg, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(MapActivity.this,
                                new String[]{ACCESS_FINE_LOCATION},
                                REQUEST_CODE_ASK_FOR_LOCATION_PERMISSION
                        );
                    }
                });
            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION},
                        REQUEST_CODE_ASK_FOR_LOCATION_PERMISSION
                );
            }
        }
    }
    // This method is called after the activity asked the user for some permission during runtime
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_ASK_FOR_LOCATION_PERMISSION) {
            if (ContextCompat.checkSelfPermission(this,
                    ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                String msg = getString(R.string.permission_granted);
                Utils.showMessage(this, msg, TOAST, null);
                myGoogleMap.setMyLocationEnabled(true);
            }
            else {
                String title = getString(R.string.permission_denied_location_title);
                String msg = getString(R.string.permission_denied_location_msg);
                Utils.showMessage(this, msg, DIALOG, title);
            }
        }
    }
    
    public void addpins(GoogleMap googleMap){
        List<MapPinsAdder.MapPinnable> pins = new LinkedList<>();
        for (Port port: listOfPorts) {
            MapPinsAdder.MapPinnable pin = port;

            pins.add(pin);
        }
        MapPinsAdder.addPins(pins, googleMap, this);

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(MapActivity.this,
                ShipsListActivity.class);
        intent.putExtra("marker_title", marker.getTitle());
        startActivity(intent);
    }

}

