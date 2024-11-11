package com.example.shoppingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.shoppingapp.view.CategoriesFragment
import com.example.shoppingapp.view.FavouriteFragment
import com.example.shoppingapp.view.HomeFragment
import com.example.shoppingapp.view.MoreFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener {item ->
            lateinit var selectedFragment: Fragment
            when(item.itemId) {
                R.id.home_bottom_nav -> selectedFragment = HomeFragment()
                R.id.categories_bottom_nav -> selectedFragment = CategoriesFragment()
                R.id.favourite_bottom_nav -> selectedFragment = FavouriteFragment()
                R.id.more_bottom_nav -> selectedFragment = MoreFragment()
            }

            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit()
            return@setOnItemSelectedListener true
        }

        FirebaseMessaging.getInstance().subscribeToTopic("News")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FCM", "Subscription to News topic successful")
                } else {
                    Log.e("FCM", "Subscription to News topic failed", task.exception)
                }
            }

        //firstly open HomeFragment
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()
    }
}