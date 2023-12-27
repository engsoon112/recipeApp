package com.base.ecomm.roomDb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.base.ecomm.roomDb.model.User

@Database(entities = [User::class], version = 2, exportSchema = false)
@TypeConverters(Converter::class)
abstract class LocalDatabase : RoomDatabase() {
	abstract fun userRecipeDao(): UserRecipeDao

	companion object {

		val MIGRATION: Migration = object : Migration(2, 3) {
			override fun migrate(database: SupportSQLiteDatabase) {
				// Write your SQL queries here to update the schema and migrate data if needed
				// For example, you can rename columns, add new columns, or modify existing columns
				// But since the update does not require any data changes, you may not need any SQL queries here.
				// Just ensure the database version is updated from 1 to 2 in the @Database annotation.
			}
		}
	}
}

