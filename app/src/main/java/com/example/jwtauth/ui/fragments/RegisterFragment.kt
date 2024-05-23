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
import com.example.jwtauth.databinding.FragmentRegisterBinding
import com.example.jwtauth.ui.viewmodels.AuthViewModel
import com.example.jwtauth.data.entities.NetworkResult
import com.example.jwtauth.data.entities.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val authViewModel:AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            val validateResult = validateUserInput()
            if (validateResult.first){
                authViewModel.register(getUserRequest())
            }else{
                Toast.makeText(context, validateResult.second ,Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        bindObserves()

    }
    private fun getUserRequest():User{
        val emailAddress = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val fullName = binding.etFullname.text.toString()
        return User(fullName,emailAddress,password)
    }

    private fun validateUserInput():Pair<Boolean,String>{
        val userRequest = getUserRequest()
        val confirmPassword = binding.etConfirmPassword.text.toString()
        return authViewModel.validateCredentials(
            fullName = userRequest.name,
            emailAddress = userRequest.email,
            password = userRequest.password,
            confirmPassword = confirmPassword,
            isLogin = false
        )
    }

    private fun bindObserves() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer{
            when(it){
                is NetworkResult.Success -> {
                    Toast.makeText(context, it.data?.message,Toast.LENGTH_SHORT).show()
                    val user = getUserRequest()
                    val authRequest = AuthRequest(user.email, user.password)
                    authViewModel.login(authRequest)
                }
                is NetworkResult.Error -> {
                    Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    Toast.makeText(context,"Loading...",Toast.LENGTH_SHORT).show()
                }
            }
        })
        authViewModel.tokenResponseLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is AuthResult.Authorized -> {
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                    val user = getUserRequest()
                    val bundle = Bundle().apply {
                        putString("email", user.email)
                        putString("token", it.data!!.token)
                    }
                    findNavController().navigate(R.id.action_registerFragment_to_homeFragment, bundle)
                }
                is AuthResult.Unauthorized -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is AuthResult.Loading -> {
                    Toast.makeText(context,"Loading...",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

}