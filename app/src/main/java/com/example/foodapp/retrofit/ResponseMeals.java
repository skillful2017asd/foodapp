package com.example.foodapp.retrofit;

import com.example.foodapp.model.Category;
import com.example.foodapp.model.Meals;

import java.util.List;

public class ResponseMeals {
    private boolean success;
    private String message;
    private List<Meals> result;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Meals> getResult() {
        return result;
    }

    public void setResult(List<Meals> result) {
        this.result = result;
    }
}
