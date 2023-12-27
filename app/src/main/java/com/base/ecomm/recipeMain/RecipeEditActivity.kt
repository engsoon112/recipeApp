package com.base.ecomm.recipeMain

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.base.ecomm.BaseActivity
import com.base.ecomm.data.MyUserManager
import com.base.ecomm.recipeMain.vm.HBookingViewModel
import com.base.ecomm.roomDb.model.User
import com.jalanjalan.makan.R
import com.jalanjalan.makan.databinding.ActivityBookingEditBinding
import com.zackratos.ultimatebarx.ultimatebarx.navigationBar
import com.zackratos.ultimatebarx.ultimatebarx.statusBar

class RecipeEditActivity : BaseActivity() {

	private lateinit var binding: ActivityBookingEditBinding

	private lateinit var hbookingViewModel: HBookingViewModel


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, R.layout.activity_booking_edit)

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

		binding.tvRecipeType.addTextChangedListener(object : TextWatcher {
			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
			}

			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

				MyUserManager.instance.recipeType = s.toString()

			}

			override fun afterTextChanged(s: Editable?) {
			}
		})

		binding.tvMealType.addTextChangedListener(object : TextWatcher {
			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
			}

			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

				MyUserManager.instance.mealType = s.toString()

			}

			override fun afterTextChanged(s: Editable?) {
			}
		})

		binding.tvSave.setOnClickListener {
			buttonOneClick(it)

			hbookingViewModel.updateRecipeDb(hbookingViewModel.receivedUser?.id!!)

			val intent = Intent(this@RecipeEditActivity, RecipeHomeActivity::class.java)
			val bundle = Bundle()
			intent.putExtras(bundle)
			startActivity(intent)
			overridePendingTransition(R.anim.fade_in, R.anim.no_anim)
		}

		binding.btnRemove.setOnClickListener {
			buttonOneClick(it)

			hbookingViewModel.deleteRecipeById(hbookingViewModel.receivedUser?.id!!)

			val intent = Intent(this@RecipeEditActivity, RecipeHomeActivity::class.java)
			val bundle = Bundle()
			intent.putExtras(bundle)
			startActivity(intent)
			overridePendingTransition(R.anim.fade_in, R.anim.no_anim)
		}

	}

	fun droidList() {

		binding.tvRecipeType.setText(hbookingViewModel.receivedUser?.recipeType)
		binding.tvMealType.setText(hbookingViewModel.receivedUser?.mealType)

	}

}