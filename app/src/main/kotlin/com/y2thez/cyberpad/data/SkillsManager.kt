package com.y2thez.cyberpad.data

import com.y2thez.cyberpad.utilities.IOUtilities
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by Y on 3/11/2018.
 */
class SkillsManager {
    companion object {
        private fun loadAbilities(): JSONObject {
            return JSONObject(IOUtilities.loadJSONFromAsset(Constants.abilitiesFileName))
        }

        private fun loadSkills(): JSONArray {
            return JSONArray(IOUtilities.loadJSONFromAsset(Constants.skillsFileName))
        }

        private fun loadCustomAbilities(): JSONObject {
            return JSONObject(IOUtilities.readFromFile(Constants.customSkillsFileName))
        }

        private fun loadValues(): JSONObject {
            return JSONObject(IOUtilities.readFromFile(Constants.valuesFileName))
        }

        private fun addCustomAbility(categoryName: String, ability: Ability, skillname: String? = null) {
            DataHolder.categories.singleOrNull { c -> c.name == categoryName }?.abilities?.add(ability)

            val customAbilities = loadCustomAbilities()
            val category: JSONObject =
                    if (customAbilities.has(categoryName)) {
                        customAbilities.getJSONObject(categoryName)
                    } else {
                        customAbilities.putOpt(categoryName, JSONObject())
                    }
            val customAbility = JSONObject()
            customAbility.put(Constants.nameKey, ability.name)
            customAbility.put(Constants.descKey, ability.desc)
            if (skillname != null) {
                customAbility.put(Constants.skillKey, skillname)
            }

            category.putOpt(ability.key, customAbility)
            IOUtilities.writeToFile(Constants.customSkillsFileName, customAbilities.toString())
        }

        fun addLanguage(languageName: String) {
            val key = languageName.replace(" ", "")
            addCustomAbility(Constants.languageKey, Ability(key, languageName, "", 0, 0, Constants.intelligenceKey))
        }

        fun addMartialArt(languageName: String) {
            val key = languageName.replace(" ", "")
            addCustomAbility(Constants.martialartsKey, Ability(key, languageName, "", 0, 0, Constants.reflexesKey))
        }

        fun saveValues() {
            val mainObj = JSONObject()
            DataHolder.skills.forEach { skill ->
                val skillObj = JSONObject()
                skillObj.put(Constants.valueKey, skill.value)
                skillObj.put(Constants.modifierKey, skill.modifier)
                mainObj.put(skill.name, skillObj)
            }
            DataHolder.categories.forEach { category ->
                category.abilities.forEach { ability ->
                    val abilityObj = JSONObject()
                    abilityObj.put(Constants.valueKey, ability.value)
                    abilityObj.put(Constants.modifierKey, ability.modifier)
                    mainObj.put(ability.key, abilityObj)
                }
            }
            IOUtilities.writeToFile(Constants.valuesFileName, mainObj.toString())
        }

        fun loadAll() {
            createInitialDataIfNeeded()
            DataHolder.skills.clear()
            DataHolder.categories.clear()

            val values = loadValues()

            val skills = loadSkills()
            for (i in 0 until skills.length()) {
                val skill = skills.getString(i)
                val valueObj = if (values.has(skill)) values.getJSONObject(skill) else null
                val value = valueObj?.getInt(Constants.valueKey) ?: 0
                val modifier = valueObj?.getInt(Constants.modifierKey) ?: 0
                val skillObject = Skill(name = skill, value = value, modifier = modifier)
                DataHolder.skills.add(skillObject)
            }

            val abilitiesObj = loadAbilities()
            val customAbilitiesObj = loadCustomAbilities()
            customAbilitiesObj.keys().forEach { categoryName -> abilitiesObj.putOpt(categoryName, customAbilitiesObj.getJSONObject(categoryName)) }
            abilitiesObj.keys().forEach { categoryName ->
                val categoryObj = abilitiesObj.getJSONObject(categoryName)
                val abilities = mutableListOf<Ability>()
                for (abilityId in categoryObj.keys()) {
                    val ability = categoryObj.getJSONObject(abilityId)
                    val valueObj = if (values.has(abilityId)) values.getJSONObject(abilityId) else null
                    val value = valueObj?.getInt(Constants.valueKey) ?: 0
                    val modifier = valueObj?.getInt(Constants.modifierKey) ?: 0
                    val abilityName = ability.getString(Constants.nameKey)
                    val abilityDesc = ability.getString(Constants.descKey)
                    val skill = if (ability.has(Constants.skillKey)) ability.getString(Constants.skillKey) else categoryName
                    abilities.add(Ability(abilityId, abilityName, abilityDesc, value, modifier, skill))
                }
                DataHolder.categories.add(Category(categoryName, abilities))
            }

        }

        private fun createInitialDataIfNeeded() {
            if (!IOUtilities.fileExists(Constants.customSkillsFileName)) {
                val emptyCustoms = JSONObject()
                emptyCustoms.putOpt(Constants.languageKey, JSONObject())
                emptyCustoms.putOpt(Constants.martialartsKey, JSONObject())
                IOUtilities.writeToFile(Constants.customSkillsFileName, emptyCustoms.toString())
            }
            if (!IOUtilities.fileExists(Constants.valuesFileName)) {
                val emptyValues = JSONObject()
                IOUtilities.writeToFile(Constants.valuesFileName, emptyValues.toString())
            }
        }
    }
}