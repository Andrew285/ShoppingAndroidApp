package com.example.shoppingapp.view

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.FragmentProductDetailsBinding
import com.example.shoppingapp.model.ProductModel
import com.example.shoppingapp.viewmodel.CartViewModel
import kotlin.random.Random

class ProductDetailsFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailsBinding
    private lateinit var productModel: ProductModel
    private val cartViewModel: CartViewModel by viewModels()

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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            productDetailsName.text = productModel.title
            categoryDetailsName.text = productModel.category?.name
            productPrice.text = "$" + productModel.price.toString()
            productDescription.text = productModel.description
            ratingBar.rating = Random.nextFloat() * 5
            countReviews.text = Random.nextInt(0, 2000).toString() + " Reviews"

            val trimmedImageString = productModel.images[0]
                .replace("[", "")
                .replace("]", "")
                .replace("\"", "")

            context?.let {
                Glide.with(it)
                    .load(trimmedImageString)
                    .into(binding.productImageView)
            }

            addToCartBtn.setOnClickListener {
                cartViewModel.addToCart(productModel)
                Toast.makeText(requireContext(), "Product is added to cart", Toast.LENGTH_LONG).show()
            }
        }
    }
}