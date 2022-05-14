package com.example.project531.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.project531.Adapter.FoodListAdapter;
import com.example.project531.Adapter.RecommendedAdapter;
import com.example.project531.Domain.FoodDomain;
import com.example.project531.Domain.FoodItem;
import com.example.project531.Helper.ManagementCart;
import com.example.project531.LoginActivity;
import com.example.project531.R;
import com.example.project531.ViewPagerAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ShowDetailActivity extends AppCompatActivity {
    private TextView addToCartBtn;
    private TextView titleTxt, feeTxt, descriptionTxt, numberOrderTxt, totalPriceTxt, starTxt, caloryTxt, timeTxt;
    private ImageView plusBtn, minusBtn, picFood, item_book_mark;
    private FoodDomain object;
    private int numberOrder = 1;
    private ManagementCart managementCart;




    private ViewPager mviewPager;
    private TabLayout mTabLayout;


    private BottomSheetBehavior bottomSheetBehavior, bottomSheetBehaviorCart;
    private RelativeLayout layout_label_cart;
    private RelativeLayout layout_bottom_cart, label_cart_bottom;
    TextView close_cart_bottom, verify_order;



    private RecyclerView rcv_food_list;
    private RecyclerView.Adapter adapter;



    int book_state = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        managementCart = new ManagementCart(this);

        iniView();
        //getBundle();


        mviewPager = findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.tab_layout);

        ArrayList<FoodItem> foodlist = new ArrayList<>();
        foodlist.add(new FoodItem("Pepperoni pizza", "pizza1", "slices pepperoni ,mozzarella cheese, fresh oregano,  ground black pepper, pizza sauce", 13.0, 5, 0));
        foodlist.add(new FoodItem("Chesse Burger", "burger", "beef, Gouda Cheese, Special sauce, Lettuce, tomato ", 15.20, 4, 2));
        foodlist.add(new FoodItem("Vagetable pizza", "pizza3", " olive oil, Vegetable oil, pitted Kalamata, cherry tomatoes, fresh oregano, basil", 11.0, 3, 3));


        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,foodlist);

        mviewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mviewPager);

        ViewGroup.LayoutParams params = mviewPager.getLayoutParams();
        params.height = 410*(foodlist.size()) +300;
        mviewPager.setLayoutParams(params);


        Intent i = new Intent(this, MainActivity.class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });

//        NestedScrollView nestedScrollView = findViewById(R.id.nestscrollview);
//        nestedScrollView.setFillViewport(true);


        rcv_food_list = findViewById(R.id.rcv_list_food);
        recyclerViewListFood();


        openBottomSheetLabelCart();




        item_book_mark = findViewById(R.id.item_book_mark);
        item_book_mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(book_state == 1){
                    book_state = 0;
                    item_book_mark.setImageResource(R.drawable.ic_book_mark1);
                }else{
                    book_state = 1;
                    item_book_mark.setImageResource(R.drawable.ic_book_mark);
                }



            }
        });


    }


    private void openBottomSheetLabelCart(){
        layout_label_cart = findViewById(R.id.layout_label_cart);
        bottomSheetBehavior = BottomSheetBehavior.from(layout_label_cart);

        label_cart_bottom = findViewById(R.id.label_cart_bottom);

        layout_bottom_cart = findViewById(R.id.layout_bottom_cart);
        bottomSheetBehaviorCart = BottomSheetBehavior.from(layout_bottom_cart);
        close_cart_bottom = findViewById(R.id.close_cart_bottom);

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
                startActivity(i3);
            }
        });



//        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }



    private void recyclerViewListFood() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rcv_food_list.setLayoutManager(linearLayoutManager);

        ArrayList<FoodItem> foodlist = new ArrayList<>();
        foodlist.add(new FoodItem("Pepperoni pizza", "pizza1", "slices pepperoni ,mozzarella cheese, fresh oregano,  ground black pepper, pizza sauce", 13.0, 5, 0));
        foodlist.add(new FoodItem("Chesse Burger", "burger", "beef, Gouda Cheese, Special sauce, Lettuce, tomato ", 15.20, 4, 2));
        foodlist.add(new FoodItem("Vagetable pizza", "pizza3", " olive oil, Vegetable oil, pitted Kalamata, cherry tomatoes, fresh oregano, basil", 11.0, 3, 3));

        adapter = new FoodListAdapter(foodlist);
        rcv_food_list.setAdapter(adapter);
    }




    private void getBundle() {
        object = (FoodDomain) getIntent().getSerializableExtra("object");

        int drawableResourceId = this.getResources().getIdentifier(object.getPic(), "drawable", this.getPackageName());
        Glide.with(this)
                .load(drawableResourceId)
                .into(picFood);

        titleTxt.setText(object.getTitle());
//        feeTxt.setText("$" + object.getFee());
        descriptionTxt.setText(object.getDescription());
        numberOrderTxt.setText(String.valueOf(numberOrder));
        caloryTxt.setText(object.getCalories() + " Calories");
        starTxt.setText(object.getStar() + "");
        timeTxt.setText(object.getTime() + " minutes");




//        totalPriceTxt.setText("$"+Math.round(numberOrder * object.getFee()));

//        plusBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                numberOrder = numberOrder + 1;
//                numberOrderTxt.setText(String.valueOf(numberOrder));
//                totalPriceTxt.setText("$"+Math.round(numberOrder * object.getFee()));
//            }
//        });

//        minusBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (numberOrder > 1) {
//                    numberOrder = numberOrder - 1;
//                }
//                numberOrderTxt.setText(String.valueOf(numberOrder));
//                totalPriceTxt.setText("$"+Math.round(numberOrder * object.getFee()));
//            }
//        });

//        addToCartBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                object.setNumberInCart(numberOrder);
//                managementCart.insertFood(object);
//            }
//        });
    }

    private void iniView() {
      //  addToCartBtn = findViewById(R.id.addToCartBtn);
        titleTxt = findViewById(R.id.titleTxt);
      //  feeTxt = findViewById(R.id.priceTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        numberOrderTxt = findViewById(R.id.numberItemTxt);
        plusBtn = findViewById(R.id.plusCardBtn);
        minusBtn = findViewById(R.id.minusCardBtn);
        picFood = findViewById(R.id.foodPic);
   //     totalPriceTxt = findViewById(R.id.totalPriceTxt);
        starTxt = findViewById(R.id.starTxt);
      //  caloryTxt = findViewById(R.id.VicaloriesTxt);
        timeTxt = findViewById(R.id.timeTxt);
    }
}