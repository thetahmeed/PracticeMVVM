package com.tahmeedul.practicemvvm.viewmodel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tahmeedul.practicemvvm.model.NewContactModel;
import com.tahmeedul.practicemvvm.repository.NewContactRepository;

public class NewContactsViewModel extends AndroidViewModel {

    private NewContactRepository repository;
    public LiveData<String> liveData;

    public NewContactsViewModel(@NonNull Application application) {
        super(application);

        repository = new NewContactRepository();

    }

    public void insertData(NewContactModel newContactModel, Uri imageUri){
        liveData = repository.insertContactFireStore(newContactModel, imageUri);
    }

}
