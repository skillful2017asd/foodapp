package com.example.foodapp.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodapp.R;
import com.example.foodapp.databinding.ActivityUpdateProFileBinding;
import com.example.foodapp.fragment.SettingFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.IOException;

import io.paperdb.Paper;

public class UpdateProFileActivity extends AppCompatActivity {

    ActivityUpdateProFileBinding binding;
    public static final int REQUEST_CODE_STORAGE_PERMISSION = 10;

    private Uri uri;

    ActivityResultLauncher<Intent> mactivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if (o.getResultCode() == Activity.RESULT_OK) {
                Intent data = o.getData();
                if (data != null) {
                    uri = data.getData();
                    Paper.book().write("Uri", uri.toString());
                    if (uri != null) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.d("thread",Thread.currentThread().getName());
                                            setImage(bitmap);
                                            Glide.with(UpdateProFileActivity.this).load(uri).error(R.drawable.logofood).into(binding.imgProfile);
                                        }
                                    });
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_update_pro_file);
       Paper.init(this);
        setControl();
    }

    private void setControl(){
        binding.imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermisson();
            }
        });
        binding.updateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickupdateprofile();
            }
        });
    }

    private void onClickupdateprofile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            return;
        }

        String fullname = binding.edtFullnameProfile.getText().toString().trim();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(fullname)
                .setPhotoUri(uri)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UpdateProFileActivity.this,"Update success",Toast.LENGTH_SHORT).show();
                            Intent resultIntent = new Intent(UpdateProFileActivity.this,MainActivity.class);
                            resultIntent.setData(uri);
                            setResult(Activity.RESULT_OK, resultIntent);
                            startActivity(resultIntent);
                        }
                    }
                });
    }

    private void onClickRequestPermisson() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            openGallery();
            return;
        }
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            openGallery();
        }else{
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PERMISSION);
        }
    }

    public void setImage(Bitmap bitmap){
        binding.imgProfile.setImageBitmap(bitmap);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_CODE_STORAGE_PERMISSION){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery();

            }else{
                Toast.makeText(this, "Bạn cần cấp quyền để sử dụng tính năng này", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void openGallery(){
        Intent intent =new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mactivityResultLauncher.launch(Intent.createChooser(intent,"Chọn Ảnh"));
    }
}