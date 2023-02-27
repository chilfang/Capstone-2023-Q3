package com.example.capstoneproject.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.FragmentMainMenuBinding
import com.example.capstoneproject.databinding.FragmentTitleScreenBinding
import com.example.capstoneproject.viewmodel.GameViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [TitleScreen.newInstance] factory method to
 * create an instance of this fragment.
 */
class TitleScreen : Fragment() {
    private var binding: FragmentTitleScreenBinding? = null
    private val sharedViewModel: GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentTitleScreenBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            titleScreenFragment = this@TitleScreen
        }

    }

    fun goToNextScreen() {
        findNavController().navigate(R.id.action_titleScreen_to_mainMenu)
    }
}