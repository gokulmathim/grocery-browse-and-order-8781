package org.example.app.data

/**
 * PUBLIC_INTERFACE
 * Repository interface to abstract data operations for auth, products, cart, and orders.
 */
interface GroceryRepository {
    suspend fun login(email: String, password: String): Result<UserProfile>
    suspend fun signup(name: String, email: String, password: String): Result<UserProfile>
    suspend fun getCurrentUser(): UserProfile?
    suspend fun logout()

    suspend fun searchProducts(query: String?): List<Product>
    suspend fun getProductById(id: String): Product?

    suspend fun getCartItems(): List<CartItem>
    suspend fun addToCart(product: Product, quantity: Int = 1)
    suspend fun updateCart(productId: String, quantity: Int)
    suspend fun clearCart()

    suspend fun placeOrder(): Result<Order>
    suspend fun getOrders(): List<Order>
    suspend fun trackOrder(orderId: String): Order?
}

/**
 * PUBLIC_INTERFACE
 * InMemoryGroceryRepository is a drop-in implementation for demo and UI development.
 * It can be replaced by a real implementation that talks to the backend.
 */
class InMemoryGroceryRepository(
    private val baseUrl: String? = null // reserved for future backend wiring
) : GroceryRepository {

    private var user: UserProfile? = null
    private val catalog = mutableListOf<Product>()
    private val cart = mutableListOf<CartItem>()
    private val orders = mutableListOf<Order>()

    init {
        // Seed catalog with sample data
        val sample = listOf(
            Product(name = "Apples", description = "Fresh red apples", price = 2.49, category = "Fruits"),
            Product(name = "Bananas", description = "Ripe bananas", price = 1.19, category = "Fruits"),
            Product(name = "Milk", description = "Organic whole milk 1L", price = 3.49, category = "Dairy"),
            Product(name = "Bread", description = "Whole wheat bread", price = 2.29, category = "Bakery"),
            Product(name = "Eggs", description = "Free-range eggs (12)", price = 4.99, category = "Dairy"),
            Product(name = "Carrots", description = "Organic carrots 1kg", price = 1.99, category = "Vegetables"),
            Product(name = "Chicken Breast", description = "Boneless chicken breast 500g", price = 6.99, category = "Meat"),
            Product(name = "Orange Juice", description = "No pulp 1L", price = 2.99, category = "Beverages")
        )
        catalog.addAll(sample)
    }

    override suspend fun login(email: String, password: String): Result<UserProfile> {
        // Placeholder auth check
        if (email.isNotBlank() && password.isNotBlank()) {
            user = UserProfile(name = email.substringBefore("@").replaceFirstChar { it.uppercase() }, email = email)
            return Result.success(user!!)
        }
        return Result.failure(IllegalArgumentException("Invalid credentials"))
    }

    override suspend fun signup(name: String, email: String, password: String): Result<UserProfile> {
        if (name.isNotBlank() && email.contains("@") && password.length >= 6) {
            user = UserProfile(name = name, email = email)
            return Result.success(user!!)
        }
        return Result.failure(IllegalArgumentException("Invalid signup details"))
    }

    override suspend fun getCurrentUser(): UserProfile? = user

    override suspend fun logout() {
        user = null
        cart.clear()
    }

    override suspend fun searchProducts(query: String?): List<Product> {
        if (query.isNullOrBlank()) return catalog.toList()
        val q = query.trim().lowercase()
        return catalog.filter { it.name.lowercase().contains(q) || it.category.lowercase().contains(q) }
    }

    override suspend fun getProductById(id: String): Product? = catalog.firstOrNull { it.id == id }

    override suspend fun getCartItems(): List<CartItem> = cart.toList()

    override suspend fun addToCart(product: Product, quantity: Int) {
        val idx = cart.indexOfFirst { it.product.id == product.id }
        if (idx >= 0) {
            val existing = cart[idx]
            cart[idx] = existing.copy(quantity = existing.quantity + quantity)
        } else {
            cart.add(CartItem(product, quantity))
        }
    }

    override suspend fun updateCart(productId: String, quantity: Int) {
        val idx = cart.indexOfFirst { it.product.id == productId }
        if (idx >= 0) {
            if (quantity <= 0) cart.removeAt(idx) else cart[idx] = cart[idx].copy(quantity = quantity)
        }
    }

    override suspend fun clearCart() {
        cart.clear()
    }

    override suspend fun placeOrder(): Result<Order> {
        if (cart.isEmpty()) return Result.failure(IllegalStateException("Cart is empty"))
        val total = cart.sumOf { it.subtotal }
        val order = Order(items = cart.toList(), total = total)
        orders.add(order)
        cart.clear()
        return Result.success(order)
    }

    override suspend fun getOrders(): List<Order> = orders.toList()

    override suspend fun trackOrder(orderId: String): Order? = orders.firstOrNull { it.id == orderId }
}
