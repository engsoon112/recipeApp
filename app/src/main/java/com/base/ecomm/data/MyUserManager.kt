package com.base.ecomm.data

import android.content.Context
import android.content.SharedPreferences
import com.base.ecomm.MyApplication

class MyUserManager constructor(context: Context) {

    private val IS_USER_LOGIN = "isUserLogin"

    var pref: SharedPreferences = context.getSharedPreferences("foodies", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = pref.edit()

    fun clearCache(callback: () -> Unit) {
        editor.clear().apply()
        callback.invoke()
    }

    var recipeType: String
        get() = pref.getString("recipeType", "")!!
        set(value) {
            editor.putString("recipeType", value)
            editor.apply()
        }

    var mealType: String
        get() = pref.getString("mealType", "")!!
        set(value) {
            editor.putString("mealType", value)
            editor.apply()
        }

    var userCsID: String
        get() = pref.getString("userCsID", "")!!
        set(value) {
            editor.putString("userCsID", value)
            editor.apply()
        }

    //Level2 For merchant & developer
    var userLanguage: String
        get() = pref.getString("userLanguage", "en")!!
        set(value) {
            editor.putString("userLanguage", value)
            editor.apply()
        }

    var displayLanguage: String
        get() = pref.getString("displayLanguage", "English")!!
        set(value) {
            editor.putString("displayLanguage", value)
            editor.apply()
        }

    var userCustomerId: String
        get() = pref.getString("userCustomerId", "")!!
        set(value) {
            editor.putString("userCustomerId", value)
            editor.apply()
        }

    var userToken: String
        get() = pref.getString("userToken", "")!!
        set(value) {
            editor.putString("userToken", value)
            editor.apply()
        }

    var userFullName: String
        get() = pref.getString("userFullName", "")!!
        set(value) {
            editor.putString("userFullName", value)
            editor.apply()
        }

    var userIc: String
        get() = pref.getString("userIc", "")!!
        set(value) {
            editor.putString("userIc", value)
            editor.apply()
        }

    var userCountry: String
        get() = pref.getString("userCountry", "")!!
        set(value) {
            editor.putString("userCountry", value)
            editor.apply()
        }

    var firebaseRegistrationToken: String
        get() = pref.getString("firebaseRegistrationToken", "")!!
        set(value) {
            editor.putString("firebaseRegistrationToken", value)
            editor.apply()
        }

    var userCurrency: String
        get() = pref.getString("userCurrency", "RM")!!
        set(value) {
            editor.putString("userCurrency", value)
            editor.apply()
        }

    var userStatus: String
        get() = pref.getString("userStatus", "")!!
        set(value) {
            editor.putString("userStatus", value)
            editor.apply()
        }

    var userMobileNo: String
        get() = pref.getString("userMobileNo", "")!!
        set(value) {
            editor.putString("userMobileNo", value)
            editor.apply()
        }

    var userJoined: String
        get() = pref.getString("userJoined", "")!!
        set(value) {
            editor.putString("userJoined", value)
            editor.apply()
        }

    var userFbAccount: String
        get() = pref.getString("userFbAccount", "")!!
        set(value) {
            editor.putString("userFbAccount", value)
            editor.apply()
        }

    var isUserLogin: Boolean
        get() = pref.getBoolean(IS_USER_LOGIN, false)
        set(value) {
            editor.putBoolean(IS_USER_LOGIN, value)
            editor.apply()
        }

    var masterBasic: String
        get() = pref.getString("masterBasic", "")!!
        set(value) {
            editor.putString("masterBasic", value)
            editor.apply()
        }

    var masterUrl: String
        get() = pref.getString("masterUrl", "")!!
        set(value) {
            editor.putString("masterUrl", value)
            editor.apply()
        }

    var fromApp: String
        get() = pref.getString("fromApp", "")!!
        set(value) {
            editor.putString("fromApp", value)
            editor.apply()
        }

    var isUserPermissionDataUsage: Boolean
        get() = pref.getBoolean("isUserPermissionDataUsage", false)
        set(value) {
            editor.putBoolean("isUserPermissionDataUsage", value)
            editor.apply()
        }

    var isHaircutUI: Boolean
        get() = pref.getBoolean("isHaircutUI", false)
        set(value) {
            editor.putBoolean("isHaircutUI", value)
            editor.apply()
        }

    companion object {
        val instance: MyUserManager by lazy {
            MyUserManager(MyApplication.contextApp)
        }
    }

}