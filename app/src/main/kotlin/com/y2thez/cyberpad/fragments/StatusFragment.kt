package com.y2thez.cyberpad.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.y2thez.cyberpad.R
import com.romainpiel.shimmer.Shimmer
import kotlinx.android.synthetic.main.fragment_status.*

/**
 * Created by Y on 4/7/2018.
 */
class StatusFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_status, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val shimmer = Shimmer()
        shimmer.duration = 5000
        shimmer.startDelay = 1000
        shimmer.start(comingSoonLabel)
    }
}
