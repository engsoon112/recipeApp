package com.base.ecomm.recipeMain

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.base.ecomm.BaseActivity
import com.base.ecomm.recipeMain.vm.HBookingViewModel
import com.base.ecomm.roomDb.model.User
import com.jalanjalan.makan.R
import com.jalanjalan.makan.databinding.ActivityBookingDetailBinding
import com.zackratos.ultimatebarx.ultimatebarx.navigationBar
import com.zackratos.ultimatebarx.ultimatebarx.statusBar

class RecipeDetailActivity : BaseActivity() {

	private lateinit var binding: ActivityBookingDetailBinding

	private lateinit var hbookingViewModel: HBookingViewModel


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, R.layout.activity_booking_detail)

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

		intent.extras?.let {
			val receivedUser: User? = it.getParcelable("user")
			hbookingViewModel.receivedUser = receivedUser
		}

		bindingView()
	}

	override fun onResume() {
		super.onResume()

	}

	private fun bindingView() {

		droidList()

		binding.ly.setOnClickListener {
			buttonOneClick(it)
		}

		binding.lyReturn.setOnClickListener {
			buttonOneClick(it)

			finish()
		}

		binding.tvEdit.setOnClickListener {
			buttonOneClick(it)

			val intent = Intent(this@RecipeDetailActivity, RecipeEditActivity::class.java)
			val bundle = Bundle()
			bundle.putParcelable("user", hbookingViewModel.receivedUser)
			intent.putExtras(bundle)
			startActivity(intent)
			overridePendingTransition(R.anim.fade_in, R.anim.no_anim)
		}

	}

	fun droidList() {

		binding.tvRecipeType.text = hbookingViewModel.receivedUser?.recipeType ?: "N/A"
		binding.tvMealType.text = hbookingViewModel.receivedUser?.mealType ?: "N/A"

	}

}