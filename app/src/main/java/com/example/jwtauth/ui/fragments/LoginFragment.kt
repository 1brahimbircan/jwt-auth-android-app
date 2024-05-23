package com.example.jwtauth.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.jwtauth.R
import com.example.jwtauth.data.entities.AuthRequest
import com.example.jwtauth.data.entities.AuthResult
import com.example.jwtauth.data.entities.NetworkResult
import com.example.jwtauth.data.entities.User
import com.example.jwtauth.databinding.FragmentLoginBinding
import com.example.jwtauth.ui.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val validateResult = validateUserInput()
            if (validateResult.first){
                authViewModel.login(getUserRequest())
            }else{
                Toast.makeText(context, validateResult.second ,Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvHaventAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        bindObserves()
    }

    private fun getUserRequest(): AuthRequest {
        val emailAddress = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        return AuthRequest(emailAddress,password)
    }

    private fun validateUserInput():Pair<Boolean,String>{
        val userRequest = getUserRequest()
        return authViewModel.validateCredentials(
            emailAddress = userRequest.email,
            password = userRequest.password,
            isLogin = true
        )
    }

    private fun bindObserves() {
        authViewModel.tokenResponseLiveData.observe(viewLifecycleOwner, Observer {
            when(it){
                is AuthResult.Authorized -> {
                    Toast.makeText(context, it.data?.message,Toast.LENGTH_SHORT).show()
                    val user = getUserRequest()
                    val bundle = Bundle().apply {
                        putString("email", user.email)
                        putString("token", it.data!!.token)
                    }
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment, bundle)
                }
                is AuthResult.Unauthorized -> {
                    Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                }
                is AuthResult.Loading -> {
                    Toast.makeText(context,"Loading...",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

}