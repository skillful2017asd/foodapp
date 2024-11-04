package com.example.foodapp.fragment;

import static android.app.Activity.RESULT_OK;
import static com.example.foodapp.activity.UpdateProFileActivity.REQUEST_CODE_STORAGE_PERMISSION;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodapp.R;
import com.example.foodapp.activity.LoginActivity;
import com.example.foodapp.activity.ThongKeActivity;
import com.example.foodapp.activity.UpdateProFileActivity;
import com.example.foodapp.databinding.FragmentSettingBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.Set;

import io.paperdb.Paper;

public class SettingFragment extends Fragment {

    FragmentSettingBinding binding;
    private final ActivityResultLauncher<Intent> mactivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if(o.getResultCode()==RESULT_OK ) {
                        showUserInfor();
                    }

                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_setting,container,false);
        Paper.init(getActivity());
        setControl();
        showUserInfor();
        return binding.getRoot();
    }
    private void setControl() {
        binding.tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Log out?")
                        .setMessage("You sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Paper.delete("Uri");
                                FirebaseAuth.getInstance().signOut();

                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
            }
        });
        binding.tvThongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ThongKeActivity.class);
                startActivity(intent);
            }
        });
        binding.tvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),UpdateProFileActivity.class);
                mactivityResultLauncher.launch(intent);
            }
        });
    }
    public void showUserInfor(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            return;
        }

        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri uri1 = user.getPhotoUrl();

        if(name == null){
            binding.tvFullname.setVisibility(View.GONE);
        }else{
            binding.tvFullname.setVisibility(View.VISIBLE);
            binding.tvFullname.setText(name);
        }

        if (uri1 != null) {
            Glide.with(this).load(uri1).error(R.drawable.logofood).into(binding.imgSetting);
        } else {
            String savedUri = Paper.book().read("Uri", null);
            if (savedUri != null) {
                Glide.with(this).load(Uri.parse(savedUri)).error(R.drawable.logofood).into(binding.imgSetting);
            } else {
                binding.imgSetting.setImageResource(R.drawable.logofood);
            }
        }

        binding.tvEmail.setText(email);

    }

}