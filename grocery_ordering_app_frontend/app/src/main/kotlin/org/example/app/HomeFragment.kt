package org.example.app

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.app.data.Product
import org.example.app.di.ServiceLocator

/**
 * PUBLIC_INTERFACE
 * HomeFragment shows product grid with search box.
 */
class HomeFragment : Fragment() {

    private lateinit var productsList: RecyclerView
    private lateinit var searchBox: EditText
    private lateinit var adapter: ProductAdapter

    private var searchJob: Job? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val v = inflater.inflate(R.layout.fragment_home, container, false)
        productsList = v.findViewById(R.id.recyclerProducts)
        searchBox = v.findViewById(R.id.searchBox)
        productsList.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = ProductAdapter(
            onProductClick = { openDetails(it) },
            onAddToCartClick = { addToCart(it) }
        )
        productsList.adapter = adapter

        searchBox.addTextChangedListener {
            val query = it?.toString()
            searchJob?.cancel()
            searchJob = viewLifecycleOwner.lifecycleScope.launch {
                delay(250)
                loadProducts(query)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            loadProducts(null)
        }

        v.findViewById<View>(R.id.fabCheckoutHome)?.setOnClickListener {
            startActivity(Intent(requireContext(), CheckoutActivity::class.java))
        }

        return v
    }

    private suspend fun loadProducts(query: String?) {
        val repo = ServiceLocator.provideRepository(requireContext())
        val list = repo.searchProducts(query)
        adapter.submit(list)
    }

    private fun openDetails(product: Product) {
        val intent = Intent(requireContext(), ProductDetailActivity::class.java)
        intent.putExtra(ProductDetailActivity.EXTRA_PRODUCT_ID, product.id)
        startActivity(intent)
    }

    private fun addToCart(product: Product) {
        viewLifecycleOwner.lifecycleScope.launch {
            ServiceLocator.provideRepository(requireContext()).addToCart(product, 1)
        }
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}
