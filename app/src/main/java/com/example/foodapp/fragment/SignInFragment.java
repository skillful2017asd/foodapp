package com.example.foodapp.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodapp.R;
import com.example.foodapp.activity.MainActivity;
import com.example.foodapp.databinding.FragmentSignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInFragment extends Fragment {



    private FragmentSignInBinding binding;
    ProgressDialog dialogFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_sign_in,container,false);
        // Inflate the layout for this fragment
        binding.btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSignInSuccess();
            }
        });
        dialogFragment = new ProgressDialog(getActivity());

        return binding.getRoot();

    }

    private void setSignInSuccess() {
        dialogFragment.setMessage("Dang dang ky");
        dialogFragment.show();
        String email = binding.edtSign1.getText().toString().trim();
        String password = binding.edtSign2.getText().toString().trim();
        String password1 = binding.edtSign3.getText().toString().trim();
        if(password1.contains(password)) {


            FirebaseAuth auth = FirebaseAuth.getInstance();


            auth.createUserWithEmailAndPassword(email,password1)
                    .addOnCompleteListener(getActivity(),new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                dialogFragment.dismiss();
                                getUser();
                                Toast.makeText(getActivity(),"Dang ky thanh cong"+email,Toast.LENGTH_SHORT).show();
                            }else {
                                dialogFragment.dismiss();
                                Toast.makeText(getActivity(),"Dang ky that bai"+email,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void getUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String userId = user.getUid();
        String name = user.getEmail();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("userID",userId);
        intent.putExtra("nameID",name);
        startActivity(intent);
        getActivity().finishAffinity();
    }
}