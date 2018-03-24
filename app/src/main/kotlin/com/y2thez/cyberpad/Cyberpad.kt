package com.y2thez.cyberpad

import android.app.Application
import com.y2thez.cyberpad.data.SkillsManager

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
        lateinit var context: Cyberpad
            private set
    }

    private fun initialSetup() {
        SkillsManager.loadAll()
    }
}