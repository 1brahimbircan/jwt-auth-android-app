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
import com.example.jwtauth.MainActivity
import com.example.jwtauth.R
import com.example.jwtauth.databinding.FragmentLoginBinding
import com.example.jwtauth.models.UserRequest
import com.example.jwtauth.ui.viewmodels.AuthViewModel
import com.example.jwtauth.utils.Helper
import com.example.jwtauth.utils.LoadingDialog
import com.example.jwtauth.utils.NetworkResult
import com.example.jwtauth.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val authViewModel:AuthViewModel by viewModels()

    private lateinit var loadingDialog: LoadingDialog

    @Inject
    lateinit var tokenManager: TokenManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        // Initialize the LoadingDialog
        loadingDialog = LoadingDialog(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvHaventAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.btnLogin.setOnClickListener {
            //Helper.hideKeyboard(it)
            val validateResult = validateUserInput()
            if (validateResult.first){
                val userRequest = getUserRequest()
                authViewModel.loginUser(userRequest)
            }else{
                Toast.makeText(context, validateResult.second ,Toast.LENGTH_SHORT).show()
            }
        }
        bindObservers()
    }

    private fun getUserRequest(): UserRequest {
        return binding.run {
            UserRequest(
                "",
                etEmail.text.toString(),
                etPassword.text.toString(),
            )
        }
    }
    private fun validateUserInput(): Pair<Boolean, String> {
        val emailAddress = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        return authViewModel.validateCredentials(emailAddress, "" , password, password, true)
    }

    private fun bindObservers() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Success -> {
                    loadingDialog.dismissDialog()
                    tokenManager.saveToken(it.data!!.token)
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }
                is NetworkResult.Error -> {
                    loadingDialog.dismissDialog()
                    Toast.makeText(context, it.message.toString() ,Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading ->{
                    loadingDialog.startLoadingDialog()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}