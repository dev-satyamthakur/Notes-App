package com.learningandroid.notesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.learningandroid.notesapp.databinding.FragmentLoginBinding
import com.learningandroid.notesapp.models.UserRequest
import com.learningandroid.notesapp.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val validationResult = validateUserInput()
            if (validationResult.first) {
                val user = getUserRequest()
                authViewModel.loginUser(user)
            }
            else {
                binding.txtError.text = validationResult.second
            }
        }

        binding.btnSignUp.setOnClickListener {
            findNavController().popBackStack()
        }

        bindObservers()
    }

    private fun getUserRequest(): UserRequest {
        val emailAddress = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()
        return UserRequest(emailAddress, password, "")
    }

    private fun validateUserInput(): Pair<Boolean, String> {
        val user = getUserRequest()
        return authViewModel.validateCredentials(user.username, user.email, user.password, true)
    }

    private fun bindObservers() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when(it) {
                is NetworkResult.Success -> {
                    // token implementation
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                }
                is NetworkResult.Error -> {
                    binding.txtError.text = it.message
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
                else -> {}
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}