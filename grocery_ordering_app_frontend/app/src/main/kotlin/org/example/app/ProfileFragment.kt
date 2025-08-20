package org.example.app

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.example.app.di.ServiceLocator

/**
 * PUBLIC_INTERFACE
 * ProfileFragment displays basic profile information with edit and logout.
 */
class ProfileFragment : Fragment() {

    private lateinit var nameView: TextView
    private lateinit var emailView: TextView
    private lateinit var editName: EditText
    private lateinit var saveBtn: Button
    private lateinit var logoutBtn: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val v = inflater.inflate(R.layout.fragment_profile, container, false)
        nameView = v.findViewById(R.id.profileName)
        emailView = v.findViewById(R.id.profileEmail)
        editName = v.findViewById(R.id.editName)
        saveBtn = v.findViewById(R.id.btnSave)
        logoutBtn = v.findViewById(R.id.btnLogout)

        viewLifecycleOwner.lifecycleScope.launch {
            val user = ServiceLocator.provideRepository(requireContext()).getCurrentUser()
            nameView.text = user?.name ?: "-"
            emailView.text = user?.email ?: "-"
            editName.setText(user?.name ?: "")
        }

        saveBtn.setOnClickListener {
            // No real backend - show a toast to simulate
            val newName = editName.text?.toString()?.trim().orEmpty()
            if (newName.isNotBlank()) {
                nameView.text = newName
                Toast.makeText(requireContext(), getString(R.string.save), Toast.LENGTH_SHORT).show()
            }
        }

        logoutBtn.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                ServiceLocator.provideRepository(requireContext()).logout()
                startActivity(Intent(requireContext(), AuthActivity::class.java))
                requireActivity().finish()
            }
        }

        return v
    }

    companion object { fun newInstance() = ProfileFragment() }
}
