package com.tahmeedul.practicemvvm.repository;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tahmeedul.practicemvvm.model.SignInModel;

public class SignInRepository {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private SignInModel user = new SignInModel();

    public MutableLiveData<SignInModel> checkAuthenticationFirebase(){
        MutableLiveData<SignInModel> isAuthenticateLiveData = new MutableLiveData<>();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null){
            user.isAuth = false;
            isAuthenticateLiveData.setValue(user);
        }else {
            user.uid = currentUser.getUid();
            user.isAuth = true;
            isAuthenticateLiveData.setValue(user);
        }

        return isAuthenticateLiveData;
    }

}
