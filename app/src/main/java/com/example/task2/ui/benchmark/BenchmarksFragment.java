package com.example.task2.ui.benchmark;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task2.R;
import com.google.android.material.textfield.TextInputEditText;

public class BenchmarksFragment extends Fragment implements View.OnFocusChangeListener, TextWatcher, CompoundButton.OnCheckedChangeListener {
    private static final String ARG_BENCHMARK_TYPE = "benchmarkType";
    private final BenchmarksAdapter adapter = new BenchmarksAdapter();
    private TextInputEditText textInputEditText;
    private ToggleButton buttonStopStart;
    private PopupWindow errorPopup;
    private BenchmarksViewModel viewModel;

    public static BenchmarksFragment newInstance(int benchmarkType) {
        Bundle args = new Bundle();
        args.putInt(ARG_BENCHMARK_TYPE, benchmarkType);
        BenchmarksFragment fragment = new BenchmarksFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        int benchmarkType = args.getInt(ARG_BENCHMARK_TYPE);

        BenchmarksViewModelFactory factory = new BenchmarksViewModelFactory(benchmarkType);
        viewModel = new ViewModelProvider(this, factory).get(BenchmarksViewModel.class);
        viewModel.onCreate();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_benchmark, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RecyclerView recyclerView = view.findViewById(R.id.rv);
        final GridLayoutManager layoutManager = new GridLayoutManager(
                getActivity(), viewModel.getNumberOfColumns()
        );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        viewModel.getCellOperationsLiveData().observe(getViewLifecycleOwner(), adapter::submitList);
        viewModel.getAllTasksCompletedLiveData().observe(getViewLifecycleOwner(), allTasksCompleted -> {
            buttonStopStart.setOnCheckedChangeListener(null);
            buttonStopStart.setChecked(allTasksCompleted);
            buttonStopStart.setOnCheckedChangeListener(this);
        });
        viewModel.getValidNumberLiveData().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage == null) {
                buttonStopStart.setEnabled(true);
                textInputEditText.setBackgroundResource(R.drawable.input_bg2);
                if (errorPopup != null) {
                    errorPopup.dismiss();
                }
            } else {
                buttonStopStart.setEnabled(false);
                showError(errorMessage, R.drawable.input_bg_error);
            }
        });

        textInputEditText = view.findViewById(R.id.editText);
        textInputEditText.setOnFocusChangeListener(this);
        textInputEditText.addTextChangedListener(this);

        buttonStopStart = view.findViewById(R.id.btnStopStart);
        buttonStopStart.setOnCheckedChangeListener(this);
        buttonStopStart.setEnabled(false);
    }

    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            textInputEditText.clearFocus();
            textInputEditText.setBackgroundResource(R.drawable.input_bg2);
            if (errorPopup != null) {
                errorPopup.dismiss();
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        final String input = s.toString().trim();

        viewModel.validateNumber(input);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        viewModel.onButtonClicked(textInputEditText.getText().toString().trim());
    }

    private void showError(int errorMessage, int inputBg) {
        final View errorView;
        final TextView errorText;
        if (errorPopup == null) {
            errorView = LayoutInflater.from(getContext()).inflate(R.layout.view_error, (ViewGroup) getView(), false);
            errorText = errorView.findViewById(R.id.errorText);
            errorPopup = new PopupWindow(errorView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            errorView = errorPopup.getContentView();
            errorText = errorPopup.getContentView().findViewById(R.id.errorText);
        }
        final int Y = 30;

        errorText.setText(errorMessage);
        textInputEditText.setBackgroundResource(inputBg);

        errorPopup.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        errorPopup.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        errorPopup.setContentView(errorView);

        errorView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        final int errorPopupWidth = errorView.getMeasuredWidth();
        final int x = textInputEditText.getWidth() / 2 - errorPopupWidth / 2;

        errorPopup.showAsDropDown(textInputEditText, x, Y);
    }
}
