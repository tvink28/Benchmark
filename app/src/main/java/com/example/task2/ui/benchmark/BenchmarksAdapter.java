package com.example.task2.ui.benchmark;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task2.R;
import com.example.task2.models.CellOperation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BenchmarksAdapter extends RecyclerView.Adapter<BenchmarksAdapter.BenchmarkViewHolder> {

    private final List<CellOperation> items = new ArrayList<>();

    public BenchmarksAdapter() {
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
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(Collection<CellOperation> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }


    public static class BenchmarkViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewAction;
//        private final TextView textViewType;

        public BenchmarkViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAction = itemView.findViewById(R.id.item_text_action);
        }

        public void bind(CellOperation cellOperation) {
            final String action = itemView.getResources().getString(cellOperation.action);
            final String type = itemView.getResources().getString(cellOperation.type);
            final String time = String.valueOf(cellOperation.time);

            textViewAction.setText(String.format("%s\n%s\n%s ns", action, type, time));
        }
    }
}
