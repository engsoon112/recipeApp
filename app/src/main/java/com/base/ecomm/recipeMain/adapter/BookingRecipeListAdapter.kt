package com.base.ecomm.recipeMain.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.base.ecomm.recipeMain.vm.HBookingViewModel
import com.base.ecomm.roomDb.model.User
import com.jalanjalan.makan.R
import com.jalanjalan.makan.databinding.ItemStatusBinding

class BookingRecipeListAdapter(
	private val context: Context,
	private val users: List<User>,
	private val viewModel: HBookingViewModel,
	private val listener: OnItemClickListener
) : RecyclerView.Adapter<BookingRecipeListAdapter.ViewHolder>() {

	interface OnItemClickListener {
		fun onItemClick(users: User)
//		fun onItemClick2(string: String)

	}

	override fun getItemCount(): Int {
		return users.size
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {


		holder.mBinding.tvName.text = users[position].recipeType

		holder.mBinding.tvType.text = "Type:" + " " + users[position].mealType

		holder.mBinding.cvBookList.setOnClickListener {

			listener.onItemClick(users[position])
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val layoutInflater =
			if (LayoutInflater.from(parent.context) == null) LayoutInflater.from(parent.context) else LayoutInflater.from(
				parent.context
			)
		val binding: ItemStatusBinding =
			DataBindingUtil.inflate(layoutInflater, R.layout.item_status, parent, false)

		return ViewHolder(binding)
	}

	inner class ViewHolder(private val binding: ItemStatusBinding) :
		RecyclerView.ViewHolder(binding.root) {
		var mBinding = binding
	}

}
