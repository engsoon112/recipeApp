package com.base.ecomm.recipeMain

import android.graphics.Color
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.base.ecomm.BaseActivity
import com.base.ecomm.data.MyUserManager
import com.base.ecomm.recipeMain.vm.HBookingViewModel
import com.jalanjalan.makan.R
import com.jalanjalan.makan.databinding.RecipetypeBinding
import com.zackratos.ultimatebarx.ultimatebarx.navigationBar
import com.zackratos.ultimatebarx.ultimatebarx.statusBar

class RecipeSelectionActivity : BaseActivity() {

	private lateinit var binding: RecipetypeBinding

	private lateinit var hbookingViewModel: HBookingViewModel


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, R.layout.recipetype)

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

		binding.recipeBR = hbookingViewModel.recipeBR

		binding.ly.setOnClickListener {
			buttonOneClick(it)
		}

		binding.lyReturn.setOnClickListener {
			buttonOneClick(it)

			finish()
		}

		binding.btnRecipeType.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newText ->

			when (newIndex) {
				0 -> {

					MyUserManager.instance.recipeType = newText
				}

				1 -> {

					MyUserManager.instance.recipeType = newText
				}

				2 -> {

					MyUserManager.instance.recipeType = newText
				}
			}
		}

		binding.btnMealType.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newText ->

			when (newIndex) {
				0 -> {

					MyUserManager.instance.mealType = newText
				}

				1 -> {

					MyUserManager.instance.mealType = newText
				}

				2 -> {

					MyUserManager.instance.mealType = newText
				}
			}
		}

		binding.btnAdd.setOnClickListener {
			buttonOneClick(it)

			hbookingViewModel.recipeBR.buttonClick = true
			if (hbookingViewModel.recipeBR.buttonValidate) {

				hbookingViewModel.insertRecipeDb()

				finish()
			}

		}

	}


}