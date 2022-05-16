package com.example.project531.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project531.DonHang.HistoryOrdersActivity;
import com.example.project531.Home.HomeFragment;
import com.example.project531.Home.OrdersFragment;
import com.example.project531.ProfileActivity;
import com.example.project531.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter, adapter2;
    private RecyclerView recyclerViewCategotyList, recyclerViewPopularList;

    FloatingActionButton cartBtn;
    ImageView avatar_btn;
    LinearLayout btn_home, btn_orders;

    ImageView icon_book_mark, icon_home;
    TextView txt_icon_book_mark, txt_icon_home;

    public static String connectURL = "http://192.168.1.105:4000";
    public static int ID_USER = 1;
    public static String CURRENT_LOCATION = "";
    public static double X_LA;
    public static double Y_LO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                icon_book_mark.setImageResource(R.drawable.ic_baseline_bookmark_27_black);
                icon_home.setImageResource(R.drawable.ic_baseline_home_27_red);
                txt_icon_book_mark.setTextColor(Color.parseColor("#777777"));
                txt_icon_home.setTextColor(Color.parseColor("#ff3232"));
                switchFragment(new HomeFragment());
            }
        });

        btn_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                icon_book_mark.setImageResource(R.drawable.ic_baseline_bookmark_27_red);
                icon_home.setImageResource(R.drawable.ic_baseline_home_27_black);
                txt_icon_book_mark.setTextColor(Color.parseColor("#ff3232"));
                txt_icon_home.setTextColor(Color.parseColor("#777777"));
                switchFragment(new OrdersFragment());
            }
        });


       getSupportFragmentManager().beginTransaction().add(R.id.home_fragment, new HomeFragment()).commit();

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












//        avatar_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(i1);
//            }
//        });

//        recyclerViewCategoty();
//        recyclerViewPopular();
//        bottomNavigation();
    }






    public void switchFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.home_fragment, fragment)
                .addToBackStack(null)
                .commit();
    }













}