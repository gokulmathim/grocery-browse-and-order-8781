package org.example.app

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.example.app.data.AppContainer
import org.example.app.ui.CartFragment
import org.example.app.ui.HomeFragment
import org.example.app.ui.OrdersFragment
import org.example.app.ui.ProfileFragment
import org.example.app.R

/**
 * PUBLIC_INTERFACE
 * MainActivity sets up the bottom navigation and the FAB for checkout.
 * It swaps between Home, Cart, Orders, and Profile fragments.
 */
class MainActivity : Activity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var fabCheckout: FloatingActionButton
    private lateinit var container: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottom_navigation)
        fabCheckout = findViewById(R.id.fab_checkout)
        container = findViewById(R.id.fragment_container)

        bottomNav.setOnNavigationItemSelectedListener(this)
        bottomNav.selectedItemId = R.id.nav_home

        fabCheckout.setOnClickListener {
            val repo = AppContainer.repository
            if (repo.getCart().isEmpty()) {
                Toast.makeText(this, "Your cart is empty.", Toast.LENGTH_SHORT).show()
            } else {
                val order = repo.placeOrder()
                if (order != null) {
                    Toast.makeText(this, "Order placed! #${order.id.take(6)}", Toast.LENGTH_LONG).show()
                    // Switch to orders tab after placing order
                    bottomNav.selectedItemId = R.id.nav_orders
                } else {
                    Toast.makeText(this, "Unable to place order.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val frag = when (item.itemId) {
            R.id.nav_home -> HomeFragment()
            R.id.nav_cart -> CartFragment()
            R.id.nav_orders -> OrdersFragment()
            R.id.nav_profile -> ProfileFragment()
            else -> HomeFragment()
        }
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, frag)
            .commit()
        fabCheckout.visibility = if (item.itemId == R.id.nav_cart) View.VISIBLE else View.GONE
        return true
    }
}
