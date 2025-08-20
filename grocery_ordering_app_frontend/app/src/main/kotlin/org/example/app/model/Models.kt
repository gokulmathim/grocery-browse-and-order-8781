package org.example.app.model

/**
 * PUBLIC_INTERFACE
 * Data class representing a product in the grocery catalog.
 * id: unique identifier
 * name: product display name
 * description: longer description
 * price: unit price
 * imageUrl: optional image url (placeholder for demo)
 * category: product category
 * inStock: availability
 */
data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String? = null,
    val category: String,
    val inStock: Boolean = true
)

/**
 * PUBLIC_INTERFACE
 * Represents a single item added to the cart.
 */
data class CartItem(
    val product: Product,
    var quantity: Int
)

/**
 * PUBLIC_INTERFACE
 * Represents a placed order summary.
 */
data class Order(
    val id: String,
    val items: List<CartItem>,
    val totalAmount: Double,
    val status: OrderStatus,
    val placedAtMillis: Long
)

/**
 * PUBLIC_INTERFACE
 * Possible order status states.
 */
enum class OrderStatus {
    PLACED, PROCESSING, OUT_FOR_DELIVERY, DELIVERED, CANCELLED
}

/**
 * PUBLIC_INTERFACE
 * Represents a user profile and authentication state.
 */
data class User(
    val userId: String,
    val email: String,
    val displayName: String,
    val address: String? = null
)
