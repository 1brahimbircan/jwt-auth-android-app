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
import com.example.jwtauth.databinding.FragmentHomeBinding
import com.example.jwtauth.databinding.FragmentLoginBinding
import com.example.jwtauth.ui.viewmodels.AuthViewModel
import com.example.jwtauth.utils.LoadingDialog
import com.example.jwtauth.utils.NetworkResult
import com.example.jwtauth.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val authViewModel:AuthViewModel by viewModels()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModel.getUser()
        bindObservers()

    }
    private fun bindObservers() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvName.text = it.data!!.user.name
                    binding.tvEmail.text = it.data.user.email
                    binding.tvToken.text = it.data.token
                }
                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    tokenManager.clearToken()
                    Toast.makeText(context, it.message.toString() ,Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
                }
                is NetworkResult.Loading ->{
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
