package com.y2thez.cyberpad.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.y2thez.cyberpad.R
import kotlinx.android.synthetic.main.fragment_status.*

/**
 * Created by Y on 4/7/2018.
 */
class StatusFragment : Fragment() {

    val dbGlobals = FirebaseDatabase.getInstance().reference.child("globals")

    var globalsListener: ValueEventListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_status, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        globalsListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val date = dataSnapshot.child("date").getValue(String::class.java)
                    val dateCount = dataSnapshot.child("dayCount").getValue(String::class.java)
                    dateView.text = date
                    dateCountView.text = dateCount
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                dateView.text = getString(R.string.unable_read_date)
                dateCountView.text = ""
            }
        }
        dbGlobals.addValueEventListener(globalsListener)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        dbGlobals.removeEventListener(globalsListener)
    }
}
