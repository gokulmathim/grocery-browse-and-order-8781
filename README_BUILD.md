To build the Android app from this repository root:

1) Ensure the Gradle wrapper is executable:
   bash setup-gradle-wrapper.sh

2) Build using the delegator wrapper:
   ./gradlew :app:assembleDebug

This delegator routes to grocery_ordering_app_frontend/gradlew.
