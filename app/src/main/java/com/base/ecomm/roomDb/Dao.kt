package com.base.ecomm.roomDb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.base.ecomm.roomDb.model.User

@Dao
interface UserRecipeDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertUser(user: User)

	@Update
	suspend fun updateUser(user: User)

	@Delete
	suspend fun deleteUser(user: User)

	@Query("UPDATE userRecipe SET recipeType = :newRecipeType, mealType = :newMealType WHERE id = :userId")
	suspend fun updateUserById(userId: Long, newRecipeType: String, newMealType: String)

	@Query("DELETE FROM userRecipe WHERE id = :userId")
	suspend fun deleteUserById(userId: Long)

	@Query("SELECT * FROM userRecipe ORDER BY id DESC")
	fun getAllUsersRecipe(): LiveData<List<User>>

	@Query("SELECT * FROM userRecipe WHERE id = :id")
	fun getRecipeById(id: Long): LiveData<User>


}