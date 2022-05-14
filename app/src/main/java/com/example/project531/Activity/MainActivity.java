package com.example.project531.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.fragment.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project531.Adapter.CategoryAdapter;
import com.example.project531.Adapter.RecommendedAdapter;
import com.example.project531.Domain.CategoryDomain;
import com.example.project531.Domain.FoodDomain;
import com.example.project531.ForgotPasswordActivity;
import com.example.project531.HistoryOrdersActivity;
import com.example.project531.HomeFragment;
import com.example.project531.LoginActivity;
import com.example.project531.OrdersFragment;
import com.example.project531.ProfileActivity;
import com.example.project531.R;
import com.example.project531.SignupActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter, adapter2;
    private RecyclerView recyclerViewCategotyList, recyclerViewPopularList;

    FloatingActionButton cartBtn;
    ImageView avatar_btn;

    LinearLayout btn_home, btn_orders;


    ImageView icon_book_mark, icon_home;

    TextView txt_icon_book_mark, txt_icon_home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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