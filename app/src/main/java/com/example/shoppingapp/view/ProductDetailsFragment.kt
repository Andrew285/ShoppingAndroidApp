package com.example.shoppingapp.view

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.FragmentProductDetailsBinding
import com.example.shoppingapp.model.ProductModel

class ProductDetailsFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailsBinding
    private lateinit var productModel: ProductModel

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            productModel = it.getParcelable("productModel", ProductModel::class.java)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)

        binding.apply {
            productDetailsName.text = productModel.title
            categoryDetailsName.text = productModel.category
            productPrice.text = "$" + productModel.price.toString()
            productDescription.text = productModel.description
            ratingBar.rating = productModel.rating!!.rate
            countReviews.text = productModel.rating!!.count.toString() + " Reviews"
            Glide.with(requireContext()).load(productModel.image).into(productImageView)
        }

        return binding.root
    }
}