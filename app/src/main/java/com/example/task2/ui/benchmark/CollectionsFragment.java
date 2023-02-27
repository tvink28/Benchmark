package com.example.task2.ui.benchmark;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task2.R;
import com.example.task2.models.CellOperation;

import java.util.Arrays;
import java.util.Collection;

public class CollectionsFragment extends BaseFragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter.setItems(createItemsList());
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {

        return inflater.inflate(R.layout.fragment_common, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        RecyclerView recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(adapter);

        textInputEditText = view.findViewById(R.id.editText);
        Button buttonStopStart = view.findViewById(R.id.btnStopStart);
        errorView = inflater.inflate(R.layout.error, null);
        errorPopup = new PopupWindow(errorView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        errorPopup.setFocusable(true);
        textInputEditText.setOnFocusChangeListener(this);
        buttonStopStart.setOnClickListener(this);

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