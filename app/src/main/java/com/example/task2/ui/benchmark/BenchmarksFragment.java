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
import com.example.task2.models.Benchmark;
import com.example.task2.models.CellOperation;
import com.example.task2.models.CollectionBenchmark;
import com.example.task2.models.MapBenchmark;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class BenchmarksFragment extends Fragment implements View.OnFocusChangeListener, TextWatcher, CompoundButton.OnCheckedChangeListener {
    private final BenchmarksAdapter adapter = new BenchmarksAdapter();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Benchmark benchmark;
    private TextInputEditText textInputEditText;
    private ToggleButton buttonStopStart;
    private PopupWindow errorPopup;
    private ExecutorService executorService;


    public static BenchmarksFragment newInstance(int benchmarkType) {
        Bundle args = new Bundle();
        args.putInt("benchmarkType", benchmarkType);
        BenchmarksFragment fragment = new BenchmarksFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        int benchmarkType = args.getInt("benchmarkType");

        if (benchmarkType == 0) {
            benchmark = new CollectionBenchmark();
        } else {
            benchmark = new MapBenchmark();
        }
        adapter.submitList(benchmark.createItemsList(false));
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
                getActivity(), benchmark.getNumberOfColumns()
        );
        recyclerView.setLayoutManager(layoutManager);
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
        int number;

        try {
            number = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            buttonStopStart.setEnabled(false);
            showError(R.string.error_valid, R.drawable.input_bg_error);
            return;
        }

        if (number < 1) {
            showError(R.string.error_count, R.drawable.input_bg_error);
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
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
            executorService = null;

            final List<CellOperation> list = new ArrayList<>(adapter.getCurrentList());
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isRunning) {
                    list.set(i, list.get(i).withIsRunning(false));
                }
            }
            adapter.submitList(list);
        } else {
            final int number = Integer.parseInt(textInputEditText.getText().toString().trim());
            runBenchmark(number);
        }
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

    private void runBenchmark(int number) {
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        final List<CellOperation> operations = benchmark.createItemsList(true);
        adapter.submitList(new ArrayList<>(operations));

        final AtomicInteger completedTasks = new AtomicInteger(0);
        for (int position = 0; position < operations.size(); position++) {
            final int pos = position;
            executorService.submit(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final CellOperation cell = operations.get(pos);
                final long operationTime = benchmark.measureTime(cell, number);

                final CellOperation update = cell.withTime(Math.toIntExact(operationTime));
                handler.post(() -> updateCell(pos, update, operations));
                if (completedTasks.incrementAndGet() == operations.size()) {
                    handler.post(() -> {
                        buttonStopStart.setOnCheckedChangeListener(null);
                        buttonStopStart.setChecked(true);
                        buttonStopStart.setOnCheckedChangeListener(BenchmarksFragment.this);
                    });
                }
            });
        }
        executorService.shutdown();
    }

    private void updateCell(int position, CellOperation cell, List<CellOperation> operations) {
        Log.d("LOGG:", "Update on position: " + position);
        operations.set(position, cell);
        adapter.submitList(new ArrayList<>(operations));
    }
}
