package com.example.project531;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ChangePasswordDialog;
import com.example.ChangePositionDialog;
import com.example.project531.Activity.MainActivity;
import com.example.project531.Interface.ILocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity implements OnMapReadyCallback{

    ChangePasswordDialog changePasswordDialog;
    ChangePositionDialog changePositionDialog;
    TextView changepass_btn, changeposition_btn,txt_address, getposition_btn;
    LinearLayout setting_btn;
    AppCompatImageView logout_btn;

    GoogleMap map;
    double x;
    double y;

    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        float[] results = new float[1];
        Location.distanceBetween(100, 50,
                100, 51,
                results);
        float distanceInMeters = results[0];
        int speedIs10MetersPerMinute = 300;
        float estimatedDriveTimeInMinutes = distanceInMeters / speedIs10MetersPerMinute;

        Log.e("xxx",  String.valueOf(estimatedDriveTimeInMinutes));




        SupportMapFragment supportMapFragment1 = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_user_local);
        supportMapFragment1.getMapAsync((OnMapReadyCallback) this);

        txt_address = findViewById(R.id.txt_address);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_user_local);
        client = LocationServices.getFusedLocationProviderClient(this);

        changepass_btn = findViewById(R.id.changepass_btn);
        changepass_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePasswordDialog = new ChangePasswordDialog();
                changePasswordDialog.show(getSupportFragmentManager(), "DIALOGDETAIL");
            }
        });


        changeposition_btn = findViewById(R.id.changeposition_btn);
        changeposition_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePositionDialog = new ChangePositionDialog(new ILocation() {
                    @Override
                    public void Done(String address_name) {
                        OpenMap(address_name);
                    }
                });
                changePositionDialog.show(getSupportFragmentManager(), "DIALOGDETAIL");
            }
        });





        if(MainActivity.CURRENT_LOCATION.isEmpty()){
            setCurrentLocation();
        }else {

        }


        getposition_btn = findViewById(R.id.getposition_btn);
        getposition_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCurrentLocation();
            }
        });


        Intent A = new Intent(this, EditAccountActivity.class);
        setting_btn = findViewById(R.id.setting_btn);
        setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(A);
            }
        });

        Intent i = new Intent(this, MainActivity.class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });

        Intent iA = new Intent(this, LoginActivity.class);
        logout_btn = findViewById(R.id.logout_btn);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "logout", Toast.LENGTH_SHORT).show();
                startActivity(iA);
            }
        });

    }

    private void setCurrentLocation(){
        if (ActivityCompat.checkSelfPermission(ProfileActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        }else{
            ActivityCompat.requestPermissions(ProfileActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    private void getCurrentLocation() {

        Task<Location> task = client.getLastLocation();
        MainActivity.CURRENT_LOCATION = "";
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                MainActivity.X_LA = location.getLatitude();
                                MainActivity.Y_LO = location.getLongitude();
                            try {
                                Geocoder geo = new Geocoder(getApplicationContext(), Locale.getDefault());
                                List<Address> addresses = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                if (addresses.isEmpty()) {
                                }
                                else {
                                    txt_address.setText(addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
                                    Log.e("xxxx",addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());

                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.e("",String.valueOf(e));
                            }

                            MarkerOptions options = new MarkerOptions().position(latLng).title("I am there!");
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                            googleMap.addMarker(options);
                        }
                    });
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 44){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.map = googleMap;

        if(MainActivity.CURRENT_LOCATION.isEmpty()){

        }else {
            OpenMap(MainActivity.CURRENT_LOCATION);
        }
//        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//        List addressList = null;
//        try {
//            addressList = geocoder.getFromLocationName("97 Man Thiện, Hiệp Phú, Hồ Chí Minh",1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (addressList != null && addressList.size() > 0){
//            Address address = (Address) addressList.get(0);
//            StringBuilder stringBuilder = new StringBuilder();
//            x = (address.getLatitude());
//            y = (address.getLongitude());
//        }
//
//        Log.e("", x + " " + y);
//
//        x = Double.parseDouble(String.valueOf(x));
//        y = Double.parseDouble(String.valueOf(y));
//
//        map = googleMap;
//        LatLng Hocvien = new LatLng(x,y);
//        map.addMarker(new MarkerOptions().position(Hocvien).title("Bun Thai"));
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(Hocvien,11));
    }


    void OpenMap(String address_name){
        MainActivity.CURRENT_LOCATION = address_name;
        txt_address.setText(address_name);

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        List addressList = null;
        try {
            addressList = geocoder.getFromLocationName(address_name,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addressList != null && addressList.size() > 0){
            Address address = (Address) addressList.get(0);
            StringBuilder stringBuilder = new StringBuilder();
            x = (address.getLatitude());
            y = (address.getLongitude());

            MainActivity.X_LA = x;
            MainActivity.Y_LO = y;
        }

        Log.e("", x + " " + y);

        x = Double.parseDouble(String.valueOf(x));
        y = Double.parseDouble(String.valueOf(y));

        LatLng Hocvien = new LatLng(x,y);
        map.addMarker(new MarkerOptions().position(Hocvien).title("Bun Thai"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(Hocvien,11));

        try {
            changePositionDialog.dismiss();
        }catch (Exception e){

        }
    }
}