package com.y2thez.cyberpad.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.y2thez.cyberpad.R
import com.y2thez.cyberpad.adapters.AbilitiesAdapter
import com.y2thez.cyberpad.data.*
import kotlinx.android.synthetic.main.fragment_abilities_child.*
import android.os.Build
import android.support.v7.app.AlertDialog


/**
 * Created by Y on 4/7/2018.
 */
class ChildAbilitiesFragment : Fragment(), AbilitiesAdapter.AbilitiesInteractionlistener, AddAbilityDialogFragment.OnAddListener {

    private var mListener: AbilitiesFragmentInteractionListener? = null

    private val abilitiesAdapter = AbilitiesAdapter(DataHolder.categories, this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_abilities_child, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        switchButton.setOnClickListener {
            mListener?.onSwitchToSkillsClicked()
        }
        abilitiesRecyclerView.layoutManager = LinearLayoutManager(activity)
        abilitiesRecyclerView.adapter = abilitiesAdapter

        addButton.setOnClickListener({
            showAddDialog()
        })
        filterButton.setOnClickListener({
            changeFilter()
        })
        searchText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(query: CharSequence?, p1: Int, p2: Int, p3: Int) {
                applySearch(query.toString())
            }
        })
    }
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is AbilitiesFragmentInteractionListener) {
            mListener = context
        } else if (parentFragment  is AbilitiesFragmentInteractionListener){
            mListener = parentFragment as AbilitiesFragmentInteractionListener
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface AbilitiesFragmentInteractionListener {
        fun onSwitchToSkillsClicked()
        fun onRollAbility(ability: Ability)
    }

    override fun onRollClicked(ability: Ability) {
        mListener?.onRollAbility(ability)
    }

    override fun onLongPressAbility(ability: Ability): Boolean {
        return if(ability.categoryName == Constants.languageKey || ability.categoryName == Constants.martialartsKey) {
            confirmRemoval(ability)
            true
        } else {
            false
        }
    }

    override fun onInfoClicked(info: String) {
        showInfoDialog(info)
    }

    private fun showInfoDialog(infoText: String) {
        val fm = childFragmentManager
        val infoDialogFragment = InfoDialogFragment.newInstance(infoText)
        infoDialogFragment.show(fm, "fragment_info")
    }

    private fun showAddDialog() {
        val fm = childFragmentManager
        val addAbilityDialogFragment = AddAbilityDialogFragment()
        addAbilityDialogFragment.show(fm, "fragment_info")
    }

    override fun onAdd() {
        abilitiesAdapter.resetData()
    }

    private fun applySearch(query: String) {
        if(query == null || query.count() == 0) {
            abilitiesAdapter.resetFilter()
        } else {
            abilitiesAdapter.applySearchFilter(query)
        }
    }

    private fun changeFilter() {
        abilitiesAdapter.toggleVisibility()
        applySearch(searchText.text.toString())
    }

    private fun confirmRemoval(ability: Ability){
        val builder: AlertDialog.Builder
        val context = activity ?: return
        builder = AlertDialog.Builder(context)
        builder.setMessage("Are you sure you want to delete ${ability.name}?")
                .setPositiveButton(android.R.string.yes, { _, _ ->
                    SkillsManager.removeCustomAbility(ability)
                    abilitiesAdapter.resetData()
                })
                .setNegativeButton(android.R.string.no, { _, _ ->
                    // do nothing
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
    }

}

