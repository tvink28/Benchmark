package com.example.task2.ui.benchmark

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.task2.R
import com.example.task2.models.benchmarks.CellOperation
import com.example.task2.ui.benchmark.BenchmarksAdapter.BenchmarkViewHolder

class BenchmarksAdapter : ListAdapter<CellOperation?, BenchmarkViewHolder?>(
        object : DiffUtil.ItemCallback<CellOperation?>() {
            override fun areItemsTheSame(
                oldItem: CellOperation, newItem: CellOperation
            ) = oldItem.action == newItem.action && oldItem.type == newItem.type

            override fun areContentsTheSame(
                oldItem: CellOperation, newItem: CellOperation
            ) = oldItem == newItem
        }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BenchmarkViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_benchmark, parent, false)
        return BenchmarkViewHolder(v)
    }

    override fun onBindViewHolder(holder: BenchmarkViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class BenchmarkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val backgroundView: View = itemView.findViewById(R.id.backgroundView)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        private val textViewAction: TextView = itemView.findViewById(R.id.item_text_action)

        fun bind(cellOperation: CellOperation?) {
            val action = itemView.resources.getString(cellOperation!!.action)
            val type = itemView.resources.getString(cellOperation.type)
            val time = cellOperation.time

            val timeText = if (time == R.string.na.toLong()) {
                itemView.context.getString(R.string.na)
            } else {
                time.toString()
            }

            textViewAction.text = itemView.context.getString(R.string.benchmark_text, action, type, timeText)

            if (cellOperation.isRunning != (progressBar.alpha != 0F)) {
                setVisibility(progressBar, cellOperation.isRunning)
                setVisibility(backgroundView, cellOperation.isRunning)
            }
        }

        private fun setVisibility(view: View, isVisible: Boolean) {
            view.animate()
                    .alpha(if (isVisible) 1F else 0F)
                    .duration = 500
        }
    }
}
