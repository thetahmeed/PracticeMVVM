package com.tahmeedul.practicemvvm.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.tahmeedul.practicemvvm.R;
import com.tahmeedul.practicemvvm.adapter.AllContactsAdapter;
import com.tahmeedul.practicemvvm.dialogue.DetailsDialogue;
import com.tahmeedul.practicemvvm.model.NewContactModel;
import com.tahmeedul.practicemvvm.model.UpdateContactModel;
import com.tahmeedul.practicemvvm.viewmodel.NewContactsViewModel;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.List;

import dmax.dialog.SpotsDialog;

public class ListFragment extends Fragment implements AllContactsAdapter.ClickInterface {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private NewContactsViewModel newContactsViewModel;
    private AllContactsAdapter allContactsAdapter;

    public List<NewContactModel> list;

    private int longPressPosition;
    private Uri selectedImageUri = null;
    CircularImageView updateImage;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchView = view.findViewById(R.id.searchContactListId);
        recyclerView = view.findViewById(R.id.contactListRecyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        allContactsAdapter = new AllContactsAdapter(this);

        inNewContactViewModel();
        setUpRecycle();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getResult(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void getResult(String query) {
        AlertDialog dialog = new SpotsDialog.Builder()
                .setContext(getActivity())
                .setTheme(R.style.Custom)
                .setCancelable(false)
                .build();
        dialog.show();

        newContactsViewModel.searchDataViewModel(query);
        newContactsViewModel.searchLiveData.observe(getViewLifecycleOwner(), new Observer<List<NewContactModel>>() {
            @Override
            public void onChanged(List<NewContactModel> resultList) {
                list = resultList;
                allContactsAdapter.getContactList(resultList);
                recyclerView.setAdapter(allContactsAdapter);
                dialog.dismiss();
                if (list.size() == 0){
                    Toast.makeText(getActivity(), "No result found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setUpRecycle() {
        // Showing the dialogue
        AlertDialog dialog = new SpotsDialog.Builder()
                .setContext(getActivity())
                .setTheme(R.style.Custom)
                .setCancelable(false)
                .build();
        dialog.show();

        newContactsViewModel.getData();
        newContactsViewModel.allContacts.observe(getViewLifecycleOwner(), new Observer<List<NewContactModel>>() {
            @Override
            public void onChanged(List<NewContactModel> newContactModels) {
                if (newContactModels.size() != 0) {
                    // Data found
                    list = newContactModels;
                    allContactsAdapter.getContactList(list);
                    recyclerView.setAdapter(allContactsAdapter);

                    dialog.dismiss();

                } else {
                    // No data found
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "No contacts found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void inNewContactViewModel() {
        newContactsViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(NewContactsViewModel.class);
    }

    @Override
    public void onItemClick(int position) {
        openDetailsDialogue(position);
    }

    private void openDetailsDialogue(int position) {
        DetailsDialogue detailsDialogue = new DetailsDialogue(list, position);

        // getChild because that dialogue is the child on this fragment
        detailsDialogue.show(getChildFragmentManager(), "Details Dialogue");
    }

    @Override
    public void onItemLongClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String[] options = {"Update", "Delete"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    updateData(position);
                } else if (i == 1) {
                    // delete the image and data
                    newContactsViewModel.deleteDataViewModel(list.get(position).getNewId());
                    Toast.makeText(getActivity(), "Deleted",
                            Toast.LENGTH_SHORT).show();
                    // update the list
                    list.remove(position);
                    allContactsAdapter.notifyItemRemoved(position);
                }
            }
        }).create().show();

    }

    private void updateData(int position) {
        longPressPosition = position;

        Button updateImageButton;
        EditText updateName, updatePhone, updateEmail;
        Button updateDataButton;

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        View view = layoutInflater.inflate(R.layout.model_update_data, null);
        builder.setView(view)
                .setTitle("Update")
                .setIcon(R.drawable.ic_baseline_phone_24)
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        updateImage = view.findViewById(R.id.updateImage);
        Glide.with(getActivity())
                .load(list.get(longPressPosition).getNewImage())
                .placeholder(R.drawable.ic_baseline_person_24)
                .centerCrop()
                .into(updateImage);

        updateImageButton = view.findViewById(R.id.updateImageButtonId);
        updateImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedImageUri != null){
                    newContactsViewModel.updateImageViewModel(list.get(longPressPosition).getNewId(), selectedImageUri);
                    Toast.makeText(getActivity(), "Image updated", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "Click in the image to update the image", Toast.LENGTH_SHORT).show();
                }
            }
        });
        updateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

        updateName = view.findViewById(R.id.updateName);
        updatePhone = view.findViewById(R.id.updatePhone);
        updateEmail = view.findViewById(R.id.updateEmail);

        updateName.setText(list.get(longPressPosition).getNewName());
        updatePhone.setText(list.get(longPressPosition).getNewPhone());
        if (list.get(longPressPosition).getNewEmail().contains("@")){
            updateEmail.setText(list.get(longPressPosition).getNewEmail());
        }

        updateDataButton = view.findViewById(R.id.updateInfoButtonId);
        updateDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = list.get(longPressPosition).getNewId();
                String name = updateName.getText().toString().trim();
                String phone = updatePhone.getText().toString().trim().replaceAll(" ", "");
                String email = updateEmail.getText().toString().trim().replaceAll(" ", "");

                if (name.isEmpty()){
                    updateName.setError("This field can't be empty");
                    updateName.requestFocus();
                }else if (phone.isEmpty()){
                    updatePhone.setError("This field can't be empty");
                    updatePhone.requestFocus();
                }else if (phone.length() != 11) {
                    updatePhone.setError("Phone number is not valid");
                    updatePhone.requestFocus();
                }else if (!email.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    updateEmail.setError("Email is not valid");
                    updateEmail.requestFocus();
                }else {
                    if (email.isEmpty()) {
                        email = "Email not found";
                    }
                    UpdateContactModel updatedData = new UpdateContactModel(id, name, phone, email);
                    newContactsViewModel.updateData(updatedData);
                    Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.create().show();

    }

    private void pickImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(getContext(), this);
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
                updateImage.setImageURI(selectedImageUri);
            }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception exception = result.getError();
                Toast.makeText(getActivity(), "Error: "+exception.toString(), Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getActivity(), "Image selection failed", Toast.LENGTH_SHORT).show();
        }

    }

}