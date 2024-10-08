package com.example.shoppingapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.FragmentHomeBinding
import com.example.shoppingapp.model.ProductModel
import com.example.shoppingapp.viewmodel.MainActivityViewModel
import com.example.shoppingapp.viewmodel.UiState

class HomeFragment : Fragment() {

    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)

        binding.shoppingCartImageButton.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.fragment_container, CartFragment()).commit()
        }

        viewModel.uiState()
            .observe(viewLifecycleOwner, Observer { uiState ->
                if (uiState != null) {
                    render(uiState)
                }
            })
        viewModel.loadProductData()

        return binding.root
    }

    private fun onLoad() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun onSuccess(uiState: UiState.Success) = with(binding) {
        progressBar.visibility = View.GONE
        val recommendedProductsRV = view?.findViewById<RecyclerView>(R.id.recommended_recyclerView)
        val linearLayoutManager = LinearLayoutManager(view?.context, RecyclerView.HORIZONTAL, false)
        val adapter = view?.let { ProductAdapter(uiState.productsList) }
        adapter?.setOnClickListener(object:
            ProductAdapter.OnClickListener {
            override fun onClick(position: Int, product: ProductModel) {
                    val bundle = Bundle()
                    bundle.putParcelable("productModel", product)

                    val detailsFragment = ProductDetailsFragment()
                    detailsFragment.apply {
                        arguments = bundle
                    }

                    parentFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, detailsFragment)
                        .addToBackStack(null)
                        .commit()
                }
            }
        )
        if (recommendedProductsRV != null) {
            recommendedProductsRV.layoutManager = linearLayoutManager
        }
        if (recommendedProductsRV != null) {
            recommendedProductsRV.adapter = adapter
        }
    }

    private fun onError(uiState: UiState.Error) = with(binding) {
        progressBar.visibility = View.GONE
        Toast.makeText(context, uiState.message, Toast.LENGTH_LONG).show()
    }

    private fun render(uiState: UiState) {
        when (uiState) {
            is UiState.Loading -> {
                onLoad()
            }
            is UiState.Success -> {
                onSuccess(uiState)
            }
            is UiState.Error -> {
                onError(uiState)
            }

            else -> {}
        }
    }
}