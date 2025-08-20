package org.example.app

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.example.app.di.ServiceLocator

/**
 * PUBLIC_INTERFACE
 * CheckoutActivity displays order total and places the order.
 */
class CheckoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val totalText = findViewById<TextView>(R.id.totalAmount)
        val placeBtn = findViewById<Button>(R.id.btnPlaceOrder)

        val repo = ServiceLocator.provideRepository(this)

        lifecycleScope.launch {
            val total = repo.getCartItems().sumOf { it.subtotal }
            totalText.text = "$" + String.format("%.2f", total)
        }

        placeBtn.setOnClickListener {
            lifecycleScope.launch {
                val result = repo.placeOrder()
                result.fold(
                    onSuccess = {
                        Toast.makeText(this@CheckoutActivity, getString(R.string.order_placed), Toast.LENGTH_SHORT).show()
                        finish()
                    },
                    onFailure = {
                        Toast.makeText(this@CheckoutActivity, it.message ?: "Failed to place order", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}
