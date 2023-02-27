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
import com.example.capstoneproject.helper.Collision
import com.example.capstoneproject.helper.Timer
import com.example.capstoneproject.helper.TouchPadTracker
import com.example.capstoneproject.viewmodel.GameViewModel
import kotlin.math.max
import kotlin.math.min


class GameScreen : Fragment() {
    var binding: FragmentGameScreenBinding? = null
    private val sharedViewModel: GameViewModel by activityViewModels()
    lateinit var characterTracker: CharacterTracker
    private lateinit var loop: Timer

    val deadZone = 0.10
    val speedMultiplier = 15
    val maxSpeed = speedMultiplier

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentGameScreenBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        characterTracker = CharacterTracker(binding!!.dot, binding!!.layout)
        (activity as MainActivity).characterTracker = characterTracker
        (activity as MainActivity).touchPadActive = true
        (activity as MainActivity).fragment = this

        (activity as MainActivity).deadZone = deadZone
        (activity as MainActivity).speedMultiplier = speedMultiplier
        (activity as MainActivity).maxSpeed = maxSpeed

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

        loop = Timer {
            if (!isDotColliding()) { //down
                characterTracker.yMomentum += 0.1F
            } else {
                characterTracker.yMomentum = 0f
            }

            characterTracker.y += min((characterTracker.yMomentum * speedMultiplier).toInt(), maxSpeed)


            characterTracker.update()
        }

        loop.delay = 33

        loop.start()
        Log.w("GameScreen", "What")
    }
    override fun onDestroyView() {
        (activity as MainActivity).touchPadActive = false
        loop.stop()
        binding = null
        super.onDestroyView()
    }

    fun isDotColliding(): Boolean {
        return Collision.testForCollision(binding!!.dot, binding!!.startPlatform)
    }
}
