package com.base.ecomm.recipeMain.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.base.ecomm.MyApplication
import com.base.ecomm.data.MyUserManager
import com.base.ecomm.recipeMain.model.RecipeBR
import com.base.ecomm.roomDb.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HBookingViewModel  : ViewModel() {

	var recipeBR = RecipeBR()

	//init roomDb dao
	val userRecipeDao = MyApplication.localDatabase.userRecipeDao()

	//query
	val getAllUsersRecipe: LiveData<List<User>> = userRecipeDao.getAllUsersRecipe()

	var receivedUser: User? = null

	fun insertRecipeDb() {
		viewModelScope.launch(Dispatchers.IO) {

			val userData = User(recipeType = MyUserManager.instance.recipeType ?: "",
				mealType = MyUserManager.instance.mealType ?: "",)
			userRecipeDao.insertUser(userData)
		}
	}

	fun updateRecipeDb(recipeId: Long) {
		val newRecipeType = MyUserManager.instance.recipeType ?: ""
		val newMealType = MyUserManager.instance.mealType ?: ""

		viewModelScope.launch(Dispatchers.IO) {
			userRecipeDao.updateUserById(recipeId, newRecipeType, newMealType)
		}
	}

	fun deleteRecipeById(recipeId: Long) {
		viewModelScope.launch {
			val recipe = userRecipeDao.getRecipeById(recipeId)

			recipe?.let {
				userRecipeDao.deleteUserById(recipeId)
			}
		}
	}





}