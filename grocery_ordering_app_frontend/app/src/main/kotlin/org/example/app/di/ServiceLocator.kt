package org.example.app.di

import android.content.Context
import org.example.app.data.GroceryRepository
import org.example.app.data.InMemoryGroceryRepository

/**
 * PUBLIC_INTERFACE
 * ServiceLocator provides app-wide dependencies like repositories.
 * Replace InMemoryGroceryRepository with a real implementation when backend is available.
 */
object ServiceLocator {
    @Volatile
    private var repository: GroceryRepository? = null

    fun provideRepository(context: Context): GroceryRepository {
        return repository ?: synchronized(this) {
            repository ?: InMemoryGroceryRepository(
                baseUrl = null // future: read from BuildConfig or environment mapping
            ).also { repository = it }
        }
    }
}
