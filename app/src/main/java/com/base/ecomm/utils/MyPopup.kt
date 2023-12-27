package com.base.ecomm.utils

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.bumptech.glide.Glide
import com.jalanjalan.makan.R
import com.jalanjalan.makan.databinding.PopupDialogBinding
import com.jalanjalan.makan.databinding.PopupLoadingBinding


class MyPopup(internal var activity: Activity) {


    interface OnPopupDialogClickListener {
        fun onDialogClick()
    }

    private val width = LinearLayout.LayoutParams.MATCH_PARENT
    private val height = LinearLayout.LayoutParams.MATCH_PARENT
    private val focusable = false // lets taps outside the popup also dismiss it


    private val loadingPopupBinding = PopupLoadingBinding.inflate(activity.layoutInflater)
    private val loadingPopupView = loadingPopupBinding.root
    private var loadingPopupWindow: PopupWindow = PopupWindow(loadingPopupView, width, height, focusable)

    /** Popup Loading */
    fun popupLoading(view: View) {
        view.post {

            // create the popup window
            loadingPopupWindow.animationStyle = R.style.popup_window_fade
            loadingPopupWindow.isClippingEnabled = false
            loadingPopupWindow.contentView = loadingPopupView

            Glide.with(activity).asGif().load(R.drawable.anim_loading).into(loadingPopupBinding.ivAppLogo)

            // show the popup window
            // which view you pass in doesn't matter, it is only used for the window tolken
            if (!loadingPopupWindow.isShowing && !activity.isFinishing) {
                loadingPopupWindow.dismiss()
                loadingPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
            }
        }
    }

    fun dismissPopupLoading() {
        // if (::loadingPopupWindow.isInitialized) loadingPopupWindow.dismiss()
        loadingPopupWindow.dismiss()
    }


    /** Popup Dialog */
    fun popupDialog(view: View, msg: String, listener: OnPopupDialogClickListener? = null) {
        view.post {
            val popupBinding = PopupDialogBinding.inflate(activity.layoutInflater)
            val popupView = popupBinding.root

            // create the popup window
            val popupWindow = PopupWindow(popupView, width, height, focusable)
            popupWindow.animationStyle = R.style.popup_window_fade
            popupWindow.isClippingEnabled = false
            popupWindow.contentView = popupView

            popupBinding.tvMsg.text = msg

            popupBinding.lyPopupBackground.setOnClickListener {
                listener?.onDialogClick()
                popupWindow.dismiss()
            }

            // show the popup window
            // which view you pass in doesn't matter, it is only used for the window tolken
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
            popupWindow.isFocusable = true
        }
    }


    /**  Button Single Click */
    private fun buttonOneClick(v: View) {
        v.isClickable = false
        v.postDelayed({ v.isClickable = true }, 800)
    }

    private fun hideKeyboard(v: View) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }

}