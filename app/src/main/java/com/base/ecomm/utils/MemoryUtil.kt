package com.base.ecomm.utils

import com.base.ecomm.MyApplication


class MemoryUtil {

    fun saveInt(key: String, value: Int) {
        val sharedPreferences = MyApplication.contextApp.getSharedPreferences("save", 0)

        val et = sharedPreferences.edit()
        et.putInt(key, value)
        et.commit()
    }

    fun loadInt(key: String): Int {
        return try {
            val sharedPreferences = MyApplication.contextApp.getSharedPreferences("save", 0)

            val value = sharedPreferences.getInt(key, 0)

            value
        } catch (e: Exception) {
            0
        }

    }

    fun saveLong(key: String, value: Long) {
        val sharedPreferences = MyApplication.contextApp.getSharedPreferences("save", 0)

        val et = sharedPreferences.edit()
        et.putLong(key, value)
        et.commit()
    }

    fun loadLong(key: String): Long {
        return try {
            val sharedPreferences = MyApplication.contextApp.getSharedPreferences("save", 0)

            val value = sharedPreferences.getLong(key, 0)

            value
        } catch (e: Exception) {
            0
        }

    }

    fun saveFloat(key: String, value: Float) {
        val sharedPreferences = MyApplication.contextApp.getSharedPreferences("save", 0)

        val et = sharedPreferences.edit()
        et.putFloat(key, value)
        et.commit()
    }

    fun loadFloat(key: String): Float {
        return try {
            val sharedPreferences = MyApplication.contextApp.getSharedPreferences("save", 0)

            val value = sharedPreferences.getFloat(key, 0f)

            value
        } catch (e: Exception) {
            0f
        }

    }

    fun saveString(key: String, value: String) {
        val sharedPreferences = MyApplication.contextApp.getSharedPreferences("save", 0)

        val et = sharedPreferences.edit()
        et.putString(key, value)
        et.commit()
    }

    fun loadString(key: String): String {
        return try {
            val sharedPreferences = MyApplication.contextApp.getSharedPreferences("save", 0)

            val value = sharedPreferences.getString(key, "")!!

            value
        } catch (e: Exception) {
            ""
        }

    }

    fun saveBoolean(key: String, value: Boolean) {
        val sharedPreferences = MyApplication.contextApp.getSharedPreferences("save", 0)

        val et = sharedPreferences.edit()
        et.putBoolean(key, value!!)
        et.commit()
    }

    fun loadBoolean(key: String): Boolean {
        return try {
            val sharedPreferences = MyApplication.contextApp.getSharedPreferences("save", 0)

            var value: Boolean = false
            value = sharedPreferences.getBoolean(key, false)

            value
        } catch (e: Exception) {
            false
        }

    }

}
