package com.example.foodapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodapp.R;
import com.example.foodapp.activity.CartActivity;
import com.example.foodapp.activity.CategoryActivity;
import com.example.foodapp.adapter.CategoryAdapter;
import com.example.foodapp.adapter.MonAnHotAdapter;
import com.example.foodapp.databinding.ActivityMainBinding;
import com.example.foodapp.databinding.FragmentHomeBinding;
import com.example.foodapp.model.Category;
import com.example.foodapp.retrofit.ResponseCategory;
import com.example.foodapp.retrofit.ResponseMonAnHot;
import com.example.foodapp.viewModel.HomeViewModel;
import com.example.foodapp.viewModel.MonAnHotViewModel;


public class HomeFragment extends Fragment {


    HomeViewModel homeViewModel;
    MonAnHotViewModel monAnHotViewModel;

    FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        initView();
        initData();
        binding.icStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CartActivity.class);
                startActivity(intent);
            }
        });
        return binding.getRoot();
    }

    private void initView() {
        binding.rcv1.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        binding.rcv1.setLayoutManager(layoutManager);

        binding.rcv2.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(getActivity(),2);
        binding.rcv2.setLayoutManager(layoutManager1);

    }

    private void initData() {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.categoryMutableLiveData().observe(getActivity(), new Observer<ResponseCategory>() {
            @Override
            public void onChanged(ResponseCategory reponseCategory) {
                if (reponseCategory.isSuccess()) {
                    CategoryAdapter adapter1 = new CategoryAdapter(reponseCategory.getResult(), category -> {
                        clickCatrgory(category);
                    });
                    binding.rcv1.setAdapter(adapter1);
                }
            }
        });
        monAnHotViewModel = new ViewModelProvider(this).get(MonAnHotViewModel.class);
        monAnHotViewModel.mutableLiveData().observe(getActivity(), new Observer<ResponseMonAnHot>() {
            @Override
            public void onChanged(ResponseMonAnHot responseMonAnHot) {
                if(responseMonAnHot.isSuccess()){
                    MonAnHotAdapter monAnHotAdapter = new MonAnHotAdapter(responseMonAnHot.getResult());
                    binding.rcv2.setAdapter(monAnHotAdapter);
                }
            }
        });
    }

    private void clickCatrgory(Category category) {
        Intent intent = new Intent(getActivity(), CategoryActivity.class);
        intent.putExtra("idcate",category.getId());
        intent.putExtra("namecate",category.getCategory());
        startActivity(intent);
    }
}