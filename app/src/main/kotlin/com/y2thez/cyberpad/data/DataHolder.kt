package com.y2thez.cyberpad.data

/**
 * Created by Y on 3/11/2018.
 */
class DataHolder {
    companion object {
        var skills = mutableListOf<Skill>()
        var categories = mutableListOf<Category>()
    }
}

data class Skill(val name: String, val value: Int, var modifier:Int)

data class Ability(val key: String, val name: String, val desc: String, var value: Int, var modifier:Int, val skill: String)

data class Category(val name:String, val abilities: MutableList<Ability>)