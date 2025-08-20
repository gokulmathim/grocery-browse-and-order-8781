# GroceryGo - Grocery Ordering Android App

A modern, light-themed Android app (Kotlin, XML Views) for browsing groceries, managing a cart, placing orders, tracking orders, and managing profile/auth.

Features:
- User authentication (mock in-memory)
- Product browsing with search and categories (demo data)
- Add to cart and cart management
- Order placement via Checkout FAB
- Order history and status
- Profile management (update display name and address)

Tech:
- Kotlin (no Jetpack Compose; traditional Views + Fragments)
- AndroidX + Material Components
- Declarative Gradle DSL sample structure

Build:
- From repo root: ./gradlew :grocery_ordering_app_frontend:app:build
- Or from app dir: cd grocery_ordering_app_frontend && ./gradlew :app:build

Install and run on device/emulator:
- ./gradlew :grocery_ordering_app_frontend:app:installDebug
- Launch "GroceryGo"

Notes:
- Data is in-memory for demo purposes; authentication is mocked.
- Colors follow the provided palette: primary #4CAF50, secondary #F5F5F5, accent #FF9800.