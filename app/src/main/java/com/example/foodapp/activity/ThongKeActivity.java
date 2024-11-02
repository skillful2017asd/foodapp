package com.example.foodapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.foodapp.R;
import com.example.foodapp.retrofit.ResponseThongKe;
import com.example.foodapp.viewModel.ThongKeViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import io.paperdb.Paper;

public class ThongKeActivity extends AppCompatActivity {

    BarChart  barchar;
    Toolbar toolbar;
    ArrayList<BarEntry> entries = new ArrayList<>();
    ArrayList<String> labels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);

        Paper.init(this);
        barchar = findViewById(R.id.barchar);
        toolbar = findViewById(R.id.toolbar);
        addControl();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThongKeActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addControl() {
        ThongKeViewModel vIewModel = new ViewModelProvider(this).get(ThongKeViewModel.class );
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String iduser = user.getUid();
        vIewModel.mutableLiveData(iduser).observe(this, new Observer<ResponseThongKe>() {
            @Override
            public void onChanged(ResponseThongKe responseThongKe) {
                if (responseThongKe.isSuccess()) {
                    for (int i = 0; i < responseThongKe.getResult().size(); i++) {
                        String tongtien = responseThongKe.getResult().get(i).getTongtien();
                        String thang = responseThongKe.getResult().get(i).getThang();
                        entries.add(new BarEntry(Integer.parseInt(thang), Float.parseFloat(tongtien)));
                    }
                    BarDataSet dataSet = new BarDataSet(entries, "Doanh thu hàng tháng");
                    dataSet.setColor(ColorTemplate.MATERIAL_COLORS[0]);
                    dataSet.setValueTextSize(15f);
                    dataSet.setValueTextColor(Color.RED);

                    BarData data = new BarData(dataSet);
                    barchar.setData(data);
                    barchar.animateXY(2000, 2000);
                    barchar.invalidate();
                } else {
                    Log.d("thongke", "Không có dữ liệu để hiển thị.");
                }
            }
        });
    }
}