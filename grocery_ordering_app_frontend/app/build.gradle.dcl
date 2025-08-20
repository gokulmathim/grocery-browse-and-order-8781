androidApplication {
    namespace = "org.example.app"

    dependencies {
        // AndroidX and Material dependencies with explicit versions
        implementation("androidx.appcompat:appcompat:1.7.0")
        implementation("com.google.android.material:material:1.12.0")
        implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")
        implementation("androidx.legacy:legacy-support-v4:1.0.0")
        implementation("androidx.activity:activity:1.9.2")
        // Keep project utilities if needed by code (not strictly required now)
        implementation(project(":utilities"))
    }
}
