package com.hackersparrow.hackersparrow.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.hackersparrow.hackersparrow.R;
import com.hackersparrow.hackersparrow.model.Port;
import com.hackersparrow.hackersparrow.utils.MapPinsAdder;
import com.hackersparrow.hackersparrow.utils.Utils;

import java.util.LinkedList;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.hackersparrow.hackersparrow.utils.Utils.MessageType.DIALOG;
import static com.hackersparrow.hackersparrow.utils.Utils.MessageType.TOAST;

public class MapActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ASK_FOR_LOCATION_PERMISSION = 10;
    private SupportMapFragment mapFragment;
    private GoogleMap myGoogleMap;
    private List<Port> listOfPorts = new LinkedList<>();
    private Port port1 = new Port();
    private Port port2 = new Port();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initializeMap();
        port1.setId("2");
        port1.setLatitude((float) 36.597096683988646);
        port1.setLongitude((float) -4.511904716491699);
        port1.setName("Costa del Sol");
        port1.setUrl("http://www.andaluciadeviaje.es/visitfotos/400px/2012-02-243161ec.jpg");
        listOfPorts.add(port1);

        port2.setId("1");
        port2.setLatitude((float) 38.9149197);
        port2.setLongitude((float) 1.4374745);
        port2.setName("Ibiza");
        port2.setUrl("http://guias.masmar.net/var/masmar/storage/images/gu%C3%ADas/puertos/baleares/puerto-deportivo-de-santa-eularia.-ibiza/197205-1-esl-ES/Puerto-deportivo-de-Santa-Eularia.-Ibiza_articlefull.jpg");
        listOfPorts.add(port2);
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


}

