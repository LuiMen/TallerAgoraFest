package com.luxelare.talleragorafest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng mochis = new LatLng(25.790466, -108.985886);
        mMap.addMarker(new MarkerOptions().position(mochis).title("marcador en los mochis "));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mochis));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                MarkerOptions marker = new MarkerOptions().position(
                        new LatLng(latLng.latitude, latLng.longitude)).title("nuevo marcador");
                mMap.addMarker(marker);
                Toast.makeText(getApplicationContext(), "LATITUD :"+latLng.latitude+", LONGITUD :"+latLng.longitude, Toast.LENGTH_SHORT).show();
                MostrarDialogoUbicacion(String.valueOf(latLng.latitude),String.valueOf(latLng.longitude));

            }
        });

    }

    public void MostrarDialogoUbicacion(final String lat,final  String lng) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        dialogBuilder.setTitle("Selecciona Ubicación");
        dialogBuilder.setMessage("Estas seguro que quieres obtener el clima de esta ubicación ?");
        dialogBuilder.setPositiveButton("Si, quiero esta ubicación", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("lat",lat);
                returnIntent.putExtra("lng",lng);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
        dialogBuilder.setNegativeButton("No, quiero otra ubicación", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mMap.clear();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        //final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        //dialogBuilder.setView(dialogView);
        //final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);
        dialogBuilder.setTitle("Aviso");
        dialogBuilder.setMessage("Estas seguro que quieres salir de la seleccion de ubicacion?");
        dialogBuilder.setPositiveButton("Si, quiero salir ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });
        dialogBuilder.setNegativeButton("No, quiero seguir en este menu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();

    }
}
