package com.y2thez.cyberpad.widgets

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by Y on 3/17/2018.
 */

abstract class SectionedRecyclerViewAdapter <HeaderVH : RecyclerView.ViewHolder, RowVH : RecyclerView.ViewHolder> :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val headerPositions = mutableListOf<Int>()
    private var itemCount = -1
    private val headerType = 1
    private val rowType = 2


    // Abstract functions
    abstract fun getSectionCount() : Int

    abstract fun getItemCountForSection(section: Int) : Int

    abstract fun onBindHeaderViewHolder(holder: HeaderVH, position: Int)

    abstract fun onBindRowViewHolder(holder: RowVH, section:Int, position: Int)

    abstract fun onCreateHeaderViewHolder(parent: ViewGroup, viewType: Int): HeaderVH

    abstract fun onCreateRowViewHolder(parent: ViewGroup, viewType: Int): RowVH


    //implementation


    override fun getItemViewType(position: Int): Int {
        return if (headerPositions.contains(position)) headerType else rowType
    }


    final override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == headerType)
            onCreateHeaderViewHolder(parent, viewType)
        else
            onCreateRowViewHolder(parent, viewType)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder.itemViewType == headerType) {
            onBindHeaderViewHolder(holder as HeaderVH, headerPositions.indexOf(position))
        } else {
            val sectionPosition = headerPositions.getClosestSectionPositionTo(position)
            val sectionIndex = headerPositions.indexOf(sectionPosition)
            val rowPosition = position - sectionPosition - 1
            onBindRowViewHolder(holder as RowVH, sectionIndex, rowPosition)
        }
    }

    final override fun getItemCount(): Int {
        return if(itemCount == -1) setItemCount() else itemCount
    }

    private fun setItemCount(): Int {
        itemCount = 0
        for (i in 0 until getSectionCount()) {
            headerPositions.add(itemCount)
            itemCount += getItemCountForSection(i) + 1
        }
        return itemCount
    }
}

fun List<Int>.getClosestSectionPositionTo(value: Int) : Int {
    val sorted = this.sortedDescending()
    for (int in sorted) {
        if (value >= int) {
            return int
        }
    }
    return 0
}