package com.tahmeedul.practicemvvm.repository;

import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tahmeedul.practicemvvm.model.NewContactModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
                        firebaseFirestore.collection("User").document(uid)
                                .collection("ContactsList").document(newContactModel.getNewId()).set(newUserAllData)
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

    // Getting data
    public MutableLiveData<List<NewContactModel>> gettingContactsList(){
        MutableLiveData<List<NewContactModel>> allSavedContacts = new MutableLiveData<>();

        String uid = firebaseAuth.getCurrentUser().getUid();
        List<NewContactModel> contactsList = new ArrayList<>();

        // We will got the data on 'task'
        firebaseFirestore.collection("User").document(uid)
                .collection("ContactsList").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                contactsList.clear();   // Clear the previous contact list

                for (DocumentSnapshot documentSnapshot : task.getResult()){
                    String id = documentSnapshot.getString("id");
                    String name = documentSnapshot.getString("name");
                    String phone = documentSnapshot.getString("phone");
                    String email = documentSnapshot.getString("email");
                    String image = documentSnapshot.getString("image");

                    // 1 Getting each Contact
                    NewContactModel newContactModel = new NewContactModel(id, name, phone, email, image);
                    // 2 Adding then to the list
                    contactsList.add(newContactModel);
                }
                // 3 Adding the list to MutableLiveData
                allSavedContacts.setValue(contactsList);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


        return allSavedContacts;
    }

}