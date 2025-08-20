package org.example.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.example.app.data.CartItem
import org.example.app.data.Order
import org.example.app.data.Product

/**
 * Adapter for product grid.
 */
class ProductAdapter(
    private val onProductClick: (Product) -> Unit,
    private val onAddToCartClick: (Product) -> Unit
) : ListAdapter<Product, ProductAdapter.VH>(Diff) {

    object Diff : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
    }

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val title: TextView = v.findViewById(R.id.itemTitle)
        val price: TextView = v.findViewById(R.id.itemPrice)
        val image: ImageView = v.findViewById(R.id.itemImage)
        val add: ImageButton = v.findViewById(R.id.btnAddCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        holder.title.text = item.name
        holder.price.text = "$" + String.format("%.2f", item.price)
        holder.image.setImageResource(R.drawable.ic_cart)
        holder.itemView.setOnClickListener { onProductClick(item) }
        holder.add.setOnClickListener { onAddToCartClick(item) }
    }

    fun submit(list: List<Product>) = submitList(list)
}

/**
 * Adapter for cart list.
 */
class CartAdapter(
    private val onQuantityChanged: (productId: String, newQuantity: Int) -> Unit,
    private val onRemove: (productId: String) -> Unit
) : ListAdapter<CartItem, CartAdapter.VH>(Diff) {

    object Diff : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem) = oldItem.product.id == newItem.product.id
        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem) = oldItem == newItem
    }

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val title: TextView = v.findViewById(R.id.cartItemTitle)
        val price: TextView = v.findViewById(R.id.cartItemPrice)
        val qty: TextView = v.findViewById(R.id.cartItemQty)
        val minus: ImageButton = v.findViewById(R.id.btnMinus)
        val plus: ImageButton = v.findViewById(R.id.btnPlus)
        val remove: ImageButton = v.findViewById(R.id.btnRemove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        holder.title.text = item.product.name
        holder.price.text = "$" + String.format("%.2f", item.subtotal)
        holder.qty.text = item.quantity.toString()
        holder.minus.setOnClickListener {
            val newQty = (holder.qty.text.toString().toIntOrNull() ?: 1) - 1
            onQuantityChanged(item.product.id, newQty)
        }
        holder.plus.setOnClickListener {
            val newQty = (holder.qty.text.toString().toIntOrNull() ?: 1) + 1
            onQuantityChanged(item.product.id, newQty)
        }
        holder.remove.setOnClickListener { onRemove(item.product.id) }
    }

    fun submit(list: List<CartItem>) = submitList(list)
}

/**
 * Adapter for orders list.
 */
class OrdersAdapter : ListAdapter<Order, OrdersAdapter.VH>(Diff) {

    object Diff : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Order, newItem: Order) = oldItem == newItem
    }

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val title: TextView = v.findViewById(R.id.orderTitle)
        val status: TextView = v.findViewById(R.id.orderStatus)
        val total: TextView = v.findViewById(R.id.orderTotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        holder.title.text = "Order #" + item.id.takeLast(6)
        holder.status.text = item.status.name
        holder.total.text = "$" + String.format("%.2f", item.total)
    }

    fun submit(list: List<Order>) = submitList(list)
}
