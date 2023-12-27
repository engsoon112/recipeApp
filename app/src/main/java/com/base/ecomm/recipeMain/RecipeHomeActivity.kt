package com.base.ecomm.recipeMain

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.PopupWindow
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import coil.transform.CircleCropTransformation
import com.base.ecomm.BaseActivity
import com.base.ecomm.data.MyUserManager
import com.base.ecomm.recipeMain.adapter.BookingRecipeListAdapter
import com.base.ecomm.recipeMain.vm.HBookingViewModel
import com.base.ecomm.roomDb.model.User
import com.jalanjalan.makan.R
import com.jalanjalan.makan.databinding.ActivityRecipeMainBinding
import com.zackratos.ultimatebarx.ultimatebarx.navigationBar
import com.zackratos.ultimatebarx.ultimatebarx.statusBar
import io.getstream.avatarview.coil.loadImage

class RecipeHomeActivity : BaseActivity() {

	private lateinit var binding: ActivityRecipeMainBinding

	private lateinit var hbookingViewModel: HBookingViewModel

	private lateinit var popupWindow: PopupWindow

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_main)

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

		binding.rv.setOnClickListener {
			buttonOneClick(it)
		}

		binding.lyAddRecipe.setOnClickListener {
			buttonOneClick(it)

			val intent = Intent(this@RecipeHomeActivity, RecipeSelectionActivity::class.java)
			val bundle = Bundle()
			intent.putExtras(bundle)
			startActivity(intent)
			overridePendingTransition(R.anim.fade_in, R.anim.no_anim)
		}

		binding.avProfile.setOnClickListener {
			buttonOneClick(it)

			val intent = Intent(this@RecipeHomeActivity, ProfileActivity::class.java)
			val bundle = Bundle()
			intent.putExtras(bundle)
			startActivity(intent)
			overridePendingTransition(R.anim.fade_in, R.anim.no_anim)

			binding.avProfile.loadImage(
				data = "https://cdn-icons-png.flaticon.com/512/4140/4140048.png",
			) {
				crossfade(true)
				crossfade(300)
				transformations(CircleCropTransformation())
				lifecycle(lifecycle)
			}

			binding.tvWelcome.text = getString(R.string.text_welcome) + " " + MyUserManager.instance.userMobileNo

		}

		hbookingViewModel.getAllUsersRecipe.observe(this) {
			binding.rv.adapter =
				BookingRecipeListAdapter(this,
					it,
					hbookingViewModel,
					object : BookingRecipeListAdapter.OnItemClickListener {
						override fun onItemClick(users: User) {

							val intent = Intent(this@RecipeHomeActivity, RecipeDetailActivity::class.java)
							val bundle = Bundle()
							bundle.putParcelable("user", users)
							intent.putExtras(bundle)
							startActivity(intent)
							overridePendingTransition(R.anim.fade_in, R.anim.no_anim)

						}

					})

		}
	}

	override fun onBackPressed() {
		doubleBackToExit()
	}
}