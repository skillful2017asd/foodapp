package com.example.foodapp.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodapp.repository.ThongKeRepository;
import com.example.foodapp.retrofit.ResponseThongKe;

public class ThongKeViewModel extends ViewModel {

    ThongKeRepository thongKeRepository;

    public ThongKeViewModel() {
        thongKeRepository = new ThongKeRepository();
    }
    public MutableLiveData<ResponseThongKe> mutableLiveData (String id){
        return thongKeRepository.mutableLiveData(id);
    }

}
