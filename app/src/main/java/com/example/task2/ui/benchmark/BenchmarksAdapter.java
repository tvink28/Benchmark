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

import java.util.HashSet;

public class BenchmarksAdapter extends ListAdapter<CellOperation, BenchmarksAdapter.BenchmarkViewHolder> {

    private final HashSet<Integer> positionsInProgress = new HashSet<>();

    private int columnWidth;

    public BenchmarksAdapter() {
        super(new DiffUtil.ItemCallback<CellOperation>() {
            @Override
            public boolean areItemsTheSame(@NonNull CellOperation oldItem, @NonNull CellOperation newItem) {
                return oldItem.action == newItem.action && oldItem.type == newItem.type;
            }

            @Override
            public boolean areContentsTheSame(@NonNull CellOperation oldItem, @NonNull CellOperation newItem) {
                return oldItem.action == newItem.action && oldItem.type == newItem.type && oldItem.time == newItem.time;
            }
        });
    }

    public void setColumnWidth(int columnWidth) {
        this.columnWidth = columnWidth;
        notifyDataSetChanged();
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
        boolean showProgressBar = positionsInProgress.contains(position);
        holder.bind(getItem(position), columnWidth, showProgressBar);
    }

    public void showProgressBar(int position) {
        positionsInProgress.add(position);
        notifyItemChanged(position);
    }

    public void hideProgressBar(int position) {
        positionsInProgress.remove(position);
        notifyItemChanged(position);
    }

    public void hideAllProgressBars() {
        HashSet<Integer> copy = new HashSet<>(positionsInProgress);
        for (Integer position : copy) {
            hideProgressBar(position);
        }
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

        public void bind(CellOperation cellOperation, int columnWidth, boolean showProgressBar) {
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

            int margin = itemView.getContext().getResources().getDimensionPixelSize(R.dimen.card_margin);
            int height = columnWidth - 2 * margin;

            ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
            layoutParams.height = height;
            itemView.setLayoutParams(layoutParams);

            if (showProgressBar) {
                progressBar.setVisibility(View.VISIBLE);
                backgroundView.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
                backgroundView.setVisibility(View.GONE);
            }
        }
    }
}
