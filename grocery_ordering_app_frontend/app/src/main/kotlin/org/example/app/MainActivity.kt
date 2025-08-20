package org.example.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * PUBLIC_INTERFACE
 * MainActivity is the single-activity entry point hosting the bottom navigation and fragments.
 * It manages navigation between Home, Cart, Orders, and Profile screens.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_GroceryApp) // ensure splash to theme switch
        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.bottomNavigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> switchFragment(HomeFragment.newInstance())
                R.id.nav_cart -> switchFragment(CartFragment.newInstance())
                R.id.nav_orders -> switchFragment(OrdersFragment.newInstance())
                R.id.nav_profile -> switchFragment(ProfileFragment.newInstance())
                else -> false
            }
        }

        if (savedInstanceState == null) {
            bottomNavigation.selectedItemId = R.id.nav_home
        }

        findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fabCheckout)?.setOnClickListener {
            startActivity(android.content.Intent(this, CheckoutActivity::class.java))
        }
    }

    private fun switchFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
        return true
    }
}
