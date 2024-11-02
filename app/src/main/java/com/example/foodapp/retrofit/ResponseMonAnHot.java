package com.example.foodapp.retrofit;

import com.example.foodapp.model.MonAnHot;

import java.util.List;

public class ResponseMonAnHot {
    private boolean success;
    private String message;
    private List<MonAnHot> result;

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

    public List<MonAnHot> getResult() {
        return result;
    }

    public void setResult(List<MonAnHot> result) {
        this.result = result;
    }
}
