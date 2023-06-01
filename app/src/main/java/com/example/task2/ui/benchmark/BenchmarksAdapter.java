package com.example.task2.ui.benchmark;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task2.R;
import com.example.task2.models.CellOperation;

public class BenchmarksAdapter extends ListAdapter<CellOperation, BenchmarksAdapter.BenchmarkViewHolder> {

    public BenchmarksAdapter() {
        super(new DiffUtil.ItemCallback<CellOperation>() {
            @Override
            public boolean areItemsTheSame(@NonNull CellOperation oldItem, @NonNull CellOperation newItem) {
                return oldItem.action == newItem.action && oldItem.type == newItem.type;
            }

            @Override
            public boolean areContentsTheSame(@NonNull CellOperation oldItem, @NonNull CellOperation newItem) {
                return oldItem.equals(newItem);
            }
        });
    }

    @NonNull
    @Override
    public BenchmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_benchmark, parent, false
        );
        return new BenchmarkViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BenchmarkViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class BenchmarkViewHolder extends RecyclerView.ViewHolder {

        private final View backgroundView;
        private final ProgressBar progressBar;
        private final TextView textViewAction;

        public BenchmarkViewHolder(@NonNull View itemView) {
            super(itemView);
            backgroundView = itemView.findViewById(R.id.backgroundView);
            textViewAction = itemView.findViewById(R.id.item_text_action);
            progressBar = itemView.findViewById(R.id.progressBar);
        }

        public void bind(CellOperation cellOperation) {
            final String action = itemView.getResources().getString(cellOperation.action);
            final String type = itemView.getResources().getString(cellOperation.type);
            final long time = cellOperation.time;

            String timeText;
            if (time == R.string.na) {
                timeText = itemView.getContext().getString(R.string.na);
            } else {
                timeText = String.valueOf(time);
            }

            textViewAction.setText(String.format("%s\n%s\n%s ns", action, type, timeText));

            if (cellOperation.isRunning != (progressBar.getAlpha() != 0)) {
                setVisibility(progressBar, cellOperation.isRunning);
                setVisibility(backgroundView, cellOperation.isRunning);
            }
        }

        public void setVisibility(View view, boolean isVisible) {
            view.animate()
                    .alpha(isVisible ? 1 : 0)
                    .setDuration(500);
        }
    }
}
