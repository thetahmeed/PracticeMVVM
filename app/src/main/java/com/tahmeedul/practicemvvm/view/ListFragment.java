package com.tahmeedul.practicemvvm.view;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tahmeedul.practicemvvm.R;
import com.tahmeedul.practicemvvm.adapter.AllContactsAdapter;
import com.tahmeedul.practicemvvm.dialogue.DetailsDialogue;
import com.tahmeedul.practicemvvm.model.NewContactModel;
import com.tahmeedul.practicemvvm.viewmodel.NewContactsViewModel;

import java.util.List;

import dmax.dialog.SpotsDialog;

public class ListFragment extends Fragment implements AllContactsAdapter.ClickInterface{

    private SearchView searchView;
    private RecyclerView recyclerView;
    private NewContactsViewModel newContactsViewModel;
    private AllContactsAdapter allContactsAdapter;

    public List<NewContactModel> list;

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
                if (newContactModels.size() != 0){
                    // Data found
                    list = newContactModels;
                    allContactsAdapter.getContactList(list);
                    recyclerView.setAdapter(allContactsAdapter);

                    dialog.dismiss();

                }else {
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
        Toast.makeText(getActivity(), ""+position+"Long Click", Toast.LENGTH_SHORT).show();
    }
}