package com.example.foodapp.retrofit;

import com.example.foodapp.model.Category;

import java.util.List;

public class ResponseCategory {
    private boolean success;
    private String message;
    private List<Category> result;

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

    public List<Category> getResult() {
        return result;
    }

    public void setResult(List<Category> result) {
        this.result = result;
    }
}
