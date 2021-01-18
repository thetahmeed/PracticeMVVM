package com.tahmeedul.practicemvvm.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.tahmeedul.practicemvvm.model.SignInModel;
import com.tahmeedul.practicemvvm.viewmodel.SignInViewModel;

public class SplashActivity extends AppCompatActivity {

    private SignInViewModel signInViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inSplashViewModel();
        checkIfUserAuthenticate();

    }

    private void checkIfUserAuthenticate() {
        signInViewModel.checkAuthenticate();
        signInViewModel.checkAuthenticateLiveData.observe(this, new Observer<SignInModel>() {
            @Override
            public void onChanged(SignInModel signInModel) {
                if (signInModel.isAuth){
                    goToMainActivity();
                }else {
                    goToSignInActivity();
                }
            }
        });
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToSignInActivity() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    private void inSplashViewModel() {
        signInViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory
                        .getInstance(this.getApplication()))
                        .get(SignInViewModel.class);

    }
}