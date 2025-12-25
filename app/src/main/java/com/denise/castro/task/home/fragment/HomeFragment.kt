package com.denise.castro.task.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.denise.castro.task.R
import com.denise.castro.task.databinding.FragmentHomeBinding
import com.denise.castro.task.ui.adapter.ViewPagerAdapter
import com.denise.castro.task.ui.fragment.DoingFragment
import com.denise.castro.task.ui.fragment.DoneFragment
import com.denise.castro.task.ui.fragment.TodoFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        configTabLayout()
        initClicks()
    }

    private fun initClicks() {
        binding.imageButtonIcLogout.setOnClickListener {
            logoutApp()
        }
    }

    private fun logoutApp() {
        auth.signOut()
        findNavController().navigate(R.id.action_homeFragment_to_authentication)
    }

    private fun configTabLayout() {
        val adapter = ViewPagerAdapter(requireActivity())
        binding.viewPagerHome.adapter = adapter
        adapter.addFragment(TodoFragment(), "A Fazer")
        adapter.addFragment(DoingFragment(), "Fazendo")
        adapter.addFragment(DoneFragment(), "Feitas")

        binding.viewPagerHome.offscreenPageLimit = adapter.itemCount

        TabLayoutMediator(
            binding.tabs, binding.viewPagerHome
        ) { tab, position ->
            tab.text = adapter.getTitle(position)
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}