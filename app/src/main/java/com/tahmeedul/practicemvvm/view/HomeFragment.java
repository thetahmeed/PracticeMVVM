package com.tahmeedul.practicemvvm.view;

import android.content.Intent;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.tahmeedul.practicemvvm.R;
import com.tahmeedul.practicemvvm.model.SignInModel;
import com.tahmeedul.practicemvvm.viewmodel.SignInViewModel;

public class HomeFragment extends Fragment {

    private SignInViewModel signInViewModel;
    private Button logOutButton;
    private CircularImageView userImage;
    private TextView userName, userEmail;

    // Sign out 0/3
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

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

        // Sign out 1/3
        getGoogleSignIn();
        getUserInfo();

        logOutButton = view.findViewById(R.id.logOutButtonId);
        userImage = view.findViewById(R.id.userImageViewId);
        userName = view.findViewById(R.id.userNameId);
        userEmail = view.findViewById(R.id.userEmailId);

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOutFromGoogle();
            }
        });

    }

    private void getGoogleSignIn() {
        // Sign out 2/3
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
    }

    private void signOutFromGoogle() {
        // Sign out 3/3
        firebaseAuth.signOut();
        mGoogleSignInClient.signOut();
        Intent intent = new Intent(getActivity(), SignInActivity.class);
        startActivity(intent);
        getActivity().onBackPressed();
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