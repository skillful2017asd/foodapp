package com.example.foodapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.foodapp.R;
import com.example.foodapp.adapter.CategoryAdapter;
import com.example.foodapp.adapter.MealsAdapter;
import com.example.foodapp.adapter.ViewPagerHomeAdapter;
import com.example.foodapp.databinding.ActivityMainBinding;
import com.example.foodapp.fragment.SettingFragment;
import com.example.foodapp.model.Category;
import com.example.foodapp.retrofit.ResponseCategory;
import com.example.foodapp.retrofit.ResponseMeals;
import com.example.foodapp.viewModel.HomeViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.bottomnav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.item_home){
                    binding.viewpagerHome.setCurrentItem(0);
                    return true;
                }else if(item.getItemId() == R.id.item_store){
                    binding.viewpagerHome.setCurrentItem(1);
                    return true;
                }else if(item.getItemId() == R.id.item_setting){
                    binding.viewpagerHome.setCurrentItem(2);
                    return true;
                }else{
                    return false;
                }
            }
        });
        ViewPagerHomeAdapter adapter = new ViewPagerHomeAdapter(MainActivity.this);
        binding.viewpagerHome.setAdapter(adapter);
        binding.viewpagerHome.setUserInputEnabled(false);
    }



}