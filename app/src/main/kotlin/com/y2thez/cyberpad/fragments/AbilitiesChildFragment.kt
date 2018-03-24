package com.y2thez.cyberpad.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.y2thez.cyberpad.R
import com.y2thez.cyberpad.adapters.AbilitiesAdapter
import com.y2thez.cyberpad.data.Ability
import com.y2thez.cyberpad.data.DataHolder
import kotlinx.android.synthetic.main.fragment_abilities_child.*
import org.jetbrains.anko.support.v4.longToast
import org.jetbrains.anko.support.v4.toast


/**
 * A simple [Fragment] subclass.
 */
class AbilitiesChildFragment : Fragment(), AbilitiesAdapter.AbilitiesInteractionlistener {


    private var mListener: AbilitiesFragmentInteractionListener? = null

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
        abilitiesRecyclerView.adapter = AbilitiesAdapter(DataHolder.categories, this)
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
    }

    override fun onRollClicked(ability: Ability) {
        toast(ability.name)
    }

    override fun onInfoClicked(info: String) {
        longToast(info)
    }
}

