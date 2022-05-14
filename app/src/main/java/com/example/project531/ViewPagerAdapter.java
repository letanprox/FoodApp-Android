package com.example.project531;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.project531.Domain.FoodItem;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<FoodItem> foodlist;


    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior, ArrayList<FoodItem> foodlist) {
        super(fm, behavior);
        this.foodlist = foodlist;
    }

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:{
                return new ListFoodFragment(foodlist);
            }
            case 1:{
                return new InfoFoodFragment();
            }
            default:
                return new ListFoodFragment(foodlist);

        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        String title = "";

        switch (position){
            case 0:
                title = "Sản phẩm";
                break;
            case 1:
                title = "Thông tin";
                break;
        }

        return title;
    }
}
