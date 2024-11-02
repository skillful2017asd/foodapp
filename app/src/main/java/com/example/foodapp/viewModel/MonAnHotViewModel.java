package com.example.foodapp.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodapp.repository.MonAnHotRepository;
import com.example.foodapp.retrofit.ResponseMonAnHot;

public class MonAnHotViewModel extends ViewModel{

    MonAnHotRepository repository;

    public MonAnHotViewModel() {
        repository = new MonAnHotRepository();
    }
    public MutableLiveData<ResponseMonAnHot> mutableLiveData(){
        return repository.mutableLiveData();
    }
}
