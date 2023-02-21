package com.example.task2.ui.benchmark;

import static com.example.task2.R.drawable.tab_drawable_selector_1;
import static com.example.task2.R.drawable.tab_indicator2_bg;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Slide;

import com.example.task2.R;
import com.example.task2.models.CellOperation;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class MapsFragment extends Fragment {

    private final CellAdapter adapter = new CellAdapter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter.setItems(createItemsList());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        TextInputEditText textInputEditText = view.findViewById(R.id.editTextMaps);
        Button buttonStopStart = view.findViewById(R.id.btnStopStartMaps);
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

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        RecyclerView recyclerView = view.findViewById(R.id.rvCollectionsMaps);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(adapter);

    }

    private Collection<CellOperation> createItemsList() {
        return Arrays.asList(
                new CellOperation(R.string.adding_new, R.string.treemap),
                new CellOperation(R.string.adding_new, R.string.hashmap),
                new CellOperation(R.string.search_by_key, R.string.treemap),
                new CellOperation(R.string.search_by_key, R.string.hashmap),
                new CellOperation(R.string.removing, R.string.treemap),
                new CellOperation(R.string.removing, R.string.hashmap)
        );
    }


}