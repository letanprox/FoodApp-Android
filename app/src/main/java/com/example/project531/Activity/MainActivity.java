package com.example.project531.Activity;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project531.ChartActivity;
import com.example.project531.Database;
import com.example.project531.DonHang.HistoryOrdersActivity;
import com.example.project531.Home.HomeFragment;
import com.example.project531.Home.OrdersFragment;
import com.example.project531.LoginActivity;
import com.example.project531.OTPSampleActivity;
import com.example.project531.ProfileActivity;
import com.example.project531.R;
import com.example.project531.SignupActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter, adapter2;
    private RecyclerView recyclerViewCategotyList, recyclerViewPopularList;

    FloatingActionButton cartBtn;
    ImageView avatar_btn;
    LinearLayout btn_home, btn_orders;

    ImageView icon_book_mark, icon_home;
    TextView txt_icon_book_mark, txt_icon_home;

    private static final int PERMISSION_REQUEST_CODE = 200;

    public static Database database;

    public static String connectURL = "http://192.168.1.104:4000";
    public static String DIACHI = "";
    public static int ID_USER = 0;
    public static String ANH = "";
    public static String TEN = "";
    public static String SDT = "";
    public static String EMAIL = "";
    public static String CURRENT_LOCATION = "";
    public static double X_LA;
    public static double Y_LO;
    List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent ixx = new Intent(this, ChartActivity.class);
//        startActivity(ixx);

        STARTDATABASE();

        Cursor cursor = MainActivity.database.GetData("SELECT * FROM Userx");
        while (cursor.moveToNext()){
            ID_USER = cursor.getInt(0);
            TEN = cursor.getString(1);
            ANH = cursor.getString(2);
            SDT = cursor.getString(3);
            EMAIL = cursor.getString(5);
        }

        if(ID_USER == 0){
            Intent ix = new Intent(this, LoginActivity.class);
            startActivity(ix);
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btn_home = findViewById(R.id.homeBtn);
        btn_orders = findViewById(R.id.orders_btn);

        icon_book_mark = findViewById(R.id.icon_book_mark);
        icon_home = findViewById(R.id.icon_home);

        txt_icon_book_mark = findViewById(R.id.txt_icon_book_mark);
        txt_icon_home = findViewById(R.id.txt_icon_home);

        icon_book_mark.setImageResource(R.drawable.ic_baseline_bookmark_27_black);
        icon_home.setImageResource(R.drawable.ic_baseline_home_27_red);
        txt_icon_book_mark.setTextColor(Color.parseColor("#777777"));
        txt_icon_home.setTextColor(Color.parseColor("#ff3232"));

        HomeFragment homeFragment = new HomeFragment();
        OrdersFragment ordersFragment = new OrdersFragment();

        fragmentList.add(homeFragment);
        fragmentList.add(ordersFragment);

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                icon_book_mark.setImageResource(R.drawable.ic_baseline_bookmark_27_black);
                icon_home.setImageResource(R.drawable.ic_baseline_home_27_red);
                txt_icon_book_mark.setTextColor(Color.parseColor("#777777"));
                txt_icon_home.setTextColor(Color.parseColor("#ff3232"));
                displayFragment(homeFragment);
            }
        });

        btn_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                icon_book_mark.setImageResource(R.drawable.ic_baseline_bookmark_27_red);
                icon_home.setImageResource(R.drawable.ic_baseline_home_27_black);
                txt_icon_book_mark.setTextColor(Color.parseColor("#ff3232"));
                txt_icon_home.setTextColor(Color.parseColor("#777777"));
                displayFragment(ordersFragment);
            }
        });
        displayFragment(homeFragment);
        //Intent i = new Intent(this, ForgotPasswordActivity.class); startActivity(i);
        cartBtn = (FloatingActionButton) findViewById(R.id.cartBtn);
        avatar_btn = findViewById(R.id.avatar_btn);
        Intent i = new Intent(this, HistoryOrdersActivity.class);
        Intent i1 = new Intent(this, ProfileActivity.class);
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i);
            }
        });
        if (checkPermission()) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }
    }

    private int getFragmentIndex(Fragment fragment) {
        int index = -1;
        for (int i = 0; i < fragmentList.size(); i++) {
            if (fragment.hashCode() == fragmentList.get(i).hashCode()){
                return i;
            }
        }
        return index;
    }

    private void displayFragment(Fragment fragment) {
        int index = getFragmentIndex(fragment);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fragment.isAdded()) { // if the fragment is already in container
            transaction.show(fragment);
        } else { // fragment needs to be added to frame container
            transaction.add(R.id.home_fragment, fragment);
        }
        // hiding the other fragments
        for (int i = 0; i < fragmentList.size(); i++) {
            if (fragmentList.get(i).isAdded() && i != index) {
                transaction.hide(fragmentList.get(i));
            }
        }
        transaction.commit();
    }


    public void switchFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.home_fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void STARTDATABASE(){
            database = new Database(this,"FOODAPP.sqlite", null, 1);
            database.QueryData("CREATE TABLE IF NOT EXISTS Userx(" +
                    "ID  INT, " +
                    "TEN VARCHAR(200), " +
                    "ANH VARCHAR(700), " +
                    "SDT VARCHAR(100), " +
                    "MATKHAU VARCHAR(100), " +
                    "EMAIL VARCHAR(100) )");
    }

    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denined.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

}