package com.y2thez.cyberpad.utilities

import com.y2thez.cyberpad.Cyberpad
import java.io.*


/**
 * Created by Y on 3/11/2018.
 */
class IOUtilities {
    companion object {
        fun loadJSONFromAsset(assetName: String): String? {
            val json: String?
            try {
                val `is` = Cyberpad.context.assets.open(assetName)
                val size = `is`.available()
                val buffer = ByteArray(size)
                `is`.read(buffer)
                `is`.close()
                json = String(buffer, Charsets.UTF_8)
            } catch (ex: IOException) {
                ex.printStackTrace()
                return null
            }

            return json
        }

        fun fileExists(name: String) : Boolean {
            return File(Cyberpad.context.filesDir, name).exists()
        }

        fun writeToFile(name: String, data: String) {
            val file = File(Cyberpad.context.filesDir, name)
            val stream = FileOutputStream(file)
            try {
                stream.write(data.toByteArray())
            } catch (e: IOException) {
            } finally {
                stream.close()
            }
        }

        fun readFromFile(name: String) : String {
            val file = File(Cyberpad.context.filesDir, name)
            val length = file.length().toInt()
            val bytes = ByteArray(length)
            val input = FileInputStream(file)
            try {
                input.read(bytes)
            } catch (e: IOException) {
            } finally {
                input.close()
            }

            return String(bytes)
        }
    }
}