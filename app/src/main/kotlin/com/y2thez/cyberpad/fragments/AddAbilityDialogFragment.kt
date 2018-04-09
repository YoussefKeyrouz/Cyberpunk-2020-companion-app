package com.y2thez.cyberpad.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.y2thez.cyberpad.R
import com.y2thez.cyberpad.data.SkillsManager
import kotlinx.android.synthetic.main.fragment_add_ability.*

/**
 * Created by Y on 4/7/2018.
 */
class AddAbilityDialogFragment : DialogFragment() {

    private var mListener: OnAddListener? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_ability, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addButton.setOnClickListener({
            saveAndDismiss()
        })
    }

    private fun saveAndDismiss(){
        val id = radio_group.checkedRadioButtonId
        when(id) {
            R.id.radio_language -> {
                SkillsManager.addLanguage(nameText.text.toString())
            }
            R.id.radio_martial -> {
                SkillsManager.addMartialArt(nameText.text.toString())
            }
        }
        mListener?.onAdd()
        dismiss()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnAddListener) {
            mListener = context
        } else if (parentFragment  is OnAddListener){
            mListener = parentFragment as OnAddListener
        }
    }
    override fun onStart() {
        super.onStart()
        dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window.setBackgroundDrawableResource(R.color.transparent)
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnAddListener {
        fun onAdd()
    }
}
