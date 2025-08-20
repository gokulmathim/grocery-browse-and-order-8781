package org.example.app.ui

import android.app.Fragment
import android.os.Bundle
import android.view.*
import android.widget.*
import org.example.app.R
import org.example.app.data.AppContainer
import org.example.app.model.CartItem

/**
 * PUBLIC_INTERFACE
 * CartFragment lists cart items with quantity controls and shows the total.
 * Checkout is handled by MainActivity's FAB for a centralized action.
 */
class CartFragment : Fragment() {

    private lateinit var listView: ListView
    private lateinit var totalText: TextView
    private lateinit var adapter: CartAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater!!.inflate(R.layout.fragment_cart, container, false)
        listView = root.findViewById(R.id.cart_list)
        totalText = root.findViewById(R.id.cart_total)

        adapter = CartAdapter()
        listView.adapter = adapter

        refresh()
        return root
    }

    private fun refresh() {
        adapter.setItems(AppContainer.repository.getCart())
        totalText.text = String.format("Total: $%.2f", AppContainer.repository.cartTotal())
    }

    private inner class CartAdapter : BaseAdapter() {
        private var items: List<CartItem> = emptyList()

        fun setItems(newItems: List<CartItem>) {
            items = newItems
            notifyDataSetChanged()
        }

        override fun getCount(): Int = items.size
        override fun getItem(position: Int): Any = items[position]
        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val v = convertView ?: layoutInflater.inflate(R.layout.item_cart, parent, false)
            val item = items[position]

            val name = v.findViewById<TextView>(R.id.cart_item_name)
            val price = v.findViewById<TextView>(R.id.cart_item_price)
            val qty = v.findViewById<EditText>(R.id.cart_item_qty)
            val remove = v.findViewById<ImageButton>(R.id.cart_item_remove)

            name.text = item.product.name
            price.text = String.format("$%.2f", item.product.price * item.quantity)
            qty.setText(item.quantity.toString())

            remove.setOnClickListener {
                AppContainer.repository.removeFromCart(item.product.id)
                refresh()
            }

            v.findViewById<ImageButton>(R.id.cart_qty_plus).setOnClickListener {
                AppContainer.repository.updateCartItem(item.product.id, item.quantity + 1)
                refresh()
            }
            v.findViewById<ImageButton>(R.id.cart_qty_minus).setOnClickListener {
                AppContainer.repository.updateCartItem(item.product.id, (item.quantity - 1).coerceAtLeast(0))
                refresh()
            }

            return v
        }
    }
}
