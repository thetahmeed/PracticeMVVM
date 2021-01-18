package com.tahmeedul.practicemvvm.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
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

    public MutableLiveData<String> firebaseSignInWithGoogle(AuthCredential authCredential){
        MutableLiveData<String> mutableLiveData = new MutableLiveData<>();

        firebaseAuth.signInWithCredential(authCredential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                String uid = currentUser.getUid();
                mutableLiveData.setValue(uid);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mutableLiveData.setValue(e.toString());
            }
        });

        return mutableLiveData;
    }

}
