package org.example.app

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import org.example.app.di.ServiceLocator

/**
 * PUBLIC_INTERFACE
 * CartFragment shows items in the cart and allows quantity updates and checkout.
 */
class CartFragment : Fragment() {

    private lateinit var list: RecyclerView
    private lateinit var total: TextView
    private lateinit var adapter: CartAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val v = inflater.inflate(R.layout.fragment_cart, container, false)
        list = v.findViewById(R.id.recyclerCart)
        total = v.findViewById(R.id.cartTotal)
        list.layoutManager = LinearLayoutManager(requireContext())
        adapter = CartAdapter(
            onQuantityChanged = { productId, qty ->
                viewLifecycleOwner.lifecycleScope.launch {
                    ServiceLocator.provideRepository(requireContext()).updateCart(productId, qty)
                    loadCart()
                }
            },
            onRemove = { productId ->
                viewLifecycleOwner.lifecycleScope.launch {
                    ServiceLocator.provideRepository(requireContext()).updateCart(productId, 0)
                    loadCart()
                }
            }
        )
        list.adapter = adapter

        v.findViewById<View>(R.id.btnCheckout)?.setOnClickListener {
            startActivity(Intent(requireContext(), CheckoutActivity::class.java))
        }

        return v
    }

    override fun onResume() {
        super.onResume()
        viewLifecycleOwner.lifecycleScope.launch { loadCart() }
    }

    private suspend fun loadCart() {
        val repo = ServiceLocator.provideRepository(requireContext())
        val items = repo.getCartItems()
        adapter.submit(items)
        val sum = items.sumOf { it.subtotal }
        total.text = getString(R.string.total) + ": $" + String.format("%.2f", sum)
    }

    companion object { fun newInstance() = CartFragment() }
}
