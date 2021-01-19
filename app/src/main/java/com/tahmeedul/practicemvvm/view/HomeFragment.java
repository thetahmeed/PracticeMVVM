package com.tahmeedul.practicemvvm.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.tahmeedul.practicemvvm.R;
import com.tahmeedul.practicemvvm.model.SignInModel;
import com.tahmeedul.practicemvvm.viewmodel.SignInViewModel;

public class HomeFragment extends Fragment {

    private SignInViewModel signInViewModel;
    private Button logOutButton;
    private CircularImageView userImage;
    private TextView userName, userEmail;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getUserInfo();

        logOutButton = view.findViewById(R.id.logOutButtonId);
        userImage = view.findViewById(R.id.userImageViewId);
        userName = view.findViewById(R.id.userNameId);
        userEmail = view.findViewById(R.id.userEmailId);


    }

    private void getUserInfo() {
        signInViewModel = new ViewModelProvider(getActivity(), ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(SignInViewModel.class);
        signInViewModel.collectUserInfo();
        signInViewModel.collectUserInfoLiveData.observe(getViewLifecycleOwner(), new Observer<SignInModel>() {
            @Override
            public void onChanged(SignInModel signInModel) {
                setUserInfo(signInModel);
            }
        });
    }

    private void setUserInfo(SignInModel signInModel) {
        Glide.with(getActivity())
                .load(signInModel.imageUrl)
                .placeholder(R.drawable.ic_baseline_person_24)
                .centerCrop()
                .into(userImage);
        userName.setText(signInModel.getName());
        userEmail.setText(signInModel.getEmail());
    }

}