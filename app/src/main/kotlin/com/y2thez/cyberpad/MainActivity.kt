package com.y2thez.cyberpad

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.y2thez.cyberpad.fragments.ParentSkillsFragment
import com.y2thez.cyberpad.fragments.StatusFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Y on 3/11/2018.
 */
class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_status -> {
                loadStatus()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_skills -> {
                loadSkills()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_inventory -> {
                loadStatus()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_news -> {
                loadStatus()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        loadSkills()

    }

    private fun loadStatus() {
        loadFragment(StatusFragment())
    }

    private fun loadSkills() {
        loadFragment(ParentSkillsFragment())
    }

    private fun loadFragment(f: Fragment) {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(R.id.mainLayout, f)
        ft.commit()

    }
}
