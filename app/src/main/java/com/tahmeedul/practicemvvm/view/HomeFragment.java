package com.tahmeedul.practicemvvm.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.tahmeedul.practicemvvm.R;

public class HomeFragment extends Fragment {

    private Button logOutButton;
    private CircularImageView userImage;
    private TextView userName, userEmail;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        logOutButton = view.findViewById(R.id.logOutButtonId);

        userImage = view.findViewById(R.id.userImageViewId);

        userName = view.findViewById(R.id.userNameId);
        userEmail = view.findViewById(R.id.userEmailId);


    }
}