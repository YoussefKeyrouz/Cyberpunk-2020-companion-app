package com.y2thez.cyberpad.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.y2thez.cyberpad.R

class SkillsFragment : Fragment(), SkillsChildFragment.SkillsFragmentInteractionListener, AbilitiesChildFragment.AbilitiesFragmentInteractionListener {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_skills, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadSkillsView(false)
    }

    private fun loadSkillsView(animate: Boolean) {
        loadFragment(SkillsChildFragment(), animate)
    }

    private fun loadAbilitiesView(animate: Boolean) {
        loadFragment(AbilitiesChildFragment(), animate)
    }

    private fun loadFragment(frag: Fragment, animate: Boolean) {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        if(animate) {
            fragmentTransaction?.setCustomAnimations(
                    R.animator.card_flip_right_in, R.animator.card_flip_left_out, R.animator.card_flip_left_in, R.animator.card_flip_right_out
            )
        }
        fragmentTransaction?.replace(R.id.replaceLayout, frag)
        fragmentTransaction?.commit()
    }

    override fun onSwitchToAbilitiesClicked() {
        loadAbilitiesView(true)
    }

    override fun onSwitchToSkillsClicked() {
        loadSkillsView(true)
    }

}


