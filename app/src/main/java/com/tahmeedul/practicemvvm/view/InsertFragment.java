package com.tahmeedul.practicemvvm.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.tahmeedul.practicemvvm.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Random;

public class InsertFragment extends Fragment {

    CircularImageView newImage;
    EditText newName, newPhone, newEmail;
    Button newButton;
    Uri selectedImageUri;

    public InsertFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_insert, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        newImage = view.findViewById(R.id.newImageViewId);
        newName = view.findViewById(R.id.newNameId);
        newPhone = view.findViewById(R.id.newPhoneId);
        newEmail = view.findViewById(R.id.newEmailId);
        newButton = view.findViewById(R.id.newSaveButtonId);

        newImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = randomDigit();
                String name = newName.getText().toString().trim();
                String phone = newPhone.getText().toString().trim().replace(" ", "");
                String email = newEmail.getText().toString().trim().replaceAll(" ", "");

                if (name.isEmpty()){
                    newName.setError("This field can't be empty");
                    newName.requestFocus();
                }else if (phone.isEmpty()){
                    newPhone.setError("This field can't be empty");
                    newPhone.requestFocus();
                }else if (phone.length() != 11) {
                    newPhone.setError("Phone number is not valid");
                    newPhone.requestFocus();
                }else if (!email.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    newEmail.setError("Email is not valid");
                    newEmail.requestFocus();
                }else {
                    if (email.isEmpty()){
                        email = "Email not found";
                    }

                    Toast.makeText(getActivity(), "Saving...", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private String randomDigit() {
        char[] chars= "1234567890".toCharArray();
        StringBuilder stringBuilder= new StringBuilder();
        Random random= new Random();
        // 5 digit random number
        for(int i=0;i<5;i++){
            char c= chars[random.nextInt(chars.length)];
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }


    private void uploadImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            // We have to take the permission

            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                // Permission not granted
                ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }else {
                // Permission is granted
                pickImage();
            }

        }else {
            // No need to ask for permission
            pickImage();
        }
    }

    private void pickImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(getContext(), this);             //.start(getActivity()); {Will check it letter}

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == getActivity().RESULT_OK){
                // got the uri
                selectedImageUri = result.getUri();
                // set selected image on the newImage
                newImage.setImageURI(selectedImageUri);
            }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception exception = result.getError();
                Toast.makeText(getActivity(), "Error: "+exception.toString(), Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getActivity(), "Image selection failed", Toast.LENGTH_SHORT).show();
        }

    }

}