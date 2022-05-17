package com.example.project531.SanPham;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.project531.Activity.MainActivity;
import com.example.project531.Adapter.FoodListAdapterOrdered;
import com.example.project531.Domain.FoodItem;
import com.example.project531.EditAccountActivity;
import com.example.project531.Helper.ManagementCart;
import com.example.project531.Interface.ImplementJson;
import com.example.project531.API.ParseURL;
import com.example.project531.ProfileActivity;
import com.example.project531.R;

import org.json.JSONArray;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManagementCart managementCart;
    private TextView totalFeeTxt, taxTxt, deliveryTxt, totalTxt, emptyTxt, payment_btn;
    private double tax;
    private ScrollView scrollView;


    ArrayList<FoodItem> foodlist;
    double pricetotal;

    private RecyclerView rcv_list_order;

    TextView diachi_cart, sdt_cart, thoigiangiaohang_cart;


    private RequestQueue mQueue;
    ParseURL parseURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        managementCart = new ManagementCart(this);

        initView();
        initList();
        bottomNavigation();
        calculateCard();


        mQueue = Volley.newRequestQueue(this);
        parseURL = new ParseURL(mQueue);


        Intent i = new Intent(this, ShowDetailActivity.class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });

        thoigiangiaohang_cart = findViewById(R.id.thoigiangiaohang_cart);
        diachi_cart = findViewById(R.id.diachi_cart);
        sdt_cart = findViewById(R.id.sdt_cart);

        diachi_cart.setText("Địa chỉ giao hàng: "+MainActivity.DIACHI);
        sdt_cart.setText("SDT: "+MainActivity.SDT);
        thoigiangiaohang_cart.setText("Thời gian giao hàng: 2p");

      p rcv_list_order = findViewById(R.id.rcv_list_order);
        recyclerViewListFood();

        payment_btn = findViewById(R.id.payment_btn);
        payment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sp = "";
                for (int i = 0; i < foodlist.size(); i++){
                    if(i == foodlist.size() - 1)
                        sp = sp + foodlist.get(i).getId()+"i"+foodlist.get(i).getNumberInCart();
                    else
                        sp = sp + foodlist.get(i).getId()+"i"+foodlist.get(i).getNumberInCart()+"-";
                }
                Log.e("sp", sp);

                parseURL.ParseData(MainActivity.connectURL+"/donhang/insert?gia="+pricetotal+"&sp="+sp, new ImplementJson() {
                    @Override
                    public void Done(JSONArray jsonArray) {


//                        try{
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                    }
                });

                Intent ok = new Intent(CartActivity.this, MainActivity.class);
                startActivity(ok);




            }
        });

    }

    private void recyclerViewListFood() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rcv_list_order.setLayoutManager(linearLayoutManager);

        pricetotal = (double) getIntent().getSerializableExtra("pricetotal");
        deliveryTxt = findViewById(R.id.deliveryTxt);
        totalFeeTxt = findViewById(R.id.totalFeeTxt);
        totalTxt = findViewById(R.id.totalTxt);
        deliveryTxt.setText(String.valueOf(10.000));
        totalFeeTxt.setText(String.valueOf(pricetotal));
        totalTxt.setText(String.valueOf(pricetotal+10.000));

        foodlist  = (ArrayList<FoodItem>) getIntent().getSerializableExtra("listfood");

        adapter = new FoodListAdapterOrdered(foodlist);
        rcv_list_order.setAdapter(adapter);
    }

    private void bottomNavigation() {
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout cartBtn = findViewById(R.id.cartBtn);

//        homeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(CartActivity.this, MainActivity.class));
//            }
//        });
//
//        cartBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(CartActivity.this, CartActivity.class));
//            }
//        });
    }

    private void initList() {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        recyclerViewList.setLayoutManager(linearLayoutManager);
//        adapter = new CartListAdapter(managementCart.getListCart(), this, new ChangeNumberItemsListener() {
//            @Override
//            public void changed() {
//                calculateCard();
//            }
//        });
//
//        recyclerViewList.setAdapter(adapter);
//        if (managementCart.getListCart().isEmpty()) {
//            emptyTxt.setVisibility(View.VISIBLE);
//            scrollView.setVisibility(View.GONE);
//        } else {
//            emptyTxt.setVisibility(View.GONE);
//            scrollView.setVisibility(View.VISIBLE);
//        }
    }

    private void calculateCard() {
//        double percentTax = 0.02;  //you can change this item for tax price
//        double delivery = 10;     //you can change this item you need price for delivery
//
//        tax = Math.round((managementCart.getTotalFee() * percentTax) * 100.0) / 100.0;
//        double total = Math.round((managementCart.getTotalFee() + tax + delivery) * 100.0) / 100.0;
//        double itemTotal = Math.round(managementCart.getTotalFee() * 100.0) / 100.0;
//
//        totalFeeTxt.setText("$" + itemTotal);
//        taxTxt.setText("$" + tax);
//        deliveryTxt.setText("$" + delivery);
//        totalTxt.setText("$" + total);
    }

    private void initView() {
//        totalFeeTxt = findViewById(R.id.totalFeeTxt);
//        taxTxt = findViewById(R.id.taxTxt);
//        deliveryTxt = findViewById(R.id.deliveryTxt);
//        totalTxt = findViewById(R.id.totalTxt);
//        recyclerViewList = findViewById(R.id.view);
//        scrollView = findViewById(R.id.scrollView);
//        emptyTxt = findViewById(R.id.emptyTxt);
    }



}
