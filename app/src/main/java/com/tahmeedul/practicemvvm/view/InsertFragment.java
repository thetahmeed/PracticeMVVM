package com.tahmeedul.practicemvvm.view;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.tahmeedul.practicemvvm.R;

public class InsertFragment extends Fragment {

    CircularImageView newImage;
    EditText newName, newPhone, newEmail;
    Button newButton;

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

        newImage = view.findViewById(R.id.userImageViewId);
        newName = view.findViewById(R.id.newNameId);
        newPhone = view.findViewById(R.id.newPhoneId);
        newEmail = view.findViewById(R.id.newEmailId);
        newButton = view.findViewById(R.id.newSaveButtonId);

        newImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


}