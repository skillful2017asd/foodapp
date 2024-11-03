package com.example.foodapp.activity;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.foodapp.R;
import com.example.foodapp.adapter.MealsAdapter;
import com.example.foodapp.databinding.ActivityCategoryBinding;
import com.example.foodapp.model.Meals;
import com.example.foodapp.viewModel.CategoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    ActivityCategoryBinding binding;
    CategoryViewModel viewModel;
    List<Meals> meals;
    MealsAdapter adapter;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if(o.getResultCode()==RESULT_OK){
                setData();
            }
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_category);

        setUI();
        setData();
        //sreachData();
    }

    private void sreachData() {
        binding.svRcv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterDetail(newText);
                return true;
            }
        });
    }

    private void setData() {
        viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        Intent intent = getIntent();
        int idcate = intent.getIntExtra("idcate",0);
        String name = intent.getStringExtra("namecate");

        binding.tvCategoryTitle.setText(name);

        viewModel.responseMealsMutableLiveData(idcate).observe(this,responseMeals -> {
            Log.d("thread",Thread.currentThread().getName());
            if(responseMeals.isSuccess()){
                meals = responseMeals.getResult();
                adapter = new MealsAdapter(meals,meals -> {
                        onClickMeal(meals);
                });
                binding.rcvCategory.setAdapter(adapter);
            }
        });
        //sreachData();

    }

    private void filterDetail(String newText) {

        List<Meals> list = new ArrayList<>();
        for (Meals m : meals) {
            if (m.getStrMeal().toLowerCase().contains(newText.toLowerCase())) {
                list.add(m);
            }
        }

        if(list.isEmpty()) {
                Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show();
        }else{
            adapter.UpdateList(list);
        }
    }

    private void onClickMeal(Meals meals) {
        Intent intent = new Intent(this,ShowDetailActivity.class);
        intent.putExtra("idmeals",meals.getIdMeal());
        intent.putExtra("namemeals",meals.getStrMeal());
        activityResultLauncher.launch(intent);

    }

    private void setUI() {
        binding.rcvCategory.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,3);
        binding.rcvCategory.setLayoutManager(layoutManager);

    }


}