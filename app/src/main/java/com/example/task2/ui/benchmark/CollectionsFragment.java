package com.example.task2.ui.benchmark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task2.R;
import com.example.task2.models.RecyclerData;

import java.util.Arrays;
import java.util.Collection;

public class CollectionsFragment extends Fragment {

    private final RecyclerViewAdapter adapter = new RecyclerViewAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter.setItems(createItemsList());
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_collections, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.rvCollections);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(adapter);
    }


    private Collection<RecyclerData> createItemsList() {
        return Arrays.asList(
                new RecyclerData(R.string.adding_in_the_beginning, R.string.arraylist),
                new RecyclerData(R.string.adding_in_the_beginning, R.string.linkedlist),
                new RecyclerData(R.string.adding_in_the_beginning, R.string.copyonwritearraylist),
                new RecyclerData(R.string.adding_in_the_middle, R.string.arraylist),
                new RecyclerData(R.string.adding_in_the_middle, R.string.linkedlist),
                new RecyclerData(R.string.adding_in_the_middle, R.string.copyonwritearraylist),
                new RecyclerData(R.string.adding_in_the_end, R.string.arraylist),
                new RecyclerData(R.string.adding_in_the_end, R.string.linkedlist),
                new RecyclerData(R.string.adding_in_the_end, R.string.copyonwritearraylist),
                new RecyclerData(R.string.search_by_value, R.string.arraylist),
                new RecyclerData(R.string.search_by_value, R.string.linkedlist),
                new RecyclerData(R.string.search_by_value, R.string.copyonwritearraylist),
                new RecyclerData(R.string.removing_in_the_beginning, R.string.arraylist),
                new RecyclerData(R.string.removing_in_the_beginning, R.string.linkedlist),
                new RecyclerData(R.string.removing_in_the_beginning, R.string.copyonwritearraylist),
                new RecyclerData(R.string.removing_in_the_middle, R.string.arraylist),
                new RecyclerData(R.string.removing_in_the_middle, R.string.linkedlist),
                new RecyclerData(R.string.removing_in_the_middle, R.string.copyonwritearraylist),
                new RecyclerData(R.string.removing_in_the_end, R.string.arraylist),
                new RecyclerData(R.string.removing_in_the_end, R.string.linkedlist),
                new RecyclerData(R.string.removing_in_the_end, R.string.copyonwritearraylist)
//                new RecyclerData(getResources().getString(R.string.adding_in_the_beginning_arraylist)),
//                new RecyclerData(getResources().getString(R.string.adding_in_the_beginning_arraylist)),
//                new RecyclerData(getResources().getString(R.string.adding_in_the_beginning_linkedlist)),
//                new RecyclerData(getResources().getString(R.string.adding_in_the_beginning_copyonwritearraylist)),
//                new RecyclerData(getResources().getString(R.string.adding_in_the_middle_arraylist)),
//                new RecyclerData(getResources().getString(R.string.adding_in_the_middle_linkedlist)),
//                new RecyclerData(getResources().getString(R.string.adding_in_the_middle_copyonwritearraylist)),
//                new RecyclerData(getResources().getString(R.string.adding_in_the_end_arraylist)),
//                new RecyclerData(getResources().getString(R.string.adding_in_the_end_linkedlist)),
//                new RecyclerData(getResources().getString(R.string.adding_in_the_end_copyonwritearraylist)),
//                new RecyclerData(getResources().getString(R.string.search_by_value_arraylist)),
//                new RecyclerData(getResources().getString(R.string.search_by_value_linkedlist)),
//                new RecyclerData(getResources().getString(R.string.search_by_value_copyonwritearraylist)),
//                new RecyclerData(getResources().getString(R.string.removing_in_the_beginning_arraylist)),
//                new RecyclerData(getResources().getString(R.string.removing_in_the_beginning_linkedlist)),
//                new RecyclerData(getResources().getString(R.string.removing_in_the_beginning_copyonwritearraylist)),
//                new RecyclerData(getResources().getString(R.string.removing_in_the_middle_arraylist)),
//                new RecyclerData(getResources().getString(R.string.removing_in_the_middle_linkedlist)),
//                new RecyclerData(getResources().getString(R.string.removing_in_the_middle_copyonwritearraylist)),
//                new RecyclerData(getResources().getString(R.string.removing_in_the_end_arraylist)),
//                new RecyclerData(getResources().getString(R.string.removing_in_the_end_linkedlist)),
//                new RecyclerData(getResources().getString(R.string.removing_in_the_end_copyonwritearraylist))
        );
    }

//    private void datainitialize() {
//
//        newsArrayList = new ArrayList<>();
//        newsArrayList.add(new RecyclerData(getResources().getString(R.string.adding_in_the_beginning_arraylist)));
//        newsArrayList.add(new RecyclerData(getResources().getString(R.string.adding_in_the_beginning_linkedlist)));
//        newsArrayList.add(new RecyclerData(getResources().getString(R.string.adding_in_the_beginning_copyonwritearraylist)));
//        newsArrayList.add(new RecyclerData(getResources().getString(R.string.adding_in_the_middle_arraylist)));
//        newsArrayList.add(new RecyclerData(getResources().getString(R.string.adding_in_the_middle_linkedlist)));
//        newsArrayList.add(new RecyclerData(getResources().getString(R.string.adding_in_the_middle_copyonwritearraylist)));
//        newsArrayList.add(new RecyclerData(getResources().getString(R.string.adding_in_the_end_arraylist)));
//        newsArrayList.add(new RecyclerData(getResources().getString(R.string.adding_in_the_end_linkedlist)));
//        newsArrayList.add(new RecyclerData(getResources().getString(R.string.adding_in_the_end_copyonwritearraylist)));
//        newsArrayList.add(new RecyclerData(getResources().getString(R.string.search_by_value_arraylist)));
//        newsArrayList.add(new RecyclerData(getResources().getString(R.string.search_by_value_linkedlist)));
//        newsArrayList.add(new RecyclerData(getResources().getString(R.string.search_by_value_copyonwritearraylist)));
//        newsArrayList.add(new RecyclerData(getResources().getString(R.string.removing_in_the_beginning_arraylist)));
//        newsArrayList.add(new RecyclerData(getResources().getString(R.string.removing_in_the_beginning_linkedlist)));
//        newsArrayList.add(new RecyclerData(getResources().getString(R.string.removing_in_the_beginning_copyonwritearraylist)));
//        newsArrayList.add(new RecyclerData(getResources().getString(R.string.removing_in_the_middle_arraylist)));
//        newsArrayList.add(new RecyclerData(getResources().getString(R.string.removing_in_the_middle_linkedlist)));
//        newsArrayList.add(new RecyclerData(getResources().getString(R.string.removing_in_the_middle_copyonwritearraylist)));
//        newsArrayList.add(new RecyclerData(getResources().getString(R.string.removing_in_the_end_arraylist)));
//        newsArrayList.add(new RecyclerData(getResources().getString(R.string.removing_in_the_end_linkedlist)));
//        newsArrayList.add(new RecyclerData(getResources().getString(R.string.removing_in_the_end_copyonwritearraylist)));
//
//    }
}