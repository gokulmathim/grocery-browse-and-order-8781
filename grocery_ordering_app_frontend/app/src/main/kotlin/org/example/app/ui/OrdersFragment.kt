package org.example.app.ui

import android.app.Fragment
import android.os.Bundle
import android.view.*
import android.widget.*
import org.example.app.R
import org.example.app.data.AppContainer
import org.example.app.model.Order

/**
 * PUBLIC_INTERFACE
 * OrdersFragment displays previous orders with totals and statuses.
 */
class OrdersFragment : Fragment() {

    private lateinit var listView: ListView
    private lateinit var emptyView: TextView
    private lateinit var adapter: OrdersAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater!!.inflate(R.layout.fragment_orders, container, false)
        listView = root.findViewById(R.id.orders_list)
        emptyView = root.findViewById(R.id.orders_empty)
        adapter = OrdersAdapter()
        listView.adapter = adapter
        refresh()
        return root
    }

    private fun refresh() {
        val orders = AppContainer.repository.listOrders()
        adapter.setItems(orders)
        emptyView.visibility = if (orders.isEmpty()) View.VISIBLE else View.GONE
    }

    private inner class OrdersAdapter : BaseAdapter() {
        private var items: List<Order> = emptyList()

        fun setItems(newItems: List<Order>) {
            items = newItems
            notifyDataSetChanged()
        }

        override fun getCount(): Int = items.size
        override fun getItem(position: Int): Any = items[position]
        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val v = convertView ?: layoutInflater.inflate(R.layout.item_order, parent, false)
            val o = items[position]

            v.findViewById<TextView>(R.id.order_id).text = "Order #${o.id.take(6)}"
            v.findViewById<TextView>(R.id.order_total).text = String.format("$%.2f", o.totalAmount)
            v.findViewById<TextView>(R.id.order_status).text = o.status.name.replace('_', ' ')
            v.findViewById<TextView>(R.id.order_time).text = android.text.format.DateFormat.format("MMM d, h:mm a", o.placedAtMillis)

            return v
        }
    }
}
