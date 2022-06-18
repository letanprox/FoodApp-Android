package com.example.project531.SanPham;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.project531.Activity.MainActivity;
import com.example.project531.Adapter.FoodListAdapter;
import com.example.project531.Domain.FoodDomain;
import com.example.project531.Domain.FoodItem;
import com.example.project531.Interface.IEventAddCart;
import com.example.project531.Interface.IEventMinusCart;
import com.example.project531.Interface.ISetTimeOrder;
import com.example.project531.Interface.ImplementJson;
import com.example.project531.API.ParseURL;
import com.example.project531.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class ShowDetailActivity extends AppCompatActivity   {

    private TextView titleTxt, descriptionTxt, starTxt, timeTxt, label_among_price, number_item_in_cart;
    private TextView close_cart_bottom, verify_order;

    private ImageView  picFood, item_book_mark;

    private RelativeLayout layout_label_cart;
    private RelativeLayout layout_bottom_cart, label_cart_bottom;
    private BottomSheetBehavior bottomSheetBehavior, bottomSheetBehaviorCart;

    private RecyclerView rcv_food_list;
    private RecyclerView.Adapter adapterZero;
    private RecyclerView.Adapter adapterOne;

    private ViewPager mviewPager;
    private TabLayout mTabLayout;

    private FoodDomain object;
    private FoodDomain foodDomain;

    int book_state = 0;
    int is_have = 0;
    double price_total = 0;
    int total_item_incart = 0;
    int rating = 1;

    private RequestQueue mQueue;
    private ParseURL parseURL;

    ArrayList<FoodItem> foodlistcart;
    ArrayList<FoodItem> foodlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        Intent i = new Intent(this, MainActivity.class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });
        mQueue = Volley.newRequestQueue(this);
        parseURL = new ParseURL(mQueue);
        object = (FoodDomain) getIntent().getSerializableExtra("object");
        foodDomain = object;
        iniView();
        getBundle();
        foodlist = new ArrayList<>();


        //API GET DS SAN PHAM THEO CUA HANG:
        parseURL.ParseData(MainActivity.connectURL+"/api/cuahang/sanpham/list?id="+foodDomain.getID(), new ImplementJson() {
            @Override
            public void Done(JSONArray jsonArray) {
                try{
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject data = null;
                        data = jsonArray.getJSONObject(i);

                        int ID = data.getInt("ID");
                        String TEN = data.getString("TEN");
                        String ANH = data.getString("ANH");
                        int SOLUONGBAN = data.getInt("SOLUONGBAN");
                        double GIA = data.getDouble("GIA");
                        ANH = ANH.replace("localhost",MainActivity.IP);

                        foodlist.add(new FoodItem(ID,TEN, ANH, "", GIA, SOLUONGBAN, 0));
                        adapterZero = new FoodListAdapter(foodlist,
                            new IEventAddCart() {
                                @Override
                                public void Done(FoodItem foodItem) {
                                    if(total_item_incart == 0) {bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);}
                                    total_item_incart = total_item_incart + 1;
                                    price_total = price_total + foodItem.getFee();
                                    if (foodlistcart.contains(foodItem)) {
                                        adapterOne.notifyDataSetChanged();
                                    } else {
                                        foodlistcart.add(foodItem);
                                        adapterOne.notifyDataSetChanged();
                                    }
                                    label_among_price.setText(String.valueOf(price_total) + "00");
                                    number_item_in_cart.setText(String.valueOf(total_item_incart));
                                }
                            }, new IEventMinusCart() {
                                @Override
                                public void Done(FoodItem foodItem) {
                                    total_item_incart = total_item_incart - 1;
                                    if(total_item_incart == 0) {bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);}

                                    price_total = price_total - foodItem.getFee();
                                    if (foodItem.getNumberInCart() == 1) {
                                        foodlistcart.remove(foodItem);
                                        adapterOne.notifyDataSetChanged();
                                    } else {
                                        adapterOne.notifyDataSetChanged();
                                    }
                                    label_among_price.setText(String.valueOf(price_total) + "00");
                                    number_item_in_cart.setText(String.valueOf(total_item_incart));
                                }
                            });
                    }

                    ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, foodlist, foodDomain,
                            new ISetTimeOrder() {
                        @Override
                        public void Done(String time) {
                            Log.e("time", time);
                            timeTxt.setText(String.valueOf( Math.round(Double.valueOf(time))  ) + "phút");
                        }
                    }, adapterZero);


                    mviewPager.setAdapter(viewPagerAdapter);
                    mTabLayout.setupWithViewPager(mviewPager);
                    ViewGroup.LayoutParams params = mviewPager.getLayoutParams();

                    if((410*(foodlist.size()) +300) > 1500){
                        params.height = 410*(foodlist.size()) +300;
                    }else{
                        params.height = 1400;
                    }
                    mviewPager.setLayoutParams(params);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        recyclerViewListFood();
        openBottomSheetLabelCart();


        //API NUT LUU CUA HANG:
        item_book_mark = findViewById(R.id.item_book_mark);
        parseURL.ParseData(MainActivity.connectURL+"/api/user/cuahang/checksave?iduser="+MainActivity.ID_USER+"&idch="+foodDomain.getID(), new ImplementJson() {
            @Override
            public void Done(JSONArray jsonArray) {

                try{
                    for (int i = 0; i < jsonArray.length(); i++) {
                        is_have = 1;
                        JSONObject data = null;
                        data = jsonArray.getJSONObject(i);

                        int ID = data.getInt("ID");
                        double DANHGIA = data.getDouble("DANHGIA");
                        int MARK = data.getInt("MARK");

                        if(MARK == 1) {
                            item_book_mark.setImageResource(R.drawable.ic_book_mark);
                            book_state = 1;
                        }
                        else{
                            item_book_mark.setImageResource(R.drawable.ic_book_mark1);
                            book_state = 0;
                        }
                    }
                    if(jsonArray.length() == 0){
                        Log.e("XXX",String.valueOf("khong co luu"));
                        item_book_mark.setImageResource(R.drawable.ic_book_mark1);
                        book_state = 0;
                    }
                } catch (JSONException e) {
                    Log.e("XXX",String.valueOf("xxxx"));
                    e.printStackTrace();
                }
            }
        });
        item_book_mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(book_state == 1){
                    book_state = 0;
                    item_book_mark.setImageResource(R.drawable.ic_book_mark1);
                    parseURL.ParseData(MainActivity.connectURL+"/api/user/cuahang/updatesave?iduser="+MainActivity.ID_USER+"&idch="+foodDomain.getID()+"&mark="+0+"&danhgia="+rating, new ImplementJson() {
                        @Override
                        public void Done(JSONArray jsonArray) {
                        }
                    });
                }else{
                    book_state = 1;
                    item_book_mark.setImageResource(R.drawable.ic_book_mark);
                    if(is_have == 1){
                        parseURL.ParseData(MainActivity.connectURL+"/api/user/cuahang/updatesave?iduser="+MainActivity.ID_USER+"&idch="+foodDomain.getID()+"&mark="+1+"&danhgia="+rating, new ImplementJson() {
                            @Override
                            public void Done(JSONArray jsonArray) {
                            }
                        });
                    }else {
                        parseURL.ParseData(MainActivity.connectURL+"/api/user/cuahang/insertsave?iduser="+MainActivity.ID_USER+"&idch="+foodDomain.getID()+"&mark="+1+"&danhgia="+rating, new ImplementJson() {
                            @Override
                            public void Done(JSONArray jsonArray) {
                            }
                        });
                    }
                }
            }
        });
    }


    private void openBottomSheetLabelCart(){
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehaviorCart.setState(BottomSheetBehavior.STATE_HIDDEN);

        label_cart_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehaviorCart.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        close_cart_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehaviorCart.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        verify_order = findViewById(R.id.verify_order);
        Intent i3 = new Intent(this, CartActivity.class);
        verify_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i3.putExtra("pricetotal", price_total);
                i3.putExtra("listfood",  (Serializable) foodlistcart);
                startActivity(i3);
            }
        });
    }


    private void recyclerViewListFood() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcv_food_list.setLayoutManager(linearLayoutManager);
        foodlistcart = new ArrayList<>();
        adapterOne = new FoodListAdapter(foodlistcart,new IEventAddCart() {
            @Override
            public void Done(FoodItem foodItem) {
                if(total_item_incart == 0) {bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);}
                total_item_incart = total_item_incart + 1;
                price_total = price_total + foodItem.getFee();
                if (foodlist.contains(foodItem)) {
                    adapterZero.notifyDataSetChanged();
                } else {
                    adapterZero.notifyDataSetChanged();
                }
                label_among_price.setText(String.valueOf(price_total) + "00");
                number_item_in_cart.setText(String.valueOf(total_item_incart));
            }
        }, new IEventMinusCart() {
            @Override
            public void Done(FoodItem foodItem) {
                total_item_incart = total_item_incart - 1;
                if(total_item_incart == 0) {bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);}

                price_total = price_total - foodItem.getFee();
                if (foodItem.getNumberInCart() == 1) {
                    adapterZero.notifyDataSetChanged();
                } else {
                    adapterZero.notifyDataSetChanged();
                }
                label_among_price.setText(String.valueOf(price_total) + "00");
                number_item_in_cart.setText(String.valueOf(total_item_incart));
            }
        });
        rcv_food_list.setAdapter(adapterOne);
    }


    private void getBundle() {
        Picasso.get()
                .load(object.getPic())
                .fit()
                .centerCrop()
                .into(picFood);

        titleTxt.setText(object.getTitle());
        descriptionTxt.setText(object.getDescription());
        starTxt.setText(object.getStar() + "");
    }

    private void iniView() {
        titleTxt = findViewById(R.id.titleTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        picFood = findViewById(R.id.foodPic);
        starTxt = findViewById(R.id.starTxt);
        timeTxt = findViewById(R.id.timeTxt);

        label_among_price = findViewById(R.id.label_among_price);
        number_item_in_cart = findViewById(R.id.number_item_in_cart);
        number_item_in_cart.setText(String.valueOf(total_item_incart));
        mviewPager = findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.tab_layout);
        rcv_food_list = findViewById(R.id.rcv_list_food);
        layout_label_cart = findViewById(R.id.layout_label_cart);
        bottomSheetBehavior = BottomSheetBehavior.from(layout_label_cart);

        label_cart_bottom = findViewById(R.id.label_cart_bottom);
        layout_bottom_cart = findViewById(R.id.layout_bottom_cart);
        bottomSheetBehaviorCart = BottomSheetBehavior.from(layout_bottom_cart);
        close_cart_bottom = findViewById(R.id.close_cart_bottom);
    }
}