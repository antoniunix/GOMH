package com.go_sharp.gomh;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.go_sharp.gomh.contextApp.ContextApp;
import com.go_sharp.gomh.dto.DtoBundle;
import com.go_sharp.gomh.dto.DtoReportCensus;
import com.go_sharp.gomh.dto.DtoSepomex;
import com.go_sharp.gomh.listener.OnFinishLocation;
import com.go_sharp.gomh.model.ModelReportPublicity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import net.gshp.api_time_module.config.Config;
import net.gshp.api_time_module.config.MD5;
import net.panamiur.geolocation.Geolocation;

import org.w3c.dom.ProcessingInstruction;

import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by leo on 3/05/18.
 */

public class ReportPublicity extends AppCompatActivity implements OnMapReadyCallback,
        OnFinishLocation, AdapterView.OnItemSelectedListener,
        TextWatcher, GoogleMap.OnMarkerDragListener {

    private MapView mapView;
    private GoogleMap map;
    private double lat, lon;
    private EditText edtStreet, edtLeftStreet, edtRightStreet, edtDelegacion, edtnumberPhone, edtEmail;
    private Spinner spnSuburb;
    private AutoCompleteTextView edtCp;
    private ModelReportPublicity modelReportPublicity;
    private DtoReportCensus dtoReportCensus;
    private CircleOptions circleOptions;
    public boolean setLocation = false;
    private Button btnPhoto, btnSave;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_publicity);
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        modelReportPublicity = new ModelReportPublicity(this, this, this);
        dtoReportCensus = new DtoReportCensus();
        mapView = (MapView) findViewById(R.id.map);
        edtStreet = findViewById(R.id.edt_street);
        edtLeftStreet = findViewById(R.id.edt_street_left);
        edtRightStreet = findViewById(R.id.edt_street_right);
        edtCp = findViewById(R.id.edt_cp);
        edtnumberPhone = findViewById(R.id.edt_number_phone);
        edtEmail = findViewById(R.id.edt_email);
        btnSave = findViewById(R.id.btnSave);
        btnPhoto = findViewById(R.id.btnPhoto);

        spnSuburb.setOnItemSelectedListener(this);
        edtCp.addTextChangedListener(this);
        mapView.onCreate(savedInstanceState);
        setUpMapIfNeeded();
        edtCp.setAdapter(modelReportPublicity.getAdapterCp());
        edtCp.setThreshold(1);

    }

    private void setUpMapIfNeeded() {
        if (map == null) {
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (map != null) {
            setUpMap();
            modelReportPublicity.onStart();
        }

    }

    private void setUpMap() {
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setZoomControlsEnabled(false);
        map.getMaxZoomLevel();
        map.setOnMarkerDragListener(this);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != -1
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != -1) {
            map.setMyLocationEnabled(true);
        }
        CameraUpdate zoom = CameraUpdateFactory.newLatLngZoom(new LatLng(22.7,
                -102.6), 3);
        map.moveCamera(zoom);
    }

    @Override
    public void onFinishLocation(Location location) {
        if (location != null) {
            if (map != null) {
                map.clear();
                circleOptions = new CircleOptions()
                        .center(new LatLng(location.getLatitude(), location
                                .getLongitude())).radius(location.getAccuracy())
                        .strokeColor(new Color().argb(50, 128, 128, 128));
                map.addCircle(circleOptions);
                map.addMarker(new MarkerOptions().position(
                        new LatLng(location.getLatitude(), location.getLongitude()))
                        .icon(BitmapDescriptorFactory
                                .fromResource(R.drawable.map)).draggable(true));
                if (!setLocation) {
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                            location.getLatitude(), location.getLongitude()), 16), 2000, null);
                    setLocation = true;
                }
                lat = location.getLatitude();
                lon = location.getLongitude();
                setDirections(lat, lon);
            }
        }
    }

    private void setDirections(double lat, double lon) {
        try {
            Geocoder geo = new Geocoder(ContextApp.context, Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(lat, lon, 1);
            Log.e("address", "address " + addresses.toString());
            if (!addresses.isEmpty()) {
                if (addresses.size() > 0) {
                    Log.e("adress", "Address "
                            + "FeatureName: " + addresses.get(0).getFeatureName() + ", \n"
                            + "County Name: " + addresses.get(0).getCountryName() + ", \n"
                            + "Admin Area: " + addresses.get(0).getAdminArea() + ",\n "
                            + "Locality: " + addresses.get(0).getLocality() + ", \n"
                            + "Postal Code: " + addresses.get(0).getPostalCode() + ",\n "
                            + "Max Adrees " + addresses.get(0).getAddressLine(0) + ", \n"
                            + "Max Adrees " + addresses.get(0).getAddressLine(1) + ", \n"
                            + "Max Adrees " + addresses.get(0).getAddressLine(2) + ", \n"
                            + "Max Adrees " + addresses.get(0).getAddressLine(3) + ", \n"
                            + "Max Adrees " + addresses.get(0).getAddressLine(4) + ", \n"
                            + "Max Adrees " + addresses.get(0).getAddressLine(5) + ", \n"
                            + "SubAdmin " + addresses.get(0).getSubAdminArea() + ", \n"
                            + "SubLocality " + addresses.get(0).getSubLocality() + ", \n"
                            + "SubThoroughfare " + addresses.get(0).getSubThoroughfare() + ", \n"
                            + "Thoroughfare " + addresses.get(0).getThoroughfare() + ", \n"
                            + "Locale: " + addresses.get(0).getLocale() + ", "
                            + " size: " + addresses.size());
                    String postal = addresses.get(0).getPostalCode();
                    String code_postal = postal.replaceAll("[^0-9]", "");
                    edtStreet.setText(addresses.get(0).getThoroughfare());
                    if (code_postal.length() >= 5) {
                        edtCp.setText(code_postal);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // getFromLocation() may sometimes fail
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!edtCp.getText().toString().equals("") && edtCp.getText().length() > 0) {
            spnSuburb.setAdapter(modelReportPublicity.getAdapterSuburb(edtCp.getText().toString()));
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        modelReportPublicity.onStopGeo();
    }

    private void saveReport() {
        if (edtStreet.getText().toString().isEmpty()) {
            Toast.makeText(this, "Ingrese la dirección del PDV", Toast.LENGTH_SHORT).show();
        } else if (edtCp.getText().toString().equals("") || spnSuburb.getCount() == 0) {
            Toast.makeText(this, "C.P. no válido", Toast.LENGTH_SHORT).show();
        } else {

            if (spnSuburb.getAdapter().getCount() != 0) {
                dtoReportCensus.setSuburb(modelReportPublicity.getItemSuburb(spnSuburb.getSelectedItemPosition()).getSuburb());
                dtoReportCensus.setState(modelReportPublicity.getItemSuburb(spnSuburb.getSelectedItemPosition()).getState());
                dtoReportCensus.setTown(modelReportPublicity.getItemSuburb(spnSuburb.getSelectedItemPosition()).getTown());
            }

            dtoReportCensus.setAddress(edtStreet.getText().toString());
            dtoReportCensus.setAddress_left(edtLeftStreet.getText().toString());
            dtoReportCensus.setAddress_right(edtRightStreet.getText().toString());
            dtoReportCensus.setCp(edtCp.getText().toString());
            dtoReportCensus.setLat(lat);
            dtoReportCensus.setLon(lon);

            dtoReportCensus.setHash(MD5.md5(System.currentTimeMillis() + "" + new Random().nextInt(1000) + ""));
            dtoReportCensus.setDate(System.currentTimeMillis());

            DtoBundle dtoBundle = new DtoBundle();

            /* Si es mayor a 0 significa que se guardo exitosamente el nuevo pdv, en caso contrario que
             * se muestre un Toast donde le diga que hubo un error en la inserción de los datos */

            startActivity(new Intent(this, MenuReport.class).putExtra(getResources().getString(R.string.app_bundle_name), dtoBundle));
        }
        modelReportPublicity.onStopGeo();
        finish();
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }
}

