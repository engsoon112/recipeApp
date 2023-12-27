package com.base.ecomm.roomDb.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "userRecipe")
data class User(
	@PrimaryKey(autoGenerate = true)
	val id: Long = 0,
	var recipeType: String,
	var mealType: String,

	) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readLong(),
		parcel.readString() ?: "",
		parcel.readString() ?: "",
	)

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeLong(id)
		parcel.writeString(recipeType)
		parcel.writeString(mealType)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<User> {
		override fun createFromParcel(parcel: Parcel): User {
			return User(parcel)
		}

		override fun newArray(size: Int): Array<User?> {
			return arrayOfNulls(size)
		}
	}
}