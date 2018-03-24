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
        loadAbilitiesView()
    }

    private fun loadSkillsView() {
        loadFragment(SkillsChildFragment(), true, true)
    }

    private fun backToAbilitiesView() {
        if (childFragmentManager.backStackEntryCount > 0) {
            childFragmentManager.popBackStack()
        }
    }

    private fun loadAbilitiesView() {
        loadFragment(AbilitiesChildFragment(), false, false)
    }

    private fun loadFragment(frag: Fragment, withbackStack: Boolean, animate: Boolean) {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        if(animate) {
            fragmentTransaction?.setCustomAnimations(
                    R.animator.card_flip_right_in, R.animator.card_flip_left_out, R.animator.card_flip_left_in, R.animator.card_flip_right_out
            )
        }
        fragmentTransaction?.replace(R.id.replaceLayout, frag)
        if(withbackStack) {
            fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction?.commit()
    }

    override fun onSwitchToAbilitiesClicked() {
        backToAbilitiesView()
    }

    override fun onSwitchToSkillsClicked() {
        loadSkillsView()
    }

}


