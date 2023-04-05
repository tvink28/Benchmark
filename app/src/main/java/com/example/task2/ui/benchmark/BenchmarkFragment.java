package com.example.task2.ui.benchmark;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.google.android.material.textfield.TextInputEditText;

public abstract class BenchmarkFragment extends Fragment implements View.OnFocusChangeListener, View.OnClickListener {
    Handler handler = new Handler(Looper.getMainLooper());
    protected final BenchmarksAdapter adapter = new BenchmarksAdapter();

    protected TextInputEditText textInputEditText;
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
        RecyclerView recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(adapter);

        textInputEditText = view.findViewById(R.id.editText);
        Button buttonStopStart = view.findViewById(R.id.btnStopStart);
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
        final TextView errorText = getErrorPopup().getContentView().findViewById(R.id.errorText);
        final int x = textInputEditText.getWidth() / 2 - getErrorPopup().getWidth() / 2;
        final int Y = 30;
        final int number;

        if (TextUtils.isEmpty(input)) {
            showError(errorText, x, Y, R.string.error_empty, R.drawable.input_bg_error);
        } else {
            try {
                number = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                showError(errorText, x, Y, R.string.error_valid, R.drawable.input_bg_error);
                return;
            }

            if (number < 1) {
                showError(errorText, x, Y, R.string.error_count, R.drawable.input_bg_error);
            } else {
                runBenchmark(number);
                errorPopup.dismiss();
                textInputEditText.setBackgroundResource(R.drawable.input_bg2);
            }
        }
    }

    private PopupWindow getErrorPopup() {
        if (errorPopup == null) {
            View errorView = LayoutInflater.from(getContext()).inflate(R.layout.view_error, null);
            errorPopup = new PopupWindow(errorView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            errorPopup.setFocusable(true);
        }
        return errorPopup;
    }

    private void showError(TextView errorText, int x, int y, int errorMessage, int inputBg) {
        errorPopup.showAsDropDown(textInputEditText, x, y);
        errorText.setText(errorMessage);
        textInputEditText.setBackgroundResource(inputBg);
    }

    protected abstract void runBenchmark(int number);

}
