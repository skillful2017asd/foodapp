package com.example.foodapp.model;

public class ThongKe {
    private String iduser;
    private String tongtien;
    private String thang;

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getTongtien() {
        return tongtien;
    }

    public void setTongtien(String tongtien) {
        this.tongtien = tongtien;
    }

    public String getThang() {
        return thang;
    }

    public void setThang(String thang) {
        this.thang = thang;
    }
    @Override
    public String toString() {
        return "ThongKe{" +
                "iduser='" + iduser + '\'' +
                ", tongtien='" + tongtien + '\'' +
                ", thang='" + thang + '\'' +
                '}';
    }
}
