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
import com.example.task2.models.CellOperation;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class BenchmarkFragment extends Fragment implements View.OnFocusChangeListener, View.OnClickListener, TextWatcher {
    protected final BenchmarksAdapter adapter = new BenchmarksAdapter();
    protected Handler handler = new Handler(Looper.getMainLooper());
    protected TextInputEditText textInputEditText;
    protected Button buttonStopStart;
    protected PopupWindow errorPopup;
    private int number = 0;
    private ExecutorService executorService;
    private List<CellOperation> operations;
    private boolean isBenchmarkRunning = false;

    protected abstract List<CellOperation> createItemsList();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        operations = createItemsList();
        adapter.submitList(operations);
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
        buttonStopStart = view.findViewById(R.id.btnStopStart);
        textInputEditText.setOnFocusChangeListener(this);
        buttonStopStart.setOnClickListener(this);
        textInputEditText.addTextChangedListener(this);
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
//        final int number;

        try {
            number = Integer.parseInt(input);
        } catch (NumberFormatException e) {
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
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void onClick(View v) {
        final String input = textInputEditText.getText().toString().trim();
        if (input.isEmpty()) {
            return;
        }
//        int number;
//
//        try {
//            number = Integer.parseInt(input);
//        } catch (NumberFormatException e) {
//            return;
//        }

        if (isBenchmarkRunning) {
            // Stop benchmark
            isBenchmarkRunning = false;
            buttonStopStart.setText(R.string.start);
            buttonStopStart.setBackgroundResource(R.drawable.button_bg_start);
            if (executorService != null) {
                executorService.shutdownNow();
            }
        } else {
            // Start benchmark
            isBenchmarkRunning = true;
            buttonStopStart.setText(R.string.stop);
            buttonStopStart.setBackgroundResource(R.drawable.button_bg_stop);

            runBenchmark(number);

        }

    }

    private void showError(View errorView, PopupWindow errorPopup, TextView errorText, int y, int errorMessage, int inputBg) {
        errorText.setText(errorMessage);
        textInputEditText.setBackgroundResource(inputBg);

        errorPopup.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        errorPopup.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        errorPopup.setContentView(errorView);

        errorView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int errorPopupWidth = errorView.getMeasuredWidth();

        int x = textInputEditText.getWidth() / 2 - errorPopupWidth / 2;

        errorPopup.showAsDropDown(textInputEditText, x, y);
    }


    private void runBenchmark(int number) {
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int position = 0; position < operations.size(); position++) {
            final int pos = position;
            executorService.submit(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isBenchmarkRunning) {
                    CellOperation operation = measureTime(operations.get(pos), number);
                    updateCell(pos, Math.toIntExact(operation.time));
                }
                Log.d("CollectionsFragment", "Thread ID: " + Thread.currentThread().getId() + ", Position: " + pos);

                if (pos == operations.size() - 1) {
                    isBenchmarkRunning = false;
                    handler.post(() -> {
                        buttonStopStart.setText(R.string.start);
                        buttonStopStart.setBackgroundResource(R.drawable.button_bg_start);
                    });
                }
            });
        }
    }

    private void updateCell(int position, int result) {
        CellOperation cellOperation = operations.get(position);
        CellOperation updatedCellOperation = cellOperation.withTime(result);
        operations.set(position, updatedCellOperation);
        handler.post(() -> adapter.notifyItemChanged(position));
    }


    CellOperation measureTime(CellOperation item, int number) {
        BenchmarkManager bm = new BenchmarkManager();

        List list;
        if (item.type == R.string.arraylist) {
            list = new ArrayList<>(Collections.nCopies(number, "test"));
        } else if (item.type == R.string.linkedlist) {
            list = new LinkedList<>(Collections.nCopies(number, "test"));
        } else {
            list = new CopyOnWriteArrayList<>(Collections.nCopies(number, "test"));
        }

        long result;
        if (item.action == R.string.adding_in_the_beginning) {
            result = bm.addStart(list);
        } else if (item.action == R.string.adding_in_the_middle) {
            result = bm.addMiddle(list);
        } else {
            result = bm.addEnd(list);
        }


        return item.withTime(result);
    }
}
