package com.example.task2;

import android.content.res.Resources;
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

    private RecyclerView recyclerView;
    private ArrayList<ItemData> newsArrayList;

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        recyclerView.findViewById(R.id.rvCollections);
//
//        // created new array list..
//        recyclerDataArrayList=new ArrayList<>();
//
//        // added data to array list
//        recyclerDataArrayList.add(new ItemData("Adding\n" + "in the beginning ArrayList N/A ms"));
//        recyclerDataArrayList.add(new ItemData("Adding\n" + "in the beginning ArrayList N/A ms"));
//        recyclerDataArrayList.add(new ItemData("Adding\n" + "in the beginning ArrayList N/A ms"));
//        recyclerDataArrayList.add(new ItemData("Adding\n" + "in the beginning ArrayList N/A ms"));
//        recyclerDataArrayList.add(new ItemData("Adding\n" + "in the beginning ArrayList N/A ms"));
//
//
//        // added data from arraylist to adapter class.
//        RecyclerViewAdapter adapter=new RecyclerViewAdapter(recyclerDataArrayList,this);
//
//        // setting grid layout manager to implement grid view.
//        // in this method '2' represents number of columns to be displayed in grid view.
//        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
//
//        // at last set adapter to recycler view.
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);
//    }

//    private RecyclerView recyclerView;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        recyclerView.findViewById(R.id.rvCollections);
//
//        ArrayList<ItemData> itemDataArrayList = new ArrayList<>();
//
//        itemDataArrayList.add(new ItemData("Adding\n" + "in the beginning ArrayList N/A ms"));
//        itemDataArrayList.add(new ItemData("Adding\n" + "in the beginning ArrayList N/A ms"));
//        itemDataArrayList.add(new ItemData("Adding\n" + "in the beginning ArrayList N/A ms"));
//
//        RecyclerViewAdapter adapter = new RecyclerViewAdapter(itemDataArrayList, this);
//
//        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
//    }

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
        recyclerView = view.findViewById(R.id.rvCollections);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        MyAdapter myAdapter = new MyAdapter(getContext(),newsArrayList);
        recyclerView.setAdapter(myAdapter);

    }

    private void datainitialize() {

        newsArrayList = new ArrayList<>();

        newsArrayList.add(new ItemData("Adding\n" + "in the beginning ArrayList N/A ms"));
        newsArrayList.add(new ItemData("Adding\n" + "in the beginning ArrayList N/A ms"));
        newsArrayList.add(new ItemData("Adding\n" + "in the beginning ArrayList N/A ms"));
        newsArrayList.add(new ItemData("Adding\n" + "in the beginning ArrayList N/A ms"));
        newsArrayList.add(new ItemData("Adding\n" + "in the beginning ArrayList N/A ms"));
        newsArrayList.add(new ItemData("Adding\n" + "in the beginning ArrayList N/A ms"));

    }
}