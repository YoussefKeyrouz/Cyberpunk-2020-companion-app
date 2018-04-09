package com.y2thez.cyberpad.data

import com.y2thez.cyberpad.Cyberpad
import com.y2thez.cyberpad.utilities.IOUtilities
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by Y on 3/11/2018.
 */
class SkillsManager {
    companion object {

        fun getValueForSkill(skillName: String) : Int{
            return Cyberpad.prefs.getInt(skillName, 0)
        }

        private fun loadAbilities(): JSONObject {
            return JSONObject(IOUtilities.loadJSONFromAsset(Constants.abilitiesFileName))
        }

        private fun loadSkills(): JSONArray {
            return JSONArray(IOUtilities.loadJSONFromAsset(Constants.skillsFileName))
        }

        private fun loadCustomAbilities(): JSONObject {
            return JSONObject(IOUtilities.readFromFile(Constants.customSkillsFileName))
        }

//        private fun loadValues(): JSONObject {
//            return JSONObject(IOUtilities.readFromFile(Constants.valuesFileName))
//        }


        fun removeCustomAbility(ability: Ability) {

            //force unwarpping. If existing is null then something is REALLY WRONG. And I prefer the app to crash because most probably the data is fucked up
            val existing = DataHolder.categories.firstOrNull { c -> c.name == ability.categoryName }?.abilities?.firstOrNull { ab -> ab.name == ability.name
            }!!

            DataHolder.categories.singleOrNull { c -> c.name == ability.categoryName }?.abilities?.remove(existing)
            val customAbilities = loadCustomAbilities()
            val category: JSONObject = customAbilities.getJSONObject(ability.categoryName)!!
            category.remove(ability.key)
            IOUtilities.writeToFile(Constants.customSkillsFileName, customAbilities.toString())
        }

        private fun addCustomAbility(ability: Ability, skillname: String? = null) {

            val existing = DataHolder.categories.firstOrNull { c -> c.name == ability.categoryName }?.abilities?.firstOrNull {
                ab -> ab.name == ability.name
            }
            if(existing != null) {
                //ability exists, don't do anything
                return
            }
            //Add to cached abilities
            DataHolder.categories.singleOrNull { c -> c.name == ability.categoryName }?.abilities?.add(ability)

            //Add to permanent storage
            val customAbilities = loadCustomAbilities()
            val category: JSONObject =
                    if (customAbilities.has(ability.categoryName)) {
                        customAbilities.getJSONObject(ability.categoryName)
                    } else {
                        customAbilities.putOpt(ability.categoryName, JSONObject())
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
            if (languageName.count() == 0) {
                return
            }
            val key = languageName.replace(" ", "")
            addCustomAbility(Ability(key, languageName, "", Constants.intelligenceKey, Constants.languageKey))
        }

        fun addMartialArt(martialName: String) {
            if (martialName.count() == 0) {
                return
            }
            val key = martialName.replace(" ", "")
            addCustomAbility(Ability(key, martialName, "", Constants.reflexesKey, Constants.martialartsKey))
        }

//TODO save values to the server
//        fun saveValues() {
//            val mainObj = JSONObject()
//            DataHolder.skills.forEach { skill ->
//                val skillObj = JSONObject()
//                skillObj.put(Constants.valueKey, skill.value)
//                skillObj.put(Constants.modifierKey, skill.modifier)
//                mainObj.put(skill.name, skillObj)
//            }
//            DataHolder.categories.forEach { category ->
//                category.abilities.forEach { ability ->
//                    val abilityObj = JSONObject()
//                    abilityObj.put(Constants.valueKey, ability.value)
//                    abilityObj.put(Constants.modifierKey, ability.modifier)
//                    mainObj.put(ability.key, abilityObj)
//                }
//            }
//            IOUtilities.writeToFile(Constants.valuesFileName, mainObj.toString())
//        }

        fun loadAll() {
            createInitialDataIfNeeded()
            DataHolder.skills.clear()
            DataHolder.categories.clear()

//            val values = loadValues()

            val skills = loadSkills()
            for (i in 0 until skills.length()) {
                val skill = skills.getString(i)
//                val valueObj = if (values.has(skill)) values.getJSONObject(skill) else null
//                val value = valueObj?.getInt(Constants.valueKey) ?: 0
//                val modifier = valueObj?.getInt(Constants.modifierKey) ?: 0
                val skillObject = Skill(name = skill)
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
//                    val valueObj = if (values.has(abilityId)) values.getJSONObject(abilityId) else null
//                    val value = valueObj?.getInt(Constants.valueKey) ?: 0
//                    val modifier = valueObj?.getInt(Constants.modifierKey) ?: 0
                    val abilityName = ability.getString(Constants.nameKey)
                    val abilityDesc = ability.getString(Constants.descKey)
                    val skill = if (ability.has(Constants.skillKey)) ability.getString(Constants.skillKey) else categoryName
                    abilities.add(Ability(abilityId, abilityName, abilityDesc, skill, categoryName))
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
//            if (!IOUtilities.fileExists(Constants.valuesFileName)) {
//                val emptyValues = JSONObject()
//                IOUtilities.writeToFile(Constants.valuesFileName, emptyValues.toString())
//            }
        }
    }
}