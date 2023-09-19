package com.example.task2.ui.benchmark

import android.app.Dialog
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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task2.R
import com.example.task2.ui.SpacesItemDecoration
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class BenchmarksFragment : Fragment(), OnFocusChangeListener, TextWatcher,
        CompoundButton.OnCheckedChangeListener, View.OnLongClickListener {

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

        view.findViewById<TextView>(R.id.textView)?.setOnLongClickListener(this)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv)
        recyclerView.layoutManager = GridLayoutManager(activity, viewModel.getNumberOfColumns())
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(SpacesItemDecoration(viewModel.getNumberOfColumns(), resources.getDimensionPixelSize(R.dimen.card_margin)))

        viewModel.getCellOperationsLiveData().observe(viewLifecycleOwner) { adapter.submitList(it) }
        viewModel.getAllTasksCompletedLiveData().observe(viewLifecycleOwner) { allTasksCompleted ->
            buttonStopStart.setOnCheckedChangeListener(null)
            buttonStopStart.isChecked = allTasksCompleted
            buttonStopStart.setOnCheckedChangeListener(this)
        }
        viewModel.getValidNumberLiveData().observe(viewLifecycleOwner) { errorMessage: Int? ->
            if (errorMessage == null) {
                buttonStopStart.isEnabled = true
                textInputEditText.setBackgroundResource(R.drawable.input_bg2)
                if (errorPopup != null) {
                    errorPopup?.dismiss()
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
        lateinit var errorView: View
        lateinit var errorText: TextView
        if (errorPopup == null) {
            errorView =
                    LayoutInflater.from(context).inflate(R.layout.view_error, view as ViewGroup?, false)
            errorText = errorView.findViewById(R.id.errorText)
            errorPopup = PopupWindow(
                    errorView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            )
        } else {
            errorPopup?.let {
                errorView = it.contentView
                errorText = it.contentView.findViewById(R.id.errorText)
            }
        }

        errorText.setText(errorMessage)
        textInputEditText.setBackgroundResource(inputBg)

        errorPopup?.apply {
            width = ViewGroup.LayoutParams.WRAP_CONTENT
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            contentView = errorView

            val y = 30
            errorView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            val errorPopupWidth = errorView.measuredWidth
            val x = textInputEditText.width / 2 - errorPopupWidth / 2
            showAsDropDown(textInputEditText, x, y)
        }
    }

    override fun onLongClick(p0: View?): Boolean {
        lifecycleScope.launch {
            val last21Result = viewModel.getLast21Results().reversed()
            val textToShow = StringBuilder()
            for ((index, result) in last21Result.withIndex()) {
                val action = result.action.replace("\n", "")
                if (index == 0) {
                    textToShow.append("Input: ${result.input}\n")
                }
                textToShow.append("$action ${result.type} ${result.time} n/a\n")
            }

            val inflater = LayoutInflater.from(requireContext())
            val layout = inflater.inflate(R.layout.toast_bd, null)

            val customToastText = layout.findViewById<TextView>(R.id.customToastText)
            customToastText.text = textToShow.toString()

            val dialog = Dialog(requireContext())
            dialog.setContentView(layout)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)


            val displayMetrics = context?.resources?.displayMetrics
            val width = (displayMetrics?.widthPixels?.times(0.9))?.toInt()
            width?.let {
                dialog.window?.setLayout(
                        it,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
            dialog.show()
        }
        return true
    }
}
