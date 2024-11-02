package com.example.foodapp.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodapp.R;
import com.example.foodapp.activity.LoginActivity;
import com.example.foodapp.activity.ThongKeActivity;
import com.example.foodapp.databinding.FragmentSettingBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SettingFragment extends Fragment {

    FragmentSettingBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_setting,container,false);
        setControl();
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
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                                getActivity().finishAffinity();
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
                getActivity().finishAffinity();
            }
        });
    }
}