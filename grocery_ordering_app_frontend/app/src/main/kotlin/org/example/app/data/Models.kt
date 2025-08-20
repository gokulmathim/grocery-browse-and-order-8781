package org.example.app.data

import java.util.Date
import java.util.UUID

/**
 * PUBLIC_INTERFACE
 * Basic data models used across the app.
 */
data class Product(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String? = null,
    val category: String = "All",
    val inStock: Boolean = true
)

data class CartItem(
    val product: Product,
    val quantity: Int
) {
    val subtotal: Double get() = quantity * product.price
}

enum class OrderStatus { PLACED, CONFIRMED, OUT_FOR_DELIVERY, DELIVERED, CANCELLED }

data class Order(
    val id: String = UUID.randomUUID().toString(),
    val items: List<CartItem>,
    val total: Double,
    val placedAt: Date = Date(),
    val status: OrderStatus = OrderStatus.PLACED
)

data class UserProfile(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val email: String
)
