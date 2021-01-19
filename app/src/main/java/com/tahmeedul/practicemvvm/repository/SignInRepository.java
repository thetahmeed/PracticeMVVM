package com.tahmeedul.practicemvvm.repository;

import android.net.Uri;

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

    // Checking user is logged in or not
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

    // Getting the user ID
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

    // Collecting the user Data
    public  MutableLiveData<SignInModel> collectUserData(){
        MutableLiveData<SignInModel> collectMutableLiveData = new MutableLiveData<>();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null){
            String uid = currentUser.getUid();
            String name = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            Uri imageUri = currentUser.getPhotoUrl();
            String image = imageUri.toString();

            SignInModel signInModel = new SignInModel(uid, name, email, image);
            collectMutableLiveData.setValue(signInModel);
        }else {

        }

        return collectMutableLiveData;
    }

}
