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
import com.example.task2.models.CellOperation;

import java.util.Arrays;
import java.util.Collection;

public class CollectionsFragment extends Fragment {

    private final CellAdapter adapter = new CellAdapter();

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

    private Collection<CellOperation> createItemsList() {
        return Arrays.asList(
                new CellOperation(R.string.adding_in_the_beginning, R.string.arraylist),
                new CellOperation(R.string.adding_in_the_beginning, R.string.linkedlist),
                new CellOperation(R.string.adding_in_the_beginning, R.string.copyonwritearraylist),
                new CellOperation(R.string.adding_in_the_middle, R.string.arraylist),
                new CellOperation(R.string.adding_in_the_middle, R.string.linkedlist),
                new CellOperation(R.string.adding_in_the_middle, R.string.copyonwritearraylist),
                new CellOperation(R.string.adding_in_the_end, R.string.arraylist),
                new CellOperation(R.string.adding_in_the_end, R.string.linkedlist),
                new CellOperation(R.string.adding_in_the_end, R.string.copyonwritearraylist),
                new CellOperation(R.string.search_by_value, R.string.arraylist),
                new CellOperation(R.string.search_by_value, R.string.linkedlist),
                new CellOperation(R.string.search_by_value, R.string.copyonwritearraylist),
                new CellOperation(R.string.removing_in_the_beginning, R.string.arraylist),
                new CellOperation(R.string.removing_in_the_beginning, R.string.linkedlist),
                new CellOperation(R.string.removing_in_the_beginning, R.string.copyonwritearraylist),
                new CellOperation(R.string.removing_in_the_middle, R.string.arraylist),
                new CellOperation(R.string.removing_in_the_middle, R.string.linkedlist),
                new CellOperation(R.string.removing_in_the_middle, R.string.copyonwritearraylist),
                new CellOperation(R.string.removing_in_the_end, R.string.arraylist),
                new CellOperation(R.string.removing_in_the_end, R.string.linkedlist),
                new CellOperation(R.string.removing_in_the_end, R.string.copyonwritearraylist)
        );
    }
}