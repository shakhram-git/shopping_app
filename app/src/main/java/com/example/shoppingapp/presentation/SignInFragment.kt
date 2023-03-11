package com.example.shoppingapp.presentation

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.domain.shoppingapp.model.NewUser
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private val viewModel: SignInViewModel by viewModels()
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private var isPasswordVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.inputPassword.addTextChangedListener {
            binding.passwordEyeBtn.isVisible = true
        }
        binding.signInBtn.setOnClickListener {
            if (checkIsFieldsFilledCorrectly()) {
                val newUser = NewUser(
                    binding.inputName.text.toString(),
                    binding.inputSurname.text.toString(),
                    binding.inputEmail.text.toString(),
                    binding.inputPassword.text.toString()
                )
                viewModel.createNewUser(newUser)
            }
        }
        binding.passwordEyeBtn.setOnClickListener {
            if (isPasswordVisible) {
                binding.inputPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                binding.inputPassword.setSelection(binding.inputPassword.length())
                binding.passwordEyeBtn.alpha = 1f
            } else {
                binding.inputPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.inputPassword.setSelection(binding.inputPassword.length())
                binding.passwordEyeBtn.alpha = 0.5f
            }
            isPasswordVisible = !isPasswordVisible
        }
        binding.logInBtn.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_loginFragment)
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isSignInSuccessful.collect { isSuccessful ->
                if (!isSuccessful)
                    Toast.makeText(
                        requireContext(),
                        "User with the email already exist",
                        Toast.LENGTH_SHORT
                    ).show()
                binding.inputEmail.error = "Use another email"
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun checkIsFieldsFilledCorrectly(): Boolean {
        var isCorrectly = true
        if (binding.inputName.text.isNullOrEmpty()) {
            binding.inputName.error = "Name is required"
            isCorrectly = false
        }
        if (binding.inputSurname.text.isNullOrEmpty()) {
            binding.inputSurname.error = "Surname is required"
            isCorrectly = false
        }

        if (binding.inputEmail.text.isNullOrEmpty()) {
            binding.inputEmail.error = "Email is required"
            isCorrectly = false
        }
        else if (!isEmailValid(binding.inputEmail.text)) {
            binding.inputEmail.error = "Email is not valid"
            isCorrectly = false
        }
        if (binding.inputPassword.text.isNullOrEmpty()) {
            binding.inputPassword.error = "Passsword is required"
            binding.passwordEyeBtn.isVisible = false
            isCorrectly = false
        }
        return isCorrectly
    }

    private fun isEmailValid(email: CharSequence?): Boolean{
        return  !email.isNullOrBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}