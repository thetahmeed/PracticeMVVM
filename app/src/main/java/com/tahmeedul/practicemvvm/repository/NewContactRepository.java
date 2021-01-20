package com.tahmeedul.practicemvvm.repository;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tahmeedul.practicemvvm.model.NewContactModel;

import java.util.HashMap;
import java.util.Map;

public class NewContactRepository {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    // We have to pass the value of info and image uri
    public MutableLiveData<String> insertContactFireStore(NewContactModel newContactModel, Uri imageUri) {
        MutableLiveData<String> insertResultLiveData = new MutableLiveData<>();
        String uid = firebaseAuth.getCurrentUser().getUid();

        // 1. At first we will push the image to the storage and get the Download Url
        // 2. Then we will push download and other info to our database

        // Creating the root to push the image to Storage
        StorageReference storageRoot = storageReference.child("profile_pictures").child(uid).child(newContactModel.getNewId() + ".jpg");

        // Uploading the file
        storageRoot.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Getting the download Url
                storageRoot.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri imageUrl) {
                        Map<String, String> newUserAllData = new HashMap<>();

                        newUserAllData.put("id", newContactModel.getNewId());
                        newUserAllData.put("name", newContactModel.getNewName());
                        newUserAllData.put("phone", newContactModel.getNewPhone());
                        newUserAllData.put("email", newContactModel.getNewEmail());
                        newUserAllData.put("image", imageUrl.toString());

                        // Pushing all info to the database
                        firebaseFirestore.collection("ContactsList").document(uid)
                                .collection("User").document(newContactModel.getNewId()).set(newUserAllData)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        insertResultLiveData.setValue("Insert successfully");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        insertResultLiveData.setValue(e.toString());
                                     }
                                });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                insertResultLiveData.setValue(e.toString());
            }
        });


        return insertResultLiveData;
    }

}
