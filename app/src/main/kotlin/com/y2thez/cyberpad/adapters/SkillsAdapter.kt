package com.y2thez.cyberpad.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import com.y2thez.cyberpad.R
import com.y2thez.cyberpad.data.Ability
import com.y2thez.cyberpad.data.Category
import com.y2thez.cyberpad.data.Skill
import com.y2thez.cyberpad.widgets.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.skill_header.view.*
import kotlinx.android.synthetic.main.skill_row.view.*
/**
 * Created by Y on 3/16/2018.
 */




class SkillsAdapter(private val skills: List<Skill>, private val listener: SkillsInteractionListener) :
        RecyclerView.Adapter<SkillsAdapter.SkillsViewHolder>() {

    interface SkillsInteractionListener {
        fun onRollClicked(skill: Skill)
    }

    class SkillsViewHolder(view: View) : RecyclerView.ViewHolder(view)


    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): SkillsAdapter.SkillsViewHolder {
        return SkillsViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.skill_row, parent, false))
    }

    override fun onBindViewHolder(holder: SkillsViewHolder, position: Int) {
        val skill = skills[position]
        holder.itemView.skillButton.text = skill.name
        holder.itemView.valueText.text = skill.value.toString()
        holder.itemView.modifierText.text = skill.modifier.toString()
        holder.itemView.skillButton.setOnClickListener{
            listener.onRollClicked(skill)
        }
        holder.itemView.infoButton.visibility = View.INVISIBLE
    }

    override fun getItemCount() = skills.size
}

class AbilitiesAdapter(private val categories: MutableList<Category>, private val listener: AbilitiesInteractionlistener) :
        SectionedRecyclerViewAdapter<AbilitiesAdapter.AbilityHeadersViewHolder, AbilitiesAdapter.AbilitiesViewHolder>() {


    interface AbilitiesInteractionlistener {
        fun onRollClicked(ability: Ability)
        fun onInfoClicked(info: String)
    }

    override fun getSectionCount(): Int {
        return categories.size
    }

    override fun getItemCountForSection(section: Int): Int {
        return categories[section].abilities.size
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup, viewType: Int): AbilityHeadersViewHolder {
        return AbilityHeadersViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.skill_header, parent, false))
    }

    override fun onCreateRowViewHolder(parent: ViewGroup, viewType: Int): AbilitiesViewHolder {
        return AbilitiesViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.skill_row, parent, false))
    }

    override fun onBindHeaderViewHolder(holder: AbilityHeadersViewHolder, position: Int) {
        val category = categories[position]
        holder.itemView.skillName.text = category.name
    }

    override fun onBindRowViewHolder(holder: AbilitiesViewHolder, section: Int, position: Int) {
        val ability = categories[section].abilities[position]
        holder.itemView.skillButton.text = ability.name
        holder.itemView.valueText.text = ability.value.toString()
        holder.itemView.modifierText.text = ability.modifier.toString()
        holder.itemView.skillButton.setOnClickListener{
            listener.onRollClicked(ability)
        }
        holder.itemView.infoButton.setOnClickListener {
            listener.onInfoClicked(ability.desc)
        }
    }


    class AbilitiesViewHolder(view: View) : RecyclerView.ViewHolder(view)
    class AbilityHeadersViewHolder(view: View) : RecyclerView.ViewHolder(view)

}