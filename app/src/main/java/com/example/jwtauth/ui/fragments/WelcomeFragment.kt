package com.example.jwtauth.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.jwtauth.R
import com.example.jwtauth.databinding.FragmentWelcomeBinding
import com.example.jwtauth.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeFragment : Fragment() {
    private lateinit var binding: FragmentWelcomeBinding

    @Inject
    lateinit var tokenManager:TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeBinding.inflate(inflater,container,false)
        if (tokenManager.getToken() != null){
            findNavController().navigate(R.id.action_welcomeFragment_to_homeFragment)
        }
        binding.btnWelcomeLogin.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment)
        }
        binding.btnWelcomeRegister.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_registerFragment)
        }
        return binding.root
    }

}