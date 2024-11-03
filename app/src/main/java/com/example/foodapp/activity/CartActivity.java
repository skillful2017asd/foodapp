package com.example.foodapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.foodapp.R;
import com.example.foodapp.adapter.CartAdapter;
import com.example.foodapp.databinding.ActivityCartBinding;
import com.example.foodapp.model.Cart;
import com.example.foodapp.model.MessModel;
import com.example.foodapp.untils.Utils;
import com.example.foodapp.viewModel.CartViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.util.List;

import io.paperdb.Paper;

public class CartActivity extends AppCompatActivity {

    private static final String CHANNEL_ID ="my_channel_id" ;
    ActivityCartBinding binding;
    CartAdapter cartAdapter;
    CartViewModel cartViewModel;
    int item;
    Double price;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_cart);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userid = user.getUid();
        Paper.init(this);
        setUI();
        updatacart();
        setData();
        TotalPrice();
    }

    private void setUI() {
        binding.rcvCart.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rcvCart.setLayoutManager(layoutManager);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
    }

    private void setData() {

        cartViewModel.init();
        cartViewModel.messModelMutableLiveData().observe(this, new Observer<MessModel>() {
            @Override
            public void onChanged(MessModel messModel) {
                Intent home = new Intent(CartActivity.this,MainActivity.class);
                startActivity(home);
                //TotalPrice();
                Utils.cartList.clear();
                Paper.book().delete(userid+"cart");
                finish();
            }
        });
        updatacart();

        binding.btnThanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cart = new Gson().toJson(Utils.cartList);
                Log.d("logg",cart);
                cartViewModel.checkOut(userid,item,price,cart);
                pushNotification();
            }
        });
    }

    private void pushNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Thông báo từ App Food!";
            String description = "Nhận thông báo về các đơn hàng và ưu đãi mới nhất.";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_done)
                .setContentTitle("Food App cảm ơn!")
                .setContentText("Cảm ơn bạn đã đặt hàng và tin tưởng chúng tôi...")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationId = 1;
        notificationManager.notify(notificationId, builder.build());
    }

    private void updatacart() {
        if(Utils.cartList!=null){
            List<Cart> cartList = Paper.book().read(userid+"cart");
            Utils.cartList = cartList;
            cartAdapter = new CartAdapter(Utils.cartList,this,() -> {
                TotalPrice();
            },userid);
            binding.rcvCart.setAdapter(cartAdapter);
        }
    }

    private void TotalPrice() {
        item =0;
        price = 0.0;
        if(Utils.cartList!=null){
            for(int i=0;i<Utils.cartList.size();i++){
                item += Utils.cartList.get(i).getAmount();
            }
            for(int i =0;i<Utils.cartList.size();i++){
                price+= Utils.cartList.get(i).getAmount() * Utils.cartList.get(i).getMealDetail().getPrice();
            }
        }

        binding.tvItem.setText(item+"");
        binding.tvPrice.setText(price+"$");
    }
}