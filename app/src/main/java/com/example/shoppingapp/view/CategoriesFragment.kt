package com.example.shoppingapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.FragmentCategoriesBinding
import com.example.shoppingapp.viewmodel.MainActivityViewModel
import com.example.shoppingapp.viewmodel.UiState

class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        viewModel.uiState()
            .observe(viewLifecycleOwner, Observer { uiState ->
                if (uiState != null) {
                    render(uiState)
                }
            })

        viewModel.loadCategoriesData()

        return binding.root
    }

    private fun onLoad() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun onSuccess(uiState: UiState.SuccessCategory) = with(binding) {
        progressBar.visibility = View.GONE
        val categoriesGridView = view?.findViewById<GridView>(R.id.categories_gridView)
        val adapter = context?.let { CategoryAdapter(it, uiState.categoriesList) }
//        val adapter = view?.let { CategoryAdapter(it.context, uiState.categoriesList) }
//        adapter?.setOnClickListener(object:
//            CategoryAdapter.OnClickListener {
//            override fun onClick(position: Int, product: CategoryModel) {
//                val bundle = Bundle()
//                bundle.putParcelable("productModel", product)
//
//                val detailsFragment = ProductDetailsFragment()
//                detailsFragment.apply {
//                    arguments = bundle
//                }
//
//                parentFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.fragment_container, detailsFragment)
//                    .addToBackStack(null)
//                    .commit()
//            }
//        }
//        )
        if (categoriesGridView != null) {
            categoriesGridView.adapter = adapter
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
            is UiState.SuccessCategory -> {
                onSuccess(uiState)
            }
            is UiState.Error -> {
                onError(uiState)
            }

            else -> {}
        }
    }
}