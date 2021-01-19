package com.tahmeedul.practicemvvm.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tahmeedul.practicemvvm.R;
import com.tahmeedul.practicemvvm.viewmodel.SignInViewModel;

public class SignInActivity extends AppCompatActivity {

    private SignInViewModel signInViewModel;
    private Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);


        signInButton = findViewById(R.id.signInButtonId);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}