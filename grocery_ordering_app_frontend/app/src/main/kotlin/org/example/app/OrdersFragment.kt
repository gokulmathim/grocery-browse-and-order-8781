package org.example.app

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import org.example.app.data.Order
import org.example.app.di.ServiceLocator

/**
 * PUBLIC_INTERFACE
 * OrdersFragment lists past and current orders and their status.
 */
class OrdersFragment : Fragment() {

    private lateinit var list: RecyclerView
    private lateinit var adapter: OrdersAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val v = inflater.inflate(R.layout.fragment_orders, container, false)
        list = v.findViewById(R.id.recyclerOrders)
        list.layoutManager = LinearLayoutManager(requireContext())
        adapter = OrdersAdapter()
        list.adapter = adapter
        return v
    }

    override fun onResume() {
        super.onResume()
        viewLifecycleOwner.lifecycleScope.launch { loadOrders() }
    }

    private suspend fun loadOrders() {
        val repo = ServiceLocator.provideRepository(requireContext())
        val orders: List<Order> = repo.getOrders()
        adapter.submit(orders)
    }

    companion object { fun newInstance() = OrdersFragment() }
}
