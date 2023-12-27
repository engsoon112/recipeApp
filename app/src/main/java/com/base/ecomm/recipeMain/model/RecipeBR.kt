package com.base.ecomm.recipeMain.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.base.ecomm.utils.BindableDelegates

class RecipeBR : BaseObservable() {

	@get:Bindable
	var recipeType: String by BindableDelegates("", BR.recipeType)

	@get:Bindable
	var mealType: String by BindableDelegates("", BR.mealType)

	@get:Bindable
	var buttonClick: Boolean by BindableDelegates(false, BR.buttonClick)

	val buttonValidate: Boolean
		@Bindable("recipeType", "mealType") get() {
			return !recipeTypeValid && !mealTypeValid
		}

	val recipeTypeValid: Boolean
		@Bindable("buttonClick", "recipeType")
		get() = buttonClick && recipeType.isEmpty()

	val mealTypeValid: Boolean
		@Bindable("buttonClick", "mealType")
		get() = buttonClick && mealType.isEmpty()
}