package com.y2thez.cyberpad.data

import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Y on 4/7/2018.
 */

class RollManager {

    companion object {

        fun getCyberRoll() : ArrayList<Int> {
            val values = ArrayList<Int>()
            val roll = get1D10Roll()
            values.add(roll)
            if (roll == 1) {
                values.add(get1D10Roll())
            }
            else if (roll == 10) {
                do {
                    val extraRoll = get1D10Roll()
                    values.add(extraRoll)
                } while (extraRoll == 10)
            }
            return values
        }

        fun get1D10Roll() : Int {
            return (1..11).random()
        }
    }

}

fun ClosedRange<Int>.random() =
        Random().nextInt(endInclusive - start) +  start