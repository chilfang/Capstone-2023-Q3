package com.example.capstoneproject.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.capstoneproject.MainActivity
import com.example.capstoneproject.databinding.FragmentGameScreenBinding
import com.example.capstoneproject.helper.CharacterTracker
import com.example.capstoneproject.viewmodel.GameViewModel


class GameScreen : Fragment() {
    var binding: FragmentGameScreenBinding? = null
    private val sharedViewModel: GameViewModel by activityViewModels()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentGameScreenBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        (activity as MainActivity).characterTracker = CharacterTracker(binding!!.dot, binding!!.layout)
        (activity as MainActivity).touchPadActive = true
        (activity as MainActivity).fragment = this

        // Inflate the layout for this fragment
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            gameScreenFragment = this@GameScreen
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        (activity as MainActivity).touchPadActive = false
    }
}
