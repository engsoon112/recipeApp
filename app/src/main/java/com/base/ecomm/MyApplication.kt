package com.base.ecomm

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.room.Room
import com.base.ecomm.roomDb.LocalDatabase
import com.base.ecomm.utils.ResourceProvider

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        resourceProvider = ResourceProvider(this)
        contextApp = applicationContext

        localDatabase = Room.databaseBuilder(
            applicationContext,
            LocalDatabase::class.java,
            "receipe_database"
        ).fallbackToDestructiveMigration().build()

    }


    companion object {
        lateinit var contextApp: Context
        lateinit var resourceProvider: ResourceProvider
        lateinit var localDatabase: LocalDatabase
    }

}