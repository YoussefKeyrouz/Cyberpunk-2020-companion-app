package com.y2thez.cyberpad.fragments


import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.y2thez.cyberpad.R
import kotlinx.android.synthetic.main.fragment_info.*

/**
 * Created by Y on 4/7/2018.
 */
class InfoDialogFragment : DialogFragment() {

    private var infoText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        if (args != null) {
            infoText = args.getString(ARG_INFO_TEXT)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dismissButton.setOnClickListener({
            this.dismiss()
        })
        infoTextView.text = infoText
    }

    override fun onStart() {
        super.onStart()
        dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window.setBackgroundDrawableResource(R.color.transparent)
    }

    companion object {
        private const val ARG_INFO_TEXT = "ARG_INFO_TEXT"

        fun newInstance(infoText: String): InfoDialogFragment {
            val fragment = InfoDialogFragment()
            val args = Bundle()
            args.putString(ARG_INFO_TEXT, infoText)
            fragment.arguments = args
            return fragment
        }
    }

}