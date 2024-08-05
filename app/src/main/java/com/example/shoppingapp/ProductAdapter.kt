package com.example.shoppingapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppingapp.retrofit.ProductModel

class ProductAdapter(private val context: Context, private val productList: ArrayList<ProductModel>):
RecyclerView.Adapter<ProductAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_product_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentProduct = productList[position]
        holder.productNameTV.text = currentProduct.title
        holder.categoryNameTV.text = currentProduct.category
        Glide.with(holder.imageView.context).load(currentProduct.image).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var productNameTV: TextView
        var categoryNameTV: TextView
        var imageView: ImageView

        init {
            productNameTV = itemView.findViewById(R.id.product_name_textView)
            categoryNameTV = itemView.findViewById(R.id.category_textView)
            imageView = itemView.findViewById(R.id.product_imageView)
        }
    }
}