package com.example.foodapp.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Process;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodapp.R;
import com.example.foodapp.activity.MainActivity;
import com.example.foodapp.adapter.ViewPagerLoginAdapter;
import com.example.foodapp.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends Fragment {

    FragmentLoginBinding binding;
    ProgressDialog dialogFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLoginSuccess();
            }
        });
        dialogFragment = new ProgressDialog(getActivity());


        return binding.getRoot();


    }

    private void setLoginSuccess() {
        dialogFragment.setMessage("Dang dang nhap");
        dialogFragment.show();
        String email = binding.edtLogin1.getText().toString().trim();
        String password = binding.edtLogin2.getText().toString().trim();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            dialogFragment.dismiss();
                            Toast.makeText(getActivity(),"Dang ky thanh cong user"+email,Toast.LENGTH_SHORT).show();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null) {
                                String userId = user.getUid();
                                String name = user.getEmail();
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                intent.putExtra("userID", userId);
                                intent.putExtra("nameID", name);
                                startActivity(intent);
                                getActivity().finishAffinity();
                            }


                        }else {
                            dialogFragment.dismiss();
                            Toast.makeText(getActivity(),"Dang ky that bai"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }
}