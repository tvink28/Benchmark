package com.example.task2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class CollectionsFragment extends Fragment {

    private ArrayList<RecyclerData> newsArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collections, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        datainitialize();
        RecyclerView recyclerView = view.findViewById(R.id.rvCollections);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(newsArrayList, getContext());
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    private void datainitialize() {

        newsArrayList = new ArrayList<>();
        newsArrayList.add(new RecyclerData(getResources().getString(R.string.adding_in_the_beginning_arraylist)));
        newsArrayList.add(new RecyclerData(getResources().getString(R.string.adding_in_the_beginning_linkedlist)));
        newsArrayList.add(new RecyclerData(getResources().getString(R.string.adding_in_the_beginning_copyonwritearraylist)));
        newsArrayList.add(new RecyclerData(getResources().getString(R.string.adding_in_the_middle_arraylist)));
        newsArrayList.add(new RecyclerData(getResources().getString(R.string.adding_in_the_middle_linkedlist)));
        newsArrayList.add(new RecyclerData(getResources().getString(R.string.adding_in_the_middle_copyonwritearraylist)));
        newsArrayList.add(new RecyclerData(getResources().getString(R.string.adding_in_the_end_arraylist)));
        newsArrayList.add(new RecyclerData(getResources().getString(R.string.adding_in_the_end_linkedlist)));
        newsArrayList.add(new RecyclerData(getResources().getString(R.string.adding_in_the_end_copyonwritearraylist)));
        newsArrayList.add(new RecyclerData(getResources().getString(R.string.search_by_value_arraylist)));
        newsArrayList.add(new RecyclerData(getResources().getString(R.string.search_by_value_linkedlist)));
        newsArrayList.add(new RecyclerData(getResources().getString(R.string.search_by_value_copyonwritearraylist)));
        newsArrayList.add(new RecyclerData(getResources().getString(R.string.removing_in_the_beginning_arraylist)));
        newsArrayList.add(new RecyclerData(getResources().getString(R.string.removing_in_the_beginning_linkedlist)));
        newsArrayList.add(new RecyclerData(getResources().getString(R.string.removing_in_the_beginning_copyonwritearraylist)));
        newsArrayList.add(new RecyclerData(getResources().getString(R.string.removing_in_the_middle_arraylist)));
        newsArrayList.add(new RecyclerData(getResources().getString(R.string.removing_in_the_middle_linkedlist)));
        newsArrayList.add(new RecyclerData(getResources().getString(R.string.removing_in_the_middle_copyonwritearraylist)));
        newsArrayList.add(new RecyclerData(getResources().getString(R.string.removing_in_the_end_arraylist)));
        newsArrayList.add(new RecyclerData(getResources().getString(R.string.removing_in_the_end_linkedlist)));
        newsArrayList.add(new RecyclerData(getResources().getString(R.string.removing_in_the_end_copyonwritearraylist)));

    }
}