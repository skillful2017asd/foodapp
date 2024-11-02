package com.example.foodapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;

import com.example.foodapp.R;
import com.example.foodapp.adapter.ViewPagerLoginAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class LoginActivity extends AppCompatActivity {

    ViewPager2 viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewPager = findViewById(R.id.viewpager_login);
        tabLayout = findViewById(R.id.tab_login);

        ViewPagerLoginAdapter viewPagerLoginAdapter = new ViewPagerLoginAdapter(LoginActivity.this);

        viewPager.setAdapter(viewPagerLoginAdapter);

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Login");
                        break;
                    case 1:
                        tab.setText("Sign In");
                        break;
                    default:
                        tab.setText("Login");
                        break;
                }
            }
        }).attach();
    }
}