package com.example.shoppingapp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.shoppingapp.R
import com.example.shoppingapp.model.CategoryModel

class CategoryAdapter(
    private var context: Context,
    private var categories: ArrayList<CategoryModel>
): BaseAdapter() {

    private var layoutInflater: LayoutInflater? = null
    private lateinit var categoryTextView: TextView
    private  lateinit var categoryImageView: ImageView


    override fun getCount(): Int {
        return categories.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var newConvertView = convertView
        val currentCategory = categories[position]

        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }

        if (newConvertView == null) {
            newConvertView = layoutInflater!!.inflate(R.layout.category_card_view, null)
        }

        categoryTextView = newConvertView!!.findViewById(R.id.category_name)
        categoryTextView.text = currentCategory.name
        categoryImageView = newConvertView.findViewById(R.id.category_imageView)
        Glide.with(context).load(currentCategory.image).into(categoryImageView)

        return newConvertView
    }

}