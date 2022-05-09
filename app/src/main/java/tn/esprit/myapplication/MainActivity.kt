package tn.esprit.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import tn.esprit.myapplication.Fragements.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide();

        val homeFragment = Home_Fragement()
        val profileFragement = Profile_Fragement()
        val settingsFragment = Setting_Fragement()
        val shoppingFragements = Shopping_Fragements()
        val wishlistFragements = Wishlist_Fragements()


        makeCurrentFragment(homeFragment)

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.Home -> {
                    // Respond to navigation item 1 click
                    makeCurrentFragment(homeFragment)
                    true
                }
                R.id.Wishlist -> {
                    makeCurrentFragment(wishlistFragements)
                    // Respond to navigation item 2 click
                    true
                }
                R.id.Cart -> {
                    makeCurrentFragment(shoppingFragements)
                    // Respond to navigation item 2 click
                    true
                }
                R.id.Profile -> {
                    makeCurrentFragment(profileFragement)
                    // Respond to navigation item 2 click
                    true
                }
                R.id.Setting -> {
                    makeCurrentFragment(settingsFragment)
                    // Respond to navigation item 2 click
                    true
                }


                else -> false
            }
        }

    }

    private fun makeCurrentFragment(fragment: Fragment)= supportFragmentManager.beginTransaction().apply{
        replace(R.id.container,fragment)
        commit()
    }
}