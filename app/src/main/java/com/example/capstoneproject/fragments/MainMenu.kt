package com.example.capstoneproject.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.FragmentMainMenuBinding
import com.example.capstoneproject.viewmodel.GameViewModel

class MainMenu : Fragment() {
    private var binding: FragmentMainMenuBinding? = null
    private val sharedViewModel: GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentMainMenuBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        // Inflate the layout for this fragment
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            mainMenuFragment = this@MainMenu
        }
    }

    fun logout() {
        findNavController().navigate(R.id.action_mainMenu_to_titleScreen)
    }

    fun playGame() {
        findNavController().navigate(R.id.action_mainMenu_to_gameScreen)
    }

    fun shop() {
        findNavController().navigate(R.id.action_mainMenu_to_shopScreen)
    }
}