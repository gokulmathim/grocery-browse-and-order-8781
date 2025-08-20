# Grocery Ordering Android App (Frontend)

This module contains a Kotlin Android application implementing:
- User authentication (login/signup placeholders)
- Product browsing and search (grid)
- Cart management
- Order placement and tracking
- Profile management

Architecture:
- Single-Activity (MainActivity) with BottomNavigation (Home, Cart, Orders, Profile)
- Fragments per tab, Activities for Auth, Product Details, and Checkout
- Repository pattern (InMemoryGroceryRepository) ready to be swapped with real backend implementation
- Light, modern Material design theme with primary color #4CAF50 and accent #FF9800

Building:
- ./gradlew :app:assembleDebug

Notes:
- No secrets are hardcoded. When integrating backend, read configuration from BuildConfig or environment mapping provided by the orchestrator.
