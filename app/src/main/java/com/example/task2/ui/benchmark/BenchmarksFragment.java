package com.example.task2.ui.benchmark;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task2.R;
import com.example.task2.models.CellOperation;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class BenchmarksFragment extends Fragment implements View.OnFocusChangeListener, TextWatcher, CompoundButton.OnCheckedChangeListener {
    private final BenchmarksAdapter adapter = new BenchmarksAdapter();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private TextInputEditText textInputEditText;
    private ToggleButton buttonStopStart;
    private PopupWindow errorPopup;
    private ExecutorService executorService;
    private boolean isBenchmarkRunning = false;

    protected abstract List<CellOperation> createItemsList()
    protected abstract CellOperation measureTime(CellOperation item, int number);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter.submitList(createItemsList());
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
        RecyclerView recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(adapter);

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
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        final String input = s.toString().trim();
        int number;

        try {
            number = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            buttonStopStart.setEnabled(false);
            return;
        }

        if (number < 1) {
            final View errorView = LayoutInflater.from(getContext()).inflate(R.layout.view_error, null);
            final TextView errorText = errorView.findViewById(R.id.errorText);
            if (errorPopup == null) {
                errorPopup = new PopupWindow(errorView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            final int Y = 30;

            showError(errorView, errorPopup, errorText, Y, R.string.error_count, R.drawable.input_bg_error);
        } else {
            textInputEditText.setBackgroundResource(R.drawable.input_bg2);
            if (errorPopup != null) {
                errorPopup.dismiss();
            }
            buttonStopStart.setEnabled(true);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        final String input = textInputEditText.getText().toString().trim();
        int number;

        if (input.isEmpty()) {
            return;
        }

        try {
            number = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return;
        }

        if (isChecked) {
            isBenchmarkRunning = false;
            if (executorService != null) {
                executorService.shutdownNow();
            }
        } else {
            isBenchmarkRunning = true;
            List<CellOperation> operations = createItemsList();
            adapter.submitList(operations);
            runBenchmark(number, operations);
        }
    }

    private void showError(View errorView, PopupWindow errorPopup, TextView errorText, int y, int errorMessage, int inputBg) {
        errorText.setText(errorMessage);
        textInputEditText.setBackgroundResource(inputBg);

        errorPopup.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        errorPopup.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        errorPopup.setContentView(errorView);

        errorView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        final int errorPopupWidth = errorView.getMeasuredWidth();
        final int x = textInputEditText.getWidth() / 2 - errorPopupWidth / 2;

        errorPopup.showAsDropDown(textInputEditText, x, y);
    }


    private void runBenchmark(int number, List<CellOperation> operations) {
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        List<CellOperation> operationsInProgress = new ArrayList<>(operations);

        AtomicInteger completedTasks = new AtomicInteger(0);

        for (int position = 0; position < operations.size(); position++) {
            final int pos = position;
            executorService.submit(() -> {
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                if (isBenchmarkRunning) {
                    CellOperation operation = measureTime(operations.get(pos), number);
                    updateCell(pos, Math.toIntExact(operation.time), operationsInProgress);
                }
                Log.d("CollectionsFragment", "Thread ID: " + Thread.currentThread().getId() + ", Position: " + pos);

                if (completedTasks.incrementAndGet() == operations.size()) {
                    isBenchmarkRunning = false;
                    handler.post(() ->
                            buttonStopStart.setChecked(true));
                }
            });
        }
        executorService.shutdown();
    }

    private void updateCell(int position, int result, List<CellOperation> operationsInProgress) {
        CellOperation cellOperation = operationsInProgress.get(position);
        CellOperation updatedCellOperation = cellOperation.withTime(result);
        operationsInProgress.set(position, updatedCellOperation);

        List<CellOperation> updatedList = new ArrayList<>(operationsInProgress);
        handler.post(() -> adapter.submitList(updatedList));
    }
}
