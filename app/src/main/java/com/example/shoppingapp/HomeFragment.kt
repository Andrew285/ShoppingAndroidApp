package com.example.shoppingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.databinding.FragmentHomeBinding
import com.example.shoppingapp.viewmodel.MainActivityViewModel
import com.example.shoppingapp.viewmodel.UiState

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false);
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
        val adapter = view?.let { ProductAdapter(it.context, uiState.productsList) }
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
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}