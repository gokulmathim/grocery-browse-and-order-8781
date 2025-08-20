package org.example.app.ui

import android.app.Fragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.*
import org.example.app.R
import org.example.app.data.AppContainer
import org.example.app.model.Product

/**
 * PUBLIC_INTERFACE
 * HomeFragment shows a search box and a grid of products.
 * Clicking a product opens the ProductDetailFragment.
 */
class HomeFragment : Fragment() {

    private lateinit var searchInput: EditText
    private lateinit var gridView: GridView
    private lateinit var adapter: ProductGridAdapter
    private var products: List<Product> = emptyList()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater!!.inflate(R.layout.fragment_home, container, false)
        searchInput = root.findViewById(R.id.search_input)
        gridView = root.findViewById(R.id.product_grid)
        products = AppContainer.repository.getProducts()

        adapter = ProductGridAdapter(activity, products)
        gridView.adapter = adapter

        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val product = adapter.getItem(position) as Product
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProductDetailFragment.newInstance(product.id))
                .addToBackStack("detail")
                .commit()
        }

        searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val filtered = AppContainer.repository.searchProducts(s?.toString() ?: "")
                adapter.updateData(filtered)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        return root
    }

    private class ProductGridAdapter(
        private val ctx: android.content.Context,
        private var items: List<Product>
    ) : BaseAdapter() {

        fun updateData(newItems: List<Product>) {
            items = newItems
            notifyDataSetChanged()
        }

        override fun getCount(): Int = items.size
        override fun getItem(position: Int): Any = items[position]
        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView ?: LayoutInflater.from(ctx).inflate(R.layout.item_product_grid, parent, false)
            val product = items[position]

            val name = view.findViewById<TextView>(R.id.product_name)
            val price = view.findViewById<TextView>(R.id.product_price)
            val image = view.findViewById<ImageView>(R.id.product_image)

            name.text = product.name
            price.text = String.format("$%.2f", product.price)
            image.setImageResource(R.drawable.ic_placeholder) // using a vector placeholder

            return view
        }
    }
}
