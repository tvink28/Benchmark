package com.example.task2.ui.benchmark;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task2.R;
import com.example.task2.models.CellOperation;
import com.google.android.material.textfield.TextInputEditText;

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

        View view = inflater.inflate(R.layout.fragment_collections, container, false);

        TextInputEditText textInputEditText = view.findViewById(R.id.editTextCollections);
        Button buttonStopStart = view.findViewById(R.id.btnStopStartCollections);
        View errorView = inflater.inflate(R.layout.error, null);
        PopupWindow errorPopup = new PopupWindow(errorView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        errorPopup.setFocusable(true);
        textInputEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    textInputEditText.setBackgroundResource(R.drawable.input_bg2);
                }
            }
        });
        buttonStopStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = textInputEditText.getText().toString().trim();
                TextView errorText = errorView.findViewById(R.id.errorText);
                int x = textInputEditText.getWidth() / 2 - errorView.getWidth() / 2;
                int y = 30;


                if (TextUtils.isEmpty(input)) {
                    errorPopup.showAsDropDown(textInputEditText, x, y);
                    errorText.setText(R.string.error_empty);
                    textInputEditText.setBackgroundResource(R.drawable.input_bg_error);
                } else {
                    try {
                        int number = Integer.parseInt(input);
                        if (number < 1000000 || number > 10000000) {
                            errorPopup.showAsDropDown(textInputEditText, x, y);
                            errorText.setText(R.string.error_count);
                            textInputEditText.setBackgroundResource(R.drawable.input_bg_error);

                        } else {
                            errorPopup.dismiss();
                            textInputEditText.setBackgroundResource(R.drawable.input_bg2);
                        }
                    } catch (NumberFormatException e) {
                        errorPopup.showAsDropDown(textInputEditText, x, y);
                        errorText.setText(R.string.error_valid);
                        textInputEditText.setBackgroundResource(R.drawable.input_bg_error);

                    }
                }
            }
        });

        return view;
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