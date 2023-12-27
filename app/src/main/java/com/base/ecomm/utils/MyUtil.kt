package com.base.ecomm.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import com.base.ecomm.MyApplication
import com.jalanjalan.makan.R
import java.io.*

class MyUtil {

    fun saveTextFile(folderName: String, fileName: String, text: String): Boolean {
        try {
            val saveFolder = File(MyApplication.contextApp.filesDir.absolutePath + File.separator + folderName)

            if (!saveFolder.exists()) saveFolder.mkdirs() //else saveFolder.delete()

            val file = File(saveFolder, fileName)
            val writer = FileWriter(file)

            writer.append(text)
            writer.flush()
            writer.close()

            return true
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }
    }


    fun getFile(folderName: String, fileName: String): File? {
        return try {
            val saveFolder = File(MyApplication.contextApp.filesDir.absolutePath + File.separator + folderName)
            if (!saveFolder.exists()) saveFolder.mkdirs()
            File(saveFolder, fileName)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    fun isValidOTP(otp: String): Boolean {
        return otp.length == 6
    }

    fun droidInt(value: Any): Int {
        return try {
            val mValue = value.toString().replace(",", "")

            if (mValue.isNotEmpty()) {
                mValue.toDouble().toInt()
            } else {
                0
            }
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    fun insertAt(target: String, position: Int, insert: String): String {
        val targetLen = target.length
        require(!(position < 0 || position > targetLen)) { "position=$position" }
        if (insert.isEmpty()) {
            return target
        }
        if (position == 0) {
            return insert + target
        } else if (position == targetLen) {
            return target + insert
        }
        val insertLen = insert.length
        val buffer = CharArray(targetLen + insertLen)
        target.toCharArray(buffer, 0, 0, position)
        insert.toCharArray(buffer, position, 0, insertLen)
        target.toCharArray(buffer, position + insertLen, position, targetLen)
        return String(buffer)
    }

    fun saveBitmapToLocale(context: Context, bitmap: Bitmap, fileName: String): File? {
        try {
            val saveFolder = File(
                MyApplication.contextApp.filesDir.absolutePath + File.separator
                        + context.getString(R.string.app_name)
            )
            if (!saveFolder.exists()) saveFolder.mkdir()

            val saveFile = File(saveFolder, fileName)
            if (saveFile.exists()) saveFile.delete()

            val fileOutPutStream = FileOutputStream(saveFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, fileOutPutStream)
            fileOutPutStream.flush()
            fileOutPutStream.close()

            context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(saveFile)))

            return saveFile
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }


}
