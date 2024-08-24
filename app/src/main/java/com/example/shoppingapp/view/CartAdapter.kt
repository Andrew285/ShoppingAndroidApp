package com.example.shoppingapp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.CartItemLayoutBinding
import com.example.shoppingapp.model.ProductModel
import com.example.shoppingapp.viewmodel.CartViewModel

class CartAdapter(
    private val cartProducts: List<ProductModel>
): RecyclerView.Adapter<CartAdapter.CartViewHolder>() {


    class CartViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var productImageView: ImageView
        var productNameTextView: TextView
        var productPriceTextView: TextView

        init {
            productImageView = itemView.findViewById(R.id.cart_product_imageView)
            productNameTextView = itemView.findViewById(R.id.cart_product_name_textView)
            productPriceTextView = itemView.findViewById(R.id.cart_product_price_textView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return cartProducts.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = cartProducts[position]

        holder.productNameTextView.text = product.title
        holder.productPriceTextView.text = product.price.toString()

        val trimmedImageString = product.images[0]
            .replace("[", "")
            .replace("]", "")
            .replace("\"", "")

        Glide.with(holder.productImageView.context)
            .load(trimmedImageString)
            .into(holder.productImageView)
    }
}