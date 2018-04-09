package com.y2thez.cyberpad

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.y2thez.cyberpad.data.SkillsManager
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

/**
 * Created by Y on 3/11/2018.
 */
class Cyberpad: Application() {


    override fun onCreate() {
        super.onCreate()
        context = this
        initialSetup()
    }

    companion object {
        lateinit var prefs : SharedPreferences
        lateinit var prefsEdit :SharedPreferences.Editor

        lateinit var context: Cyberpad
            private set
    }

    private fun initialSetup() {
        SkillsManager.loadAll()
        prefs = PreferenceManager.getDefaultSharedPreferences(this)
        prefsEdit = prefs.edit()

        //Fonts
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Orbitron-Medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build())
    }
}