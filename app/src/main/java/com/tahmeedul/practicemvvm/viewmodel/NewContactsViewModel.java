package com.tahmeedul.practicemvvm.viewmodel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tahmeedul.practicemvvm.model.NewContactModel;
import com.tahmeedul.practicemvvm.model.UpdateContactModel;
import com.tahmeedul.practicemvvm.repository.NewContactRepository;

import java.util.List;

public class NewContactsViewModel extends AndroidViewModel {

    private NewContactRepository repository;
    public LiveData<String> liveData;
    public LiveData<List<NewContactModel>> allContacts;

    public NewContactsViewModel(@NonNull Application application) {
        super(application);

        repository = new NewContactRepository();

    }

    public void insertData(NewContactModel newContactModel, Uri imageUri){
        liveData = repository.insertContactFireStore(newContactModel, imageUri);
    }

    public void getData(){
        allContacts = repository.gettingContactsList();
    }

    public void deleteDataViewModel (String id){
        repository.deleteDataRepository(id);
    }

    public void updateImageViewModel(String id, Uri uri){
        repository.updateImageRepository(id, uri);
    }

    public void updateData(UpdateContactModel updatedData){
        repository.updateInfoRepository(updatedData);
    }
}
