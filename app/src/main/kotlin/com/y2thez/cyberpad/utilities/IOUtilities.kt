package com.y2thez.cyberpad.utilities

import android.graphics.Bitmap
import com.y2thez.cyberpad.Cyberpad
import java.io.*
import android.content.Intent
import android.support.v4.content.FileProvider
import android.content.Context


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

        fun shareBitmapToWhatsapp(context: Context, bitmap: Bitmap) {
            val fileName = "share.png"
            val dir = File(context.cacheDir, "images")
            dir.mkdirs()
            val file = File(dir, fileName)
            try {
                val fOut = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut)
                fOut.flush()
                fOut.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val uri = FileProvider.getUriForFile(context, context.packageName + ".fileprovider", file)

            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.type = "image/*"
            intent.setDataAndType(uri, context.contentResolver.getType(uri))
            intent.putExtra(Intent.EXTRA_SUBJECT, "")
            intent.putExtra(Intent.EXTRA_TEXT, "")
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.`package` = "com.whatsapp"
            try {
                Cyberpad.context.startActivity(Intent.createChooser(intent, "Roll"))
            } catch (ex: android.content.ActivityNotFoundException) {
                ex.printStackTrace()
            }

        }
    }
}