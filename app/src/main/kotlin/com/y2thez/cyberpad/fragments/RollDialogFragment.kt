package com.y2thez.cyberpad.fragments

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.y2thez.cyberpad.R
import com.y2thez.cyberpad.utilities.IOUtilities
import com.y2thez.cyberpad.utilities.getBitmap
import kotlinx.android.synthetic.main.fragment_roll.*
import org.jetbrains.anko.textColor


/**
 * Created by Y on 4/8/2018.
 */


class RollDialogFragment : DialogFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_roll, container)
    }

    override fun onStart() {
        super.onStart()
        dialog.window.setLayout(MATCH_PARENT, WRAP_CONTENT)
        dialog.window.setBackgroundDrawableResource(R.color.transparent)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        val res = args?.getInt("rollResult") ?: 0
        val skillName = args?.getString("skillName")
        val roll = args?.getString("rollString")
        val skill = args?.getString("skillString")
        val ability = args?.getString("abilityString")
        val fumble = args?.getString("fumbleString")
        val criticlaSuccess = args?.getString("criticlaSuccess")
        roll_result.setOnClickListener({
            this.dismiss()
        })
        share_button.setOnClickListener {
            share()
        }
        roll_result.text = res.toString()
        skill_name.text = skillName
        val context = activity ?: return
        if(fumble != null){
            ContextCompat.getDrawable(context, R.drawable.roll_bg_red)?.let {  roll_result.background = it}
            ContextCompat.getColor(context, R.color.red).let {
                roll_result.textColor = it
                fumble_string.textColor = it
            }
            fumble_string.text = fumble
            detailsBox.visibility = View.GONE

            return
        }

        if(criticlaSuccess != null) {
            ContextCompat.getDrawable(context, R.drawable.roll_bg_green)?.let {  roll_result.background = it}
            ContextCompat.getColor(context, R.color.green).let {
                roll_result.textColor = it
                fumble_string.textColor = it
            }
            fumble_string.text = criticlaSuccess
        } else {
            fumble_string.visibility = View.INVISIBLE

        }

        if(roll == null) {
            roll_string.visibility = View.GONE
        } else {
            roll_string.text = roll
        }

        if(skill == null) {
            skill_string.visibility = View.GONE
        } else {
            skill_string.text = skill
        }

        if(ability == null) {
            ability_string.visibility = View.GONE
        } else {
            ability_string.text = ability
        }


    }

    fun share() {

        context?.let {
            IOUtilities.shareBitmapToWhatsapp(it,roll_layout.getBitmap())
        }
    }

    companion object {

        fun newInstance(skillName: String, rollResult: Int, rollString: String, skillString: String?, abilityString: String?, fumbleString: String?, criticlaSuccess: String?): RollDialogFragment {
            val frag = RollDialogFragment()
            val args = Bundle()
            args.putInt("rollResult", rollResult)
            args.putString("skillName", skillName)
            args.putString("skillString", skillString)
            args.putString("rollString", rollString)
            args.putString("abilityString", abilityString)
            args.putString("fumbleString", fumbleString)
            args.putString("criticlaSuccess", criticlaSuccess)
            frag.arguments = args
            return frag
        }
    }
}