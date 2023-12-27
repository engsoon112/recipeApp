package com.base.ecomm.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.jalanjalan.makan.R
import com.jalanjalan.makan.databinding.FragmentBlankBinding


class BlankFragment : Fragment() {

//    lateinit var activity: HomeActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var binding: FragmentBlankBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_blank, container, false)
//        activity = getActivity() as HomeActivity

        bindingView()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

    }

    private fun bindingView() {

    }

    companion object {
        @JvmStatic
        fun newInstance() = BlankFragment().apply {
            arguments = Bundle().apply {}
        }
    }

}
