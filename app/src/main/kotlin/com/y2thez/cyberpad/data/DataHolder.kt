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

data class Skill(val name: String)

data class Ability(val key: String, val name: String, val desc: String, val skill: String, val categoryName: String)

data class Category(val name:String, val abilities: MutableList<Ability>)