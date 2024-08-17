package com.example.shoppingapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.FragmentCartBinding
import com.example.shoppingapp.viewmodel.CartViewModel

class CartFragment : Fragment() {
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

        val cartProducts = cartViewModel.cartProducts.value
        val adapter = cartProducts?.let { CartAdapter(requireContext(), it) }

        binding.cartItemsRecyclerView.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(view?.context, RecyclerView.HORIZONTAL, false)
        binding.cartItemsRecyclerView.layoutManager = linearLayoutManager

        return binding.root
    }
}