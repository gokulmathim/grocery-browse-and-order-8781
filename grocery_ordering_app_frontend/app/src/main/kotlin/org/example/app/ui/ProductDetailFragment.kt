package org.example.app.ui

import android.app.Fragment
import android.os.Bundle
import android.view.*
import android.widget.*
import org.example.app.R
import org.example.app.data.AppContainer
import org.example.app.model.Product

/**
 * PUBLIC_INTERFACE
 * ProductDetailFragment shows a detailed view of a product and allows the user to add it to the cart.
 */
class ProductDetailFragment : Fragment() {

    private var productId: String? = null
    private var product: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productId = arguments?.getString(ARG_PRODUCT_ID)
        product = productId?.let { AppContainer.repository.getProduct(it) }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater!!.inflate(R.layout.fragment_product_detail, container, false)

        val name = root.findViewById<TextView>(R.id.detail_name)
        val price = root.findViewById<TextView>(R.id.detail_price)
        val desc = root.findViewById<TextView>(R.id.detail_desc)
        val qty = root.findViewById<NumberPicker>(R.id.detail_qty)
        val addBtn = root.findViewById<Button>(R.id.detail_add_to_cart)
        val img = root.findViewById<ImageView>(R.id.detail_image)

        qty.minValue = 1
        qty.maxValue = 20
        img.setImageResource(R.drawable.ic_placeholder)

        product?.let {
            name.text = it.name
            price.text = String.format("$%.2f", it.price)
            desc.text = it.description
        }

        addBtn.setOnClickListener {
            product?.let {
                AppContainer.repository.addToCart(it, qty.value)
                Toast.makeText(activity, "Added to cart", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    companion object {
        private const val ARG_PRODUCT_ID = "product_id"

        /** PUBLIC_INTERFACE Create a new instance of ProductDetailFragment for a given productId. */
        fun newInstance(productId: String): ProductDetailFragment {
            val f = ProductDetailFragment()
            val b = Bundle()
            b.putString(ARG_PRODUCT_ID, productId)
            f.arguments = b
            return f
        }
    }
}
