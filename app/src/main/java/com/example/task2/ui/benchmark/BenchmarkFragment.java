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
import com.example.task2.models.BenchmarkManager;
import com.google.android.material.textfield.TextInputEditText;

public abstract class BenchmarkFragment extends Fragment implements View.OnFocusChangeListener, View.OnClickListener {

    protected BenchmarkManager benchmarkManager = new BenchmarkManager(new BenchmarkManager.CollectionProvider());

    protected final BenchmarksAdapter adapter = new BenchmarksAdapter();
    protected RecyclerView recyclerView;
    protected TextInputEditText textInputEditText;
    protected View errorView;
    protected PopupWindow errorPopup;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_benchmark, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(adapter);

        textInputEditText = view.findViewById(R.id.editText);
        Button buttonStopStart = view.findViewById(R.id.btnStopStart);
        errorView = LayoutInflater.from(getContext()).inflate(R.layout.view_error, null);
        errorPopup = new PopupWindow(errorView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        errorPopup.setFocusable(true);
        textInputEditText.setOnFocusChangeListener(this);
        buttonStopStart.setOnClickListener(this);
    }

    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            textInputEditText.clearFocus();
            textInputEditText.setBackgroundResource(R.drawable.input_bg2);
        }
    }


    public void onClick(View v) {
        final String input = textInputEditText.getText().toString().trim();
        final TextView errorText = errorView.findViewById(R.id.errorText);
        final int x = textInputEditText.getWidth() / 2 - errorView.getWidth() / 2;
        final int Y = 30;

        if (TextUtils.isEmpty(input)) {
            errorPopup.showAsDropDown(textInputEditText, x, Y);
            errorText.setText(R.string.error_empty);
            textInputEditText.setBackgroundResource(R.drawable.input_bg_error);
        } else {
            try {
                final int number = Integer.parseInt(input);
                if (number < 1) {
                    errorPopup.showAsDropDown(textInputEditText, x, Y);
                    errorText.setText(R.string.error_count);
                    textInputEditText.setBackgroundResource(R.drawable.input_bg_error);
                } else {
                    makeBenchmark(number);

                    errorPopup.dismiss();
                    textInputEditText.setBackgroundResource(R.drawable.input_bg2);
                }
            } catch (NumberFormatException e) {
                errorPopup.showAsDropDown(textInputEditText, x, Y);
                errorText.setText(R.string.error_valid);
                textInputEditText.setBackgroundResource(R.drawable.input_bg_error);
            }
        }
    }

    protected abstract void makeBenchmark(int number);

}
