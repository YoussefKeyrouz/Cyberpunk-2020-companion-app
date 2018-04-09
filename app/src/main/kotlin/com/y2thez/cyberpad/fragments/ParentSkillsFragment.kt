package com.y2thez.cyberpad.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.y2thez.cyberpad.R
import com.y2thez.cyberpad.data.*

/**
 * Created by Y on 4/7/2018.
 */
class ParentSkillsFragment : Fragment(), ChildSkillsFragment.SkillsFragmentInteractionListener, ChildAbilitiesFragment.AbilitiesFragmentInteractionListener {

    interface RollResultInterface {
        fun rollResult(value: Int, skill: String, fumble: Boolean, criticalSuccess: Boolean)
        fun cancelRoll()
    }
    private var resValue: Int = 0
    private var resSkill: String = ""
    private var resFumble: Boolean = false
    private var resCriticalSuccess: Boolean = false

    private var mListener: RollResultInterface? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_skills, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadAbilitiesView()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is RollResultInterface) {
            mListener = context
        } else if (parentFragment  is RollResultInterface){
            mListener = parentFragment as RollResultInterface
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    private fun loadSkillsView() {
        loadFragment(ChildSkillsFragment(), true, true)
    }

    private fun backToAbilitiesView() {
        if (childFragmentManager.backStackEntryCount > 0) {
            childFragmentManager.popBackStack()
        }
    }

    private fun loadAbilitiesView() {
        loadFragment(ChildAbilitiesFragment(), false, false)
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

    //Todo refactor this to make 1 functions for skill and abilities instead of 2
    override fun onRollSkill(skill: Skill) {
        val rolls = RollManager.getCyberRoll()
        val firstRoll = rolls[0]
        resSkill = skill.name

        if(firstRoll == 1) {
            val fumbleRoll = rolls[1]
            showRollDialog(skill.name, 1, skillString="", fumbleString = "Fumble $fumbleRoll")
            resValue = fumbleRoll
            resFumble = true
            resCriticalSuccess = false
            return
        }

        var criticalSuccess: String? = null
        if(firstRoll == 10) {
            resFumble = false
            resCriticalSuccess = true
            criticalSuccess = "Critical Success"
        } else {
            resFumble = false
            resCriticalSuccess = false
        }
        val skillValue = SkillsManager.getValueForSkill(skill.name)
        val modValue = SkillsManager.getValueForSkill(Constants.modKey + skill.name)
        val totalValue = skillValue + modValue + rolls.sum()
        resValue = totalValue
        val rollString = "Roll: " + rolls.joinToString(separator = " + ")
        val skillString = skill.name + ": " + (skillValue + modValue)
        showRollDialog(skill.name, totalValue, rollString,skillString=skillString, criticalSuccess=criticalSuccess)
    }


    override fun onRollAbility(ability: Ability) {
        val rolls = RollManager.getCyberRoll()
        val firstRoll = rolls[0]
        resSkill = ability.name

        if(firstRoll == 1) {
            val fumbleRoll = rolls[1]
            showRollDialog(ability.name, 1, skillString="", fumbleString = "Fumble: $fumbleRoll")
            resValue = fumbleRoll
            resFumble = true
            resCriticalSuccess = false
            return
        }

        var criticalSuccess: String? = null
        if(firstRoll == 10) {
            resFumble = false
            resCriticalSuccess = true
            criticalSuccess = "Critical Success"
        } else {
            resFumble = false
            resCriticalSuccess = false
        }
        val skillValue = SkillsManager.getValueForSkill(ability.skill)
        val modSkillValue = SkillsManager.getValueForSkill(Constants.modKey + ability.skill)
        val abilityValue = SkillsManager.getValueForSkill(ability.key)
        val modValue = SkillsManager.getValueForSkill(Constants.modKey + ability.key)
        val totalValue = skillValue + abilityValue + modValue + + modSkillValue + rolls.sum()
        resValue = totalValue
        val rollString = "Roll: " + rolls.joinToString(separator = " + ")
        val skillString = ability.skill + ": " + (skillValue + modSkillValue)
        val abilityString = ability.name + ": " + (abilityValue + modValue)
        showRollDialog(ability.name, totalValue, rollString,skillString=skillString, abilityString=abilityString, criticalSuccess=criticalSuccess)
    }


    private fun showRollDialog(skillName: String, rollResult: Int, rollString: String = "", skillString: String? = null, abilityString: String? = null, fumbleString: String? = null, criticalSuccess: String? = null) {
        val fm = childFragmentManager
        val rollDialogFragment = RollDialogFragment.newInstance(skillName, rollResult, rollString, skillString, abilityString, fumbleString, criticalSuccess)
        rollDialogFragment.show(fm, "fragment_roll")
    }



}


