package com.tahmeedul.practicemvvm.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tahmeedul.practicemvvm.R;
import com.tahmeedul.practicemvvm.model.NewContactModel;
import com.tahmeedul.practicemvvm.viewmodel.NewContactsViewModel;

import java.util.List;

public class ListFragment extends Fragment {

    private NewContactsViewModel newContactsViewModel;

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
        inNewContactViewModel();
        setUpRecycle();


    }

    private void setUpRecycle() {
        newContactsViewModel.getData();
        newContactsViewModel.allContacts.observe(getViewLifecycleOwner(), new Observer<List<NewContactModel>>() {
            @Override
            public void onChanged(List<NewContactModel> newContactModels) {
                if (newContactModels.size() != 0){
                    String name = newContactModels.get(0).getNewName();
                    Toast.makeText(getActivity(), ""+name, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "No contacts found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void inNewContactViewModel() {
        newContactsViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(NewContactsViewModel.class);
    }
}