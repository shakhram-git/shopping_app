package com.example.shoppingapp.presentation

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.domain.shoppingapp.model.LoginError
import com.example.domain.shoppingapp.model.UserLoginData
import com.example.shoppingapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private var isPasswordVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.inputPassword.addTextChangedListener {
            binding.passwordEyeBtn.isVisible = true
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
        binding.loginBtn.setOnClickListener {
            if (checkIsFieldsFilledCorrectly()) {
                val userLoginData = UserLoginData(
                    binding.inputEmail.text.toString(),
                    binding.inputPassword.text.toString()
                )
                viewModel.login(userLoginData)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.loginState.collect { loginState ->
                when (loginState) {
                    LoginError.USER_NOT_FOUND -> binding.inputEmail.error = "User not found"
                    LoginError.WRONG_PASSWORD -> {
                        binding.inputPassword.error = "Wrong password"
                        binding.passwordEyeBtn.isVisible = false
                    }
                    null -> { }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkIsFieldsFilledCorrectly(): Boolean {
        var isCorrectly = true

        if (binding.inputEmail.text.isNullOrEmpty()) {
            binding.inputEmail.error = "Email is required"
            isCorrectly = false
        } else if (!isEmailValid(binding.inputEmail.text)) {
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

    private fun isEmailValid(email: CharSequence?): Boolean {
        return !email.isNullOrBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}