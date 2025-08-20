androidApplication {
    namespace = "org.example.app"

    testing {
        dependencies {
            implementation("junit:junit:4.13.2")
        }
    }

    dependencies {
        // UI and AndroidX
        implementation("androidx.appcompat:appcompat:1.7.0")
        implementation("com.google.android.material:material:1.12.0")
        implementation("androidx.recyclerview:recyclerview:1.3.2")
        implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")
        implementation("androidx.fragment:fragment-ktx:1.8.2")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")

        // Keep existing (not used now but harmless)
        implementation("org.apache.commons:commons-text:1.11.0")
        implementation(project(":utilities"))
    }
}
