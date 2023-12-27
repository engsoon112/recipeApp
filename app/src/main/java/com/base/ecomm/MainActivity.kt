package com.base.ecomm

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.base.ecomm.data.MyUserManager
import com.base.ecomm.recipeMain.RecipeHomeActivity
import com.jalanjalan.makan.R
import com.jalanjalan.makan.databinding.ActivityMainBinding
import com.zackratos.ultimatebarx.ultimatebarx.navigationBar
import com.zackratos.ultimatebarx.ultimatebarx.statusBar


class MainActivity : AppCompatActivity(), Application.ActivityLifecycleCallbacks{

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        statusBar {
            fitWindow = false
            light = false
        }

        navigationBar{
            fitWindow = false
            light = false
        }

        Handler().postDelayed({ nextActivity() }, 2700)

    }

    private fun nextActivity() {

        if (MyUserManager.instance.userCsID.isNotEmpty()) {
//            startActivity(Intent(this, ProsesHidangActivity::class.java))
            finish()
            overridePendingTransition(R.anim.fade_in, R.anim.no_anim)
        } else {
            startActivity(Intent(this, RecipeHomeActivity::class.java))
            finish()
            overridePendingTransition(R.anim.fade_in, R.anim.no_anim)
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onActivityStarted(activity: Activity) {
        TODO("Not yet implemented")
    }

    override fun onActivityResumed(activity: Activity) {
        TODO("Not yet implemented")
    }

    override fun onActivityPaused(activity: Activity) {
        TODO("Not yet implemented")
    }

    override fun onActivityStopped(activity: Activity) {
        TODO("Not yet implemented")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        TODO("Not yet implemented")
    }

    override fun onActivityDestroyed(activity: Activity) {
        TODO("Not yet implemented")
    }
}
