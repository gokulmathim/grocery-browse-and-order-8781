package org.example.app.ui

import android.app.Fragment
import android.os.Bundle
import android.view.*
import android.widget.*
import org.example.app.R
import org.example.app.data.AppContainer

/**
 * PUBLIC_INTERFACE
 * ProfileFragment allows user authentication (sign in/up), shows current user info,
 * and allows updating display name and address or signing out.
 */
class ProfileFragment : Fragment() {

    private lateinit var authContainer: View
    private lateinit var profileContainer: View

    // Auth views
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var nameInput: EditText
    private lateinit var signInBtn: Button
    private lateinit var signUpBtn: Button

    // Profile views
    private lateinit var userName: TextView
    private lateinit var userEmail: TextView
    private lateinit var addressInput: EditText
    private lateinit var saveBtn: Button
    private lateinit var signOutBtn: Button

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater!!.inflate(R.layout.fragment_profile, container, false)
        authContainer = root.findViewById(R.id.auth_container)
        profileContainer = root.findViewById(R.id.profile_container)

        emailInput = root.findViewById(R.id.input_email)
        passwordInput = root.findViewById(R.id.input_password)
        nameInput = root.findViewById(R.id.input_name)
        signInBtn = root.findViewById(R.id.btn_sign_in)
        signUpBtn = root.findViewById(R.id.btn_sign_up)

        userName = root.findViewById(R.id.profile_name)
        userEmail = root.findViewById(R.id.profile_email)
        addressInput = root.findViewById(R.id.input_address)
        saveBtn = root.findViewById(R.id.btn_save_profile)
        signOutBtn = root.findViewById(R.id.btn_sign_out)

        signInBtn.setOnClickListener {
            val ok = AppContainer.repository.signIn(emailInput.text.toString(), passwordInput.text.toString())
            if (!ok) Toast.makeText(activity, "Sign in failed", Toast.LENGTH_SHORT).show()
            updateUi()
        }

        signUpBtn.setOnClickListener {
            val ok = AppContainer.repository.signUp(
                emailInput.text.toString(),
                passwordInput.text.toString(),
                nameInput.text.toString()
            )
            if (!ok) Toast.makeText(activity, "Sign up failed", Toast.LENGTH_SHORT).show()
            updateUi()
        }

        saveBtn.setOnClickListener {
            val updated = AppContainer.repository.updateProfile(userName.text.toString(), addressInput.text.toString())
            if (updated) Toast.makeText(activity, "Profile updated", Toast.LENGTH_SHORT).show()
            updateUi()
        }

        signOutBtn.setOnClickListener {
            AppContainer.repository.signOut()
            updateUi()
        }

        updateUi()
        return root
    }

    private fun updateUi() {
        val user = AppContainer.repository.currentUser()
        if (user == null) {
            authContainer.visibility = View.VISIBLE
            profileContainer.visibility = View.GONE
        } else {
            authContainer.visibility = View.GONE
            profileContainer.visibility = View.VISIBLE
            userName.text = user.displayName
            userEmail.text = user.email
            addressInput.setText(user.address ?: "")
        }
    }
}
