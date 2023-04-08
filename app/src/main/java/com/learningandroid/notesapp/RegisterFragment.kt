package com.learningandroid.notesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.learningandroid.notesapp.databinding.FragmentRegisterBinding
import com.learningandroid.notesapp.models.UserRequest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.btnSignUp.setOnClickListener {
            authViewModel.registerUser(UserRequest("test001@gmail.com", "123456", "test001"))
//            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
        }

        binding.btnLogin.setOnClickListener {
            authViewModel.loginUser(UserRequest("test001@gmail.com", "123456", "test001"))
//            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}