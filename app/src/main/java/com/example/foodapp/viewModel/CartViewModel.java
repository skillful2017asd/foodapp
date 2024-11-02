package com.example.foodapp.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodapp.model.MessModel;
import com.example.foodapp.repository.CartRepository;

public class CartViewModel extends ViewModel {
    private CartRepository cartRepository;
    private MutableLiveData<MessModel> liveData;
    public void init(){
        cartRepository = new CartRepository();
        liveData = cartRepository.messModelMutableLiveData();
    }
    public void checkOut(String iduser,int amount,double total,String detail){
        cartRepository.checkOut(iduser,amount,total,detail);
    }
    public MutableLiveData<MessModel> messModelMutableLiveData(){
        return liveData;
    }
}
