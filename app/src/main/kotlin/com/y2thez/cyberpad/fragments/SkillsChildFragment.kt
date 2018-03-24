package com.y2thez.cyberpad.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.y2thez.cyberpad.R
import com.y2thez.cyberpad.adapters.SkillsAdapter
import com.y2thez.cyberpad.data.DataHolder
import com.y2thez.cyberpad.data.Skill
import kotlinx.android.synthetic.main.fragment_skills_child.*
import org.jetbrains.anko.support.v4.toast


class SkillsChildFragment : Fragment(), SkillsAdapter.SkillsInteractionListener {


    private var mListener: SkillsFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_skills_child, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        switchButton.setOnClickListener{
            mListener?.onSwitchToAbilitiesClicked()
        }

        skillsRecyclerView.layoutManager = LinearLayoutManager(activity)
        skillsRecyclerView.adapter = SkillsAdapter(DataHolder.skills, this)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is SkillsFragmentInteractionListener) {
            mListener = context
        } else if (parentFragment  is SkillsFragmentInteractionListener){
            mListener = parentFragment as SkillsFragmentInteractionListener
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface SkillsFragmentInteractionListener {
        fun onSwitchToAbilitiesClicked()
    }

    override fun onRollClicked(skill: Skill) {
        toast(skill.name)
    }
}
