package org.example.app

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.example.app.di.ServiceLocator

/**
 * PUBLIC_INTERFACE
 * AuthActivity handles basic login and signup flows.
 * In production, integrate with real backend authentication here.
 */
class AuthActivity : AppCompatActivity() {

    private var isSignup = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_GroceryApp)
        setContentView(R.layout.activity_auth)

        val toggle = findViewById<TextView>(R.id.toggleMode)
        val nameField = findViewById<EditText>(R.id.inputName)
        val emailField = findViewById<EditText>(R.id.inputEmail)
        val passwordField = findViewById<EditText>(R.id.inputPassword)
        val actionBtn = findViewById<Button>(R.id.btnAction)

        fun updateMode() {
            findViewById<TextView>(R.id.title).text = if (isSignup) getString(R.string.signup) else getString(R.string.login)
            nameField.visibility = if (isSignup) android.view.View.VISIBLE else android.view.View.GONE
            actionBtn.text = if (isSignup) getString(R.string.signup) else getString(R.string.login)
            toggle.text = if (isSignup) getString(R.string.login) else getString(R.string.signup)
        }

        updateMode()

        toggle.setOnClickListener {
            isSignup = !isSignup
            updateMode()
        }

        actionBtn.setOnClickListener {
            val email = emailField.text?.toString()?.trim().orEmpty()
            val password = passwordField.text?.toString().orEmpty()
            val name = nameField.text?.toString()?.trim().orEmpty()

            val repo = ServiceLocator.provideRepository(this)
            lifecycleScope.launch {
                val result = if (isSignup) repo.signup(name, email, password) else repo.login(email, password)
                result.fold(
                    onSuccess = {
                        startActivity(Intent(this@AuthActivity, MainActivity::class.java))
                        finish()
                    },
                    onFailure = {
                        Toast.makeText(this@AuthActivity, it.message ?: "Authentication failed", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}
