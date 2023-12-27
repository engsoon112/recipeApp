package com.base.ecomm.roomDb

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {
	@TypeConverter
	fun fromString(value: String?): List<String>? {
		if (value == null) {
			return null
		}
		val listType = object : TypeToken<List<String>>() {}.type
		return Gson().fromJson(value, listType)
	}

	@TypeConverter
	fun fromList(list: List<String>?): String? {
		return Gson().toJson(list)
	}
}