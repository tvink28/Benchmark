package com.example.task2.ui.benchmark

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task2.R
import com.example.task2.ui.SpacesItemDecoration
import com.google.android.material.textfield.TextInputEditText

class BenchmarksFragment : Fragment(), OnFocusChangeListener, TextWatcher, CompoundButton.OnCheckedChangeListener {

    companion object {
        const val ARG_BENCHMARK_TYPE = "benchmarkType"

        fun newInstance(benchmarkType: Int): BenchmarksFragment {
            return BenchmarksFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_BENCHMARK_TYPE, benchmarkType)
                }
            }
        }
    }

    private val adapter = BenchmarksAdapter()
    private lateinit var textInputEditText: TextInputEditText
    private lateinit var buttonStopStart: ToggleButton
    private var errorPopup: PopupWindow? = null
    private lateinit var viewModel: BenchmarksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = BenchmarksViewModelFactory(requireArguments().getInt(ARG_BENCHMARK_TYPE))
        viewModel = ViewModelProvider(this, factory)[BenchmarksViewModel::class.java]
        viewModel.onCreate()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_benchmark, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv)
        recyclerView.layoutManager = GridLayoutManager(activity, viewModel.numberOfColumns)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(SpacesItemDecoration(viewModel.numberOfColumns, resources.getDimensionPixelSize(R.dimen.card_margin)))

        viewModel.getCellOperationsLiveData().observe(viewLifecycleOwner) { adapter.submitList(it) }
        viewModel.getAllTasksCompletedLiveData().observe(viewLifecycleOwner) { allTasksCompleted: Boolean? ->
            buttonStopStart.setOnCheckedChangeListener(null)
            buttonStopStart.isChecked = allTasksCompleted!!
            buttonStopStart.setOnCheckedChangeListener(this)
        }
        viewModel.getValidNumberLiveData().observe(viewLifecycleOwner) { errorMessage: Int? ->
            if (errorMessage == null) {
                buttonStopStart.isEnabled = true
                textInputEditText.setBackgroundResource(R.drawable.input_bg2)
                if (errorPopup != null) {
                    errorPopup!!.dismiss()
                }
            } else {
                buttonStopStart.isEnabled = false
                showError(errorMessage, R.drawable.input_bg_error)
            }
        }

        textInputEditText = view.findViewById(R.id.editText)
        textInputEditText.onFocusChangeListener = this
        textInputEditText.addTextChangedListener(this)

        buttonStopStart = view.findViewById(R.id.btnStopStart)
        buttonStopStart.setOnCheckedChangeListener(this)
        buttonStopStart.isEnabled = false
    }

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        if (!hasFocus) {
            textInputEditText.clearFocus()
            textInputEditText.setBackgroundResource(R.drawable.input_bg2)
            errorPopup?.dismiss()
        }
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        viewModel.validateNumber(s.toString().trim())
    }

    override fun afterTextChanged(s: Editable) {}

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        viewModel.onButtonClicked(textInputEditText.text.toString().trim())
    }

    private fun showError(errorMessage: Int, inputBg: Int) {
        val errorView: View
        val errorText: TextView
        if (errorPopup == null) {
            errorView = LayoutInflater.from(context).inflate(R.layout.view_error, view as ViewGroup?, false)
            errorText = errorView.findViewById(R.id.errorText)
            errorPopup = PopupWindow(errorView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        } else {
            errorView = errorPopup!!.contentView
            errorText = errorPopup!!.contentView.findViewById(R.id.errorText)
        }
        val y = 30

        errorText.setText(errorMessage)
        textInputEditText.setBackgroundResource(inputBg)

        errorPopup!!.width = ViewGroup.LayoutParams.WRAP_CONTENT
        errorPopup!!.height = ViewGroup.LayoutParams.WRAP_CONTENT
        errorPopup!!.contentView = errorView

        errorView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val errorPopupWidth = errorView.measuredWidth
        val x = textInputEditText.width / 2 - errorPopupWidth / 2
        errorPopup!!.showAsDropDown(textInputEditText, x, y)
    }
}