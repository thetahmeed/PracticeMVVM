package com.tahmeedul.practicemvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tahmeedul.practicemvvm.model.SignInModel;
import com.tahmeedul.practicemvvm.repository.SignInRepository;

public class SignInViewModel extends AndroidViewModel {

    private SignInRepository signInRepository;
    public LiveData<SignInModel> checkAuthenticateLiveData;

    public SignInViewModel(@NonNull Application application) {
        super(application);

        signInRepository = new SignInRepository();

    }

    public void checkAuthenticate(){

        checkAuthenticateLiveData = signInRepository.checkAuthenticationFirebase();

    }

}
