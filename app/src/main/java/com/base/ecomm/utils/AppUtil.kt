package com.base.ecomm.utils

import android.os.Environment
import java.io.File
import java.util.*


class AppUtil {


    fun formatMalaysiaIc(ic: String): String {

        if (ic.contains("-")) return ic

        var tempIc = ic.replace("-", "")

        return when (ic.length) {
            12 -> {
                tempIc = MyUtil().insertAt(tempIc, 6, "-")
                tempIc = MyUtil().insertAt(tempIc, 9, "-")
                tempIc
            }
            else -> ic
        }
    }

    fun malaysiaIcValidation(ic: String): Boolean {

        if (ic.length != 14) return false

        val month = ic.substring(2, 4).toInt()
        val date = ic.substring(4, 6).toInt()

        return if (month in 1..12) date in 1..31 else false
    }


    fun formatThailandIc(ic: String): String {

        if (ic.contains("-")) return ic

        var tempIc = ic.replace("-", "")

        return when (ic.length) {
            13 -> {
                tempIc = MyUtil().insertAt(tempIc, 1, "-")
                tempIc = MyUtil().insertAt(tempIc, 6, "-")
                tempIc = MyUtil().insertAt(tempIc, 12, "-")
                tempIc = MyUtil().insertAt(tempIc, 15, "-")
                tempIc
            }
            else -> ic
        }
    }


    fun getDeviceCameraPicturesCount(): Int {
        var count = 0
        try {
            val dir = File(Environment.getExternalStorageDirectory(), "/DCIM/Camera")
            val files = dir.listFiles()

            val dir1 = File(Environment.getExternalStorageDirectory(), "/Pictures")
            val files1 = dir1.listFiles()

            val dir2 = File(Environment.getExternalStorageDirectory(), "/Camera")
            val files2 = dir2.listFiles()

            //val files = dir.listFiles()
            // files?.size ?: 0
            when {
                files?.isNotEmpty() == true -> {
                    files.let {
                        return if (files.isNotEmpty()) {
                            //var count = 0
                            files.forEach {
                                if (it.exists()) {
                                    val fileName = it.name.lowercase(Locale.getDefault())
                                    if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png")) {
                                        count++
                                    }
                                }
                            }
                            count
                        } else {
//                            MyFirebaseCrashlytics().logError("AppUtil.kt.getDeviceCameraPicturesCount() file is empty")
                            0
                        }
                    }
                }
                files1?.isNotEmpty() == true -> {
                    files1.let {
                        return if (files1.isNotEmpty()) {
                            //var count = 0
                            files1.forEach {
                                if (it.exists()) {
                                    val fileName = it.name.lowercase(Locale.getDefault())
                                    if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png")) {
                                        count++
                                    }
                                }
                            }
                            count
                        } else {
//                            MyFirebaseCrashlytics().logError("AppUtil.kt.getDeviceCameraPicturesCount() file is empty")
                            0
                        }
                    }
                }
                files2?.isNotEmpty() == true -> {
                    files2.let {
                        return if (files2.isNotEmpty()) {
                            //var count = 0
                            files2.forEach {
                                if (it.exists()) {
                                    val fileName = it.name.lowercase(Locale.getDefault())
                                    if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png")) {
                                        count++
                                    }
                                }
                            }
                            count
                        } else {
//                            MyFirebaseCrashlytics().logError("AppUtil.kt.getDeviceCameraPicturesCount() file is empty")
                            0
                        }
                    }
                }
                else -> {
//                    MyFirebaseCrashlytics().logError("AppUtil.kt.getDeviceCameraPicturesCount() no files")
                }
            }
        } catch (e: Exception) {
//            MyFirebaseCrashlytics().recordException(e)
        }
        return count
    }

    fun getDeviceCameraPicturesLatestPhoto(count: Int = 10): ArrayList<ByteArray> {
        val items = ArrayList<ByteArray>()
        try {

            val dir = File(Environment.getExternalStorageDirectory(), "/DCIM/Camera")
            val files = dir.listFiles()

            val dir1 = File(Environment.getExternalStorageDirectory(), "/Pictures")
            val files1 = dir1.listFiles()

            val dir2 = File(Environment.getExternalStorageDirectory(), "/Camera")
            val files2 = dir2.listFiles()

            when {
                files?.isNotEmpty() == true -> {
                    files.let {
                        if (files.isNotEmpty()) {
                            var totalCount = 0
                            for (i in files.size - 1 downTo 0) {
                                try {
                                    if (files[i].exists()) {
                                        val fileName = files[i].name.lowercase(Locale.getDefault())
                                        val fileSize = files[i].length() / 1024 / 1024    //convert in MB
                                        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png")) {
//                                        MyUtil().byteArrayToBitmap(files[i].readBytes())?.let {
//                                            items.add(MyUtil().bitmapToByteArray(it, 40))
//                                            totalCount++
//                                            it.recycle()
//                                        }
                                            if (fileSize < 10) {     //set image size limit to 10mb
                                                files[i].readBytes().let {
                                                    items.add(it)
                                                    totalCount++
                                                }
                                            }
                                        }
                                    }
                                } catch (e: Exception) {
//                                    MyFirebaseCrashlytics().recordException(e)
                                    e.printStackTrace()
                                }
                                if (totalCount == count) break
                            }
                        } else {
//                            MyFirebaseCrashlytics().logError("AppUtil.kt.getDeviceCameraPicturesLatestPhoto() file is empty")
                        }
                    }
                }
                files1?.isNotEmpty() == true -> {
                    files1.let {
                        if (files1.isNotEmpty()) {
                            var totalCount = 0
                            for (i in files1.size - 1 downTo 0) {
                                try {
                                    if (files1[i].exists()) {
                                        val fileName = files1[i].name.lowercase(Locale.getDefault())
                                        val fileSize1 = files1[i].length() / 1024 / 1024    //convert in MB
                                        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png")) {
//                                        MyUtil().byteArrayToBitmap(files1[i].readBytes())?.let {
//                                            items.add(MyUtil().bitmapToByteArray(it, 40))
//                                            totalCount++
//                                            it.recycle()
//                                        }
                                            if (fileSize1 < 10) {     //set image size limit to 10mb
                                                files1[i].readBytes().let {
                                                    items.add(it)
                                                    totalCount++
                                                }
                                            }
                                        }
                                    }
                                } catch (e: Exception) {
//                                    MyFirebaseCrashlytics().recordException(e)
                                    e.printStackTrace()
                                }
                                if (totalCount == count) break
                            }
                        } else {
//                            MyFirebaseCrashlytics().logError("AppUtil.kt.getDeviceCameraPicturesLatestPhoto() file is empty")
                        }
                    }
                }
                files2?.isNotEmpty() == true -> {
                    files2.let {
                        if (files2.isNotEmpty()) {
                            var totalCount = 0
                            for (i in files2.size - 1 downTo 0) {
                                try {
                                    if (files2[i].exists()) {
                                        val fileName = files2[i].name.lowercase(Locale.getDefault())
                                        val fileSize2 = files2[i].length() / 1024 / 1024    //convert in MB
                                        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png")) {
                                            if (fileSize2 < 10) {     //set image size limit to 10mb
                                                files2[i].readBytes().let {
                                                    items.add(it)
                                                    totalCount++
                                                }
                                            }
                                        }
                                    }
                                } catch (e: Exception) {
//                                    MyFirebaseCrashlytics().recordException(e)
                                    e.printStackTrace()
                                }
                                if (totalCount == count) break
                            }
                        } else {
//                            MyFirebaseCrashlytics().logError("AppUtil.kt.getDeviceCameraPicturesLatestPhoto() file is empty")
                        }
                    }
                }
                else -> {
//                    MyFirebaseCrashlytics().logError("AppUtil.kt.getDeviceCameraPicturesLatestPhoto() no files")
                    return items
                }
            }

        } catch (e: Exception) {
//            MyFirebaseCrashlytics().recordException(e)
            e.printStackTrace()
        }
        return items
    }
}
