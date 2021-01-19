package com.tahmeedul.practicemvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.AuthCredential;
import com.tahmeedul.practicemvvm.model.SignInModel;
import com.tahmeedul.practicemvvm.repository.SignInRepository;

public class SignInViewModel extends AndroidViewModel {

    private SignInRepository signInRepository;
    public LiveData<SignInModel> checkAuthenticateLiveData;
    public LiveData<String> authenticationLiveData;
    public LiveData<SignInModel> collectUserInfoLiveData;

    public SignInViewModel(@NonNull Application application) {
        super(application);
        signInRepository = new SignInRepository();
    }

    // sign in
    public void signInWithGoogle(AuthCredential authCredential){
        authenticationLiveData = signInRepository.firebaseSignInWithGoogle(authCredential);
    }

    // check sign in
    public void checkAuthenticate(){
        checkAuthenticateLiveData = signInRepository.checkAuthenticationFirebase();
    }

    // collect data
    public void collectUserInfo(){
        collectUserInfoLiveData = signInRepository.collectUserData();
    }

}
