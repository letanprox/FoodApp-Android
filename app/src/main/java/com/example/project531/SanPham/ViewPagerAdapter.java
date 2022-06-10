package com.example.project531.SanPham;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project531.Domain.FoodDomain;
import com.example.project531.Domain.FoodItem;
import com.example.project531.Interface.ISetTimeOrder;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<FoodItem> foodlist;
    FoodDomain foodDomain;
    ISetTimeOrder iSetTimeOrder;

    private RecyclerView.Adapter adapterZero;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior, ArrayList<FoodItem> foodlist, FoodDomain foodDomain, ISetTimeOrder iSetTimeOrder, RecyclerView.Adapter adapterZero) {
        super(fm, behavior);
        this.foodlist = foodlist;
        this.foodDomain = foodDomain;

        this.iSetTimeOrder = iSetTimeOrder;
        this.adapterZero = adapterZero;
    }

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:{
                return new ListFoodFragment(foodlist, adapterZero);
            }
            case 1:{
                return new InfoFoodFragment(foodDomain, iSetTimeOrder);
            }
            default:
                return new ListFoodFragment(foodlist, adapterZero);
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
