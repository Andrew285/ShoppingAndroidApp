package com.example.shoppingapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.FragmentCartBinding
import com.example.shoppingapp.model.ProductModel
import com.example.shoppingapp.viewmodel.CartViewModel

class CartFragment : Fragment(), IProductAmountChanged {
    private lateinit var binding: FragmentCartBinding
    private val cartViewModel: CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false)

        cartViewModel.cartProducts.observe(viewLifecycleOwner, Observer { cartProducts ->
            if (cartProducts != null) {
                // Set up RecyclerView adapter and layout manager here
                val adapter = CartAdapter(cartProducts, this)
                binding.cartItemsRecyclerView.adapter = adapter

                val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                binding.cartItemsRecyclerView.layoutManager = linearLayoutManager
            }

            cartViewModel.calculatePrices()
        })

        cartViewModel.subTotalPrice.observe(viewLifecycleOwner, Observer {subTotalPrice ->
            binding.subtotalPriceTextView.text = subTotalPrice.toString()
        })

        cartViewModel.deliveryPrice.observe(viewLifecycleOwner, Observer {deliveryPrice ->
            binding.deliveryPriceTextView.text = deliveryPrice.toString()
        })

        cartViewModel.totalPrice.observe(viewLifecycleOwner, Observer {totalPrice ->
            binding.totalPriceTextView.text = totalPrice.toString()
        })

        return binding.root
    }

    override fun onChangeAmountListener(product: ProductModel, amount: Int) {
        cartViewModel.calculatePrices(product, amount)
    }
}