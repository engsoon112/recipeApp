package com.base.ecomm

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.base.ecomm.data.BaseResponse
import com.base.ecomm.data.MyUserManager
import com.base.ecomm.data.ResourceAPI
import com.base.ecomm.data.StatusAPI
import com.base.ecomm.utils.DialogUtil
import com.base.ecomm.utils.InternetConnectionUtil
import com.base.ecomm.utils.LocaleHelper
import com.base.ecomm.utils.MyPopup
import com.base.ecomm.utils.PermissionsUtil
import com.base.ecomm.utils.ResourceProvider
import com.jalanjalan.makan.R
import java.util.Locale

abstract class BaseActivity : AppCompatActivity() {

    interface OnApiResultListener {
        fun onSuccess(response: BaseResponse)
    }

    lateinit var internet: InternetConnectionUtil
    lateinit var dialog: DialogUtil
    lateinit var popup: MyPopup

    private var locale: Locale = Locale.ENGLISH

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base))

        val newOverride = Configuration(base.resources?.configuration)
        newOverride.fontScale = 1.0f
        applyOverrideConfiguration(newOverride)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MyApplication.resourceProvider = ResourceProvider(this)

        internet = InternetConnectionUtil(this)
        dialog = DialogUtil(this)
        popup = MyPopup(this)

        popup.dismissPopupLoading()
    }


    override fun onStart() {
        super.onStart()

        popup.dismissPopupLoading()

        // Check Country
        //checkCountry()
    }

    override fun onResume() {
        super.onResume()

        hideKeyboard()
    }

    /**
     * API Repository Response
     */
    fun repositoryResponse(result: ResourceAPI<Any>) {

        val root = window.decorView.rootView

        // Loading
        when (result.status) {
            StatusAPI.LOADING -> popup.popupLoading(root)
            else -> popup.dismissPopupLoading()
        }

        // Result
        when (result.status) {

            StatusAPI.FAILED -> {
                val response = result.data as BaseResponse
                popup.popupDialog(root, response.msg)
            }

            StatusAPI.TOKEN_EXPIRED -> {
                val response = result.data as BaseResponse
//                logoutUserAndLogin(response.msg)
            }

            StatusAPI.UNAUTHORIZED -> {
                val response = result.data as BaseResponse
//                logoutUserAndLogin(response.msg)
            }

            StatusAPI.VERSION_UPDATE -> {

            }

            StatusAPI.MAINTENANCE -> {
                val response = result.data as BaseResponse

                val msg = if (response.msg.isEmpty()) getString(R.string.sentence_maintenance) else response.msg

                dialog.showDialog("", msg, getString(R.string.text_Okay),
                    object : DialogUtil.OnDialogButtonClickListener {
                        override fun onButtonClick() {
                            finish()
                        }

                        override fun onButton2Click() {

                        }
                    }
                )
            }

            StatusAPI.ERROR -> {
                popup.popupDialog(root, getString(R.string.sentence_Something_went_wrong))
            }

            StatusAPI.NETWORK_ERROR -> {
                popup.popupDialog(root, getString(R.string.sentence_network_unstable))
            }

            else -> {

            }
        }

    }


    fun dismissLoadingPopup() {
        // Use this when call api at onCreate or onResume
        Handler(Looper.getMainLooper()).postDelayed({ popup.dismissPopupLoading() }, 50)
    }

    fun droidPermission(permissions: Array<String>): Boolean {
        return try {

            if (PermissionsUtil.checkAndRequestPermissions(this, window.decorView.rootView, permissions)) {
                true
            } else {
                PermissionsUtil.requestPermissions(this, permissions)
                false
            }

        } catch (e: Exception) {
//            MyFirebaseCrashlytics().recordException(e)
            e.printStackTrace()
            false
        }
    }


    /**  Button Single Click */
    fun buttonOneClick(v: View, t: Long = 800) {
        if (t > 0) {
            v.isClickable = false
            v.postDelayed({ v.isClickable = true }, t)
        }
        hideKeyboard(v)
    }

    fun hideKeyboard() {
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    fun hideKeyboard(v: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }


    override fun finish() {
        super.finish()
        popup.dismissPopupLoading()
        if (isFinishing) overridePendingTransition(R.anim.no_anim, R.anim.fade_out)
    }

    override fun onStop() {
        super.onStop()

        popup.dismissPopupLoading()
    }

    private var doubleBackToExitPressedOnce = false

    fun doubleBackToExit() {
        if (doubleBackToExitPressedOnce) {
            finish()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(
            this,
            "" + resources.getString(R.string.sentence_exit_double_back),
            Toast.LENGTH_SHORT
        ).show()
        Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExitPressedOnce = false }, 900)
    }

    fun getLanguageCode(): String {
        val code = LocaleHelper.getLocale(this).toString()
        return code.let {
            when (it) {
                "cn", "zh", "zh_CN" -> "cn"
                "tcn", "zh_TW" -> "tcn"
                "ms", "ms_MY" -> "malay"
                "ko", "ko_KR" -> "ko"
                "ja", "ja_JP" -> "ja"
                "in", "id", "ina" -> "id"
                else -> it
            }
        }
    }

}