package com.example.project531.SanPham;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project531.Activity.MainActivity;
import com.example.project531.Domain.FoodDomain;
import com.example.project531.Interface.ISetTimeOrder;
import com.example.project531.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class InfoFoodFragment extends Fragment implements OnMapReadyCallback {

    FoodDomain foodDomain;
    RatingBar ratingBar;
    GoogleMap map;
    TextView position_label, priceavg_label, timeopen_label;
    double x;
    double y;
    ISetTimeOrder iSetTimeOrder;
    public InfoFoodFragment(FoodDomain foodDomain, ISetTimeOrder iSetTimeOrder) {
        this.foodDomain = foodDomain;
        this.iSetTimeOrder = iSetTimeOrder;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_food_fragment, container, false);

        ratingBar = view.findViewById(R.id.rating_star);
        Log.e("",String.valueOf(foodDomain.getPosition()));
        ratingBar.setRating(Float.parseFloat(String.valueOf(foodDomain.getStar())));

        position_label = view.findViewById(R.id.position_label);
        priceavg_label = view.findViewById(R.id.priceavg_label);
        timeopen_label = view.findViewById(R.id.timeopen_label);

        position_label.setText("Vị trí: "+foodDomain.getPosition());
        priceavg_label.setText("Giá trung bình: "+foodDomain.getFee()+"k");
        timeopen_label.setText("Giờ mở cửa: "+foodDomain.getTimeopen());

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
//                Toast.makeText(getContext(), String.valueOf(ratingBar.getRating()),Toast.LENGTH_SHORT).show();
            }
        });

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapstore);
        supportMapFragment.getMapAsync(this);
        return  view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        List addressList = null;
        try {
            addressList = geocoder.getFromLocationName(foodDomain.getPosition(),1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addressList != null && addressList.size() > 0){
            Address address = (Address) addressList.get(0);
            StringBuilder stringBuilder = new StringBuilder();
            x = (address.getLatitude());
            y = (address.getLongitude());

            float[] results = new float[1];
            Location.distanceBetween(x, y,
                    MainActivity.X_LA, MainActivity.Y_LO,
                    results);

            float distanceInMeters = results[0];

            int speedIs10MetersPerMinute = 300;
            float estimatedDriveTimeInMinutes = distanceInMeters / speedIs10MetersPerMinute;

            Log.e("xxx",  String.valueOf(estimatedDriveTimeInMinutes));
            iSetTimeOrder.Done(String.valueOf(estimatedDriveTimeInMinutes));
        }


        x = Double.parseDouble(String.valueOf(x));
        y = Double.parseDouble(String.valueOf(y));

        map = googleMap;
        LatLng Hocvien = new LatLng(x,y);
        map.addMarker(new MarkerOptions().position(Hocvien).title("Bun Thai"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(Hocvien,11));
    }
}
