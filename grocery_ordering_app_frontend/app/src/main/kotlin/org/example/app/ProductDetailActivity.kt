package org.example.app

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.example.app.data.Product
import org.example.app.di.ServiceLocator

/**
 * PUBLIC_INTERFACE
 * ProductDetailActivity shows details of a product and allows adding to cart.
 */
class ProductDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PRODUCT_ID = "extra_product_id"
    }

    private var product: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val title = findViewById<TextView>(R.id.productTitle)
        val desc = findViewById<TextView>(R.id.productDescription)
        val price = findViewById<TextView>(R.id.productPrice)
        val image = findViewById<ImageView>(R.id.productImage)
        val addBtn = findViewById<Button>(R.id.btnAddToCart)

        val repo = ServiceLocator.provideRepository(this)
        val productId = intent.getStringExtra(EXTRA_PRODUCT_ID)

        lifecycleScope.launch {
            product = productId?.let { repo.getProductById(it) }
            product?.let {
                title.text = it.name
                desc.text = it.description
                price.text = "$" + String.format("%.2f", it.price)
                // In a real app, load it.imageUrl using Glide/Picasso; here we use a placeholder
                image.setImageResource(R.drawable.ic_cart)
            }
        }

        addBtn.setOnClickListener {
            product?.let { p ->
                lifecycleScope.launch {
                    repo.addToCart(p, 1)
                    finish()
                }
            }
        }
    }
}
