package com.example.foodapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.foodapp.fragment.CartFragment;
import com.example.foodapp.fragment.HomeFragment;
import com.example.foodapp.fragment.SettingFragment;

public class ViewPagerHomeAdapter extends FragmentStateAdapter {
    public ViewPagerHomeAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new HomeFragment();
            case 1: return new CartFragment();
            case 2: return new SettingFragment();
            default: return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
