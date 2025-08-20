package org.example.app.data

import org.example.app.model.*
import java.util.UUID
import kotlin.math.round

/**
 * PUBLIC_INTERFACE
 * Repository defines the operations used by UI to access products, cart, orders and user auth.
 * This demo implementation is in-memory and stateful for app lifetime.
 */
interface Repository {
    /** PUBLIC_INTERFACE Search products by query string; empty query returns all products. */
    fun searchProducts(query: String = ""): List<Product>

    /** PUBLIC_INTERFACE Get a product by ID. Returns null if not found. */
    fun getProduct(productId: String): Product?

    /** PUBLIC_INTERFACE Get all products (optionally filtered by category). */
    fun getProducts(category: String? = null): List<Product>

    /** PUBLIC_INTERFACE Get current cart items. */
    fun getCart(): List<CartItem>

    /** PUBLIC_INTERFACE Add product to cart with quantity (aggregates if existing). */
    fun addToCart(product: Product, qty: Int = 1)

    /** PUBLIC_INTERFACE Update quantity of existing cart item. */
    fun updateCartItem(productId: String, qty: Int)

    /** PUBLIC_INTERFACE Remove a product from cart. */
    fun removeFromCart(productId: String)

    /** PUBLIC_INTERFACE Clear all items from cart. */
    fun clearCart()

    /** PUBLIC_INTERFACE Place order using current cart. Returns created order or null if cart empty. */
    fun placeOrder(): Order?

    /** PUBLIC_INTERFACE List past orders (most recent first). */
    fun listOrders(): List<Order>

    /** PUBLIC_INTERFACE Get current authenticated user (or null). */
    fun currentUser(): User?

    /** PUBLIC_INTERFACE Sign in with email and password (mock). Creates a fake user on success. */
    fun signIn(email: String, password: String): Boolean

    /** PUBLIC_INTERFACE Sign up (mock). Returns true on success and signs in the user. */
    fun signUp(email: String, password: String, displayName: String): Boolean

    /** PUBLIC_INTERFACE Sign out current user and clear transient state if needed. */
    fun signOut()

    /** PUBLIC_INTERFACE Update profile of current user. */
    fun updateProfile(displayName: String, address: String?): Boolean

    /** PUBLIC_INTERFACE Compute cart total. */
    fun cartTotal(): Double
}

/**
 * InMemoryRepository provides a simple in-memory data store for demo purposes.
 */
class InMemoryRepository : Repository {

    private val seedProducts: List<Product> by lazy {
        listOf(
            Product("p-apple", "Apples", "Fresh red apples by the pound.", 2.49, null, "Fruits"),
            Product("p-banana", "Bananas", "Organic bananas, rich in potassium.", 1.29, null, "Fruits"),
            Product("p-milk", "Milk", "2% Reduced Fat Milk 1 Gallon.", 3.59, null, "Dairy"),
            Product("p-eggs", "Eggs", "Free-range large eggs, dozen.", 4.19, null, "Dairy"),
            Product("p-bread", "Whole Wheat Bread", "Healthy whole grain bread loaf.", 2.99, null, "Bakery"),
            Product("p-chicken", "Chicken Breast", "Boneless skinless chicken breast.", 6.49, null, "Meat"),
            Product("p-tomato", "Tomatoes", "Vine-ripened tomatoes.", 1.99, null, "Vegetables"),
            Product("p-lettuce", "Romaine Lettuce", "Crisp romaine hearts.", 2.49, null, "Vegetables"),
            Product("p-cheese", "Cheddar Cheese", "Sharp cheddar block 8oz.", 3.99, null, "Dairy"),
            Product("p-yogurt", "Greek Yogurt", "Plain Greek yogurt 32oz.", 5.49, null, "Dairy"),
        )
    }

    private val cart = LinkedHashMap<String, CartItem>()
    private val orders = mutableListOf<Order>()
    private var user: User? = null

    override fun searchProducts(query: String): List<Product> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return seedProducts
        return seedProducts.filter { p ->
            p.name.lowercase().contains(q) || p.description.lowercase().contains(q) || p.category.lowercase().contains(q)
        }
    }

    override fun getProduct(productId: String): Product? = seedProducts.find { it.id == productId }

    override fun getProducts(category: String?): List<Product> {
        return if (category.isNullOrBlank()) seedProducts else seedProducts.filter { it.category.equals(category, true) }
    }

    override fun getCart(): List<CartItem> = cart.values.toList()

    override fun addToCart(product: Product, qty: Int) {
        val current = cart[product.id]
        if (current == null) {
            cart[product.id] = CartItem(product, qty.coerceAtLeast(1))
        } else {
            current.quantity = (current.quantity + qty).coerceAtLeast(1)
        }
    }

    override fun updateCartItem(productId: String, qty: Int) {
        if (qty <= 0) {
            cart.remove(productId)
        } else {
            cart[productId]?.let { it.quantity = qty }
        }
    }

    override fun removeFromCart(productId: String) {
        cart.remove(productId)
    }

    override fun clearCart() {
        cart.clear()
    }

    override fun placeOrder(): Order? {
        if (cart.isEmpty()) return null
        val items = cart.values.map { CartItem(it.product, it.quantity) }
        val total = cartTotal()
        val order = Order(
            id = UUID.randomUUID().toString(),
            items = items,
            totalAmount = round(total * 100) / 100.0,
            status = OrderStatus.PLACED,
            placedAtMillis = System.currentTimeMillis()
        )
        orders.add(0, order)
        clearCart()
        return order
    }

    override fun listOrders(): List<Order> = orders.toList()

    override fun currentUser(): User? = user

    override fun signIn(email: String, password: String): Boolean {
        // Mock sign-in: accept any non-empty credentials
        val ok = email.isNotBlank() && password.length >= 4
        if (ok) {
            user = User(userId = UUID.randomUUID().toString(), email = email, displayName = email.substringBefore("@"))
        }
        return ok
    }

    override fun signUp(email: String, password: String, displayName: String): Boolean {
        val ok = email.isNotBlank() && password.length >= 6 && displayName.isNotBlank()
        if (ok) {
            user = User(userId = UUID.randomUUID().toString(), email = email, displayName = displayName)
        }
        return ok
    }

    override fun signOut() {
        user = null
        // Keep orders history for demo; in real app, might clear or refetch after auth change
    }

    override fun updateProfile(displayName: String, address: String?): Boolean {
        val curr = user ?: return false
        user = curr.copy(displayName = displayName.ifBlank { curr.displayName }, address = address)
        return true
    }

    override fun cartTotal(): Double {
        return cart.values.sumOf { it.product.price * it.quantity }
    }
}

/**
 * PUBLIC_INTERFACE
 * AppContainer holds singletons for easy access by Activities and Fragments.
 */
object AppContainer {
    val repository: Repository by lazy { InMemoryRepository() }
}
