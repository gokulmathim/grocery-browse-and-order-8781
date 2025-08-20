# CI Helpers

Use run-gradle.sh to invoke Gradle from CI:

- Example: .init/run-gradle.sh :grocery_ordering_app_frontend:app:assembleDebug

This wrapper ensures the correct root gradle shim is used, which delegates to the app's gradle-wrapper.jar.
