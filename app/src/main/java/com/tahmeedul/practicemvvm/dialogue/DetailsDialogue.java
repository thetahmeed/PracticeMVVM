package com.tahmeedul.practicemvvm.dialogue;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.tahmeedul.practicemvvm.R;
import com.tahmeedul.practicemvvm.model.NewContactModel;

import java.util.List;

public class DetailsDialogue extends DialogFragment {

    private ImageView userImage;
    private TextView id, name, phone, email;
    private List<NewContactModel> userList;
    private int position;

    public DetailsDialogue(List<NewContactModel> userList, int position) {
        this.userList = userList;
        this.position = position;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.model_details_single_contact, null);
        builder.setView(view)
                .setTitle("Details")
                .setIcon(R.drawable.ic_baseline_phone_24)
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        userImage = view.findViewById(R.id.singleDp);
        id = view.findViewById(R.id.singleId);
        name = view.findViewById(R.id.singleName);
        phone = view.findViewById(R.id.singlePhone);
        email = view.findViewById(R.id.singleEmail);

        Glide.with(getActivity())
                .load(userList.get(position).getNewImage())
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_person_24)
                .into(userImage);
        id.setText("Id: "+userList.get(position).getNewId());
        name.setText("Name: "+userList.get(position).getNewName());
        phone.setText("Phone: "+userList.get(position).getNewPhone());
        email.setText("Email: "+userList.get(position).getNewEmail());

        return builder.create();
    }
}
