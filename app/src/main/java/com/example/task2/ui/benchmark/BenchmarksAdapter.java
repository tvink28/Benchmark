package com.example.task2.ui.benchmark;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
                return oldItem.action == newItem.action && oldItem.type == newItem.type && oldItem.time == newItem.time && oldItem.isRunning == newItem.isRunning;
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

            if (cellOperation.isRunning) {
                fadeIn(progressBar);
                fadeIn(backgroundView);
            } else {
                fadeOut(progressBar);
                fadeOut(backgroundView);
            }


            String timeText;
            if (time == R.string.na) {
                timeText = itemView.getContext().getString(R.string.na);
            } else {
                timeText = String.valueOf(time);
            }

            textViewAction.setText(String.format("%s\n%s\n%s ns", action, type, timeText));

        }

        private void fadeIn(final View view) {
            view.setAlpha(0);
            view.setVisibility(View.VISIBLE);
            view.animate()
                    .alpha(1)
                    .setDuration(300)
                    .setListener(null);
        }

        private void fadeOut(final View view) {
            view.animate()
                    .alpha(0)
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            view.setVisibility(View.GONE);
                        }
                    });
        }
    }
}
