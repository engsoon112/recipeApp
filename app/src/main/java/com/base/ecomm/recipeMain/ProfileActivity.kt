package com.base.ecomm.recipeMain

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.base.ecomm.BaseActivity
import com.base.ecomm.data.MyUserManager
import com.base.ecomm.recipeMain.vm.HBookingViewModel
import com.jalanjalan.makan.R
import com.jalanjalan.makan.databinding.ActivityProfileBinding
import com.jalanjalan.makan.databinding.PopupLogoutConfirmBinding
import com.zackratos.ultimatebarx.ultimatebarx.navigationBar
import com.zackratos.ultimatebarx.ultimatebarx.statusBar

class ProfileActivity : BaseActivity() {

	private lateinit var binding: ActivityProfileBinding

	private lateinit var hbookingViewModel: HBookingViewModel


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

		statusBar {
			fitWindow = true
			light = false
			color = Color.parseColor("#586963")
		}

		navigationBar {
			fitWindow = false
			light = true
			color = Color.parseColor("#586963")
		}

		hbookingViewModel = ViewModelProviders.of(this)[HBookingViewModel::class.java]

		bindingView()
	}

	override fun onResume() {
		super.onResume()

	}

	private fun bindingView() {

		binding.ly.setOnClickListener {
			buttonOneClick(it)
		}

		binding.lyReturn.setOnClickListener {
			buttonOneClick(it)

			finish()
		}

		binding.btnLogout.setOnClickListener {
			buttonOneClick(it)

			popUpLogout(it)
		}


	}

	private fun popUpLogout(view: View) {
		view.post {
			val popupBinding = PopupLogoutConfirmBinding.inflate(layoutInflater)
			val popupView = popupBinding.root

			// create the popup window
			val popupWindow =
				PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, false)
			popupWindow.animationStyle = R.style.popup_window_fade
			popupWindow.isClippingEnabled = false
			popupWindow.contentView = popupView


			popupBinding.btnContinue.setOnClickListener {
				popupWindow.dismiss()

				// Clear ALL CACHE
				MyUserManager.instance.clearCache {

					popup.popupLoading(window.decorView.rootView)
					Handler(Looper.getMainLooper()).postDelayed({
						popup.dismissPopupLoading()

						val intent = Intent(this, RecipeHomeActivity::class.java)
						startActivity(intent)
						finish()
						overridePendingTransition(R.anim.fade_in, R.anim.no_anim)
					}, 6000)

				}
			}

			popupBinding.btnBack.setOnClickListener {
				popupWindow.dismiss()

			}

			// show the popup window
			// which view you pass in doesn't matter, it is only used for the window tolken
			popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
			popupWindow.isFocusable = true
		}
	}
}