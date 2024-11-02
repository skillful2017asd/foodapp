package com.example.foodapp.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodapp.repository.MealDetailRepository;
import com.example.foodapp.retrofit.ResponseMealDetail;

public class ShowDetailViewModel extends ViewModel {

    MealDetailRepository repository;

    public ShowDetailViewModel() {
        this.repository = new MealDetailRepository();
    }
    public MutableLiveData<ResponseMealDetail> detailMutableLiveData(int id){
        return repository.detailMutableLiveData(id);
    }
}
