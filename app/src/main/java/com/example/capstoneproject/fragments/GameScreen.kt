package com.example.capstoneproject.fragments

import android.annotation.SuppressLint
import android.app.ActionBar.LayoutParams
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.capstoneproject.MainActivity
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.FragmentGameScreenBinding
import com.example.capstoneproject.helper.CharacterTracker
import com.example.capstoneproject.helper.Collision
import com.example.capstoneproject.helper.Timer
import com.example.capstoneproject.viewmodel.GameViewModel
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.min


class GameScreen : Fragment() {
    var binding: FragmentGameScreenBinding? = null
    private val sharedViewModel: GameViewModel by activityViewModels()
    lateinit var characterTracker: CharacterTracker
    private lateinit var loop: Timer

    val deadZone = 0.10
    val speedMultiplier = 15
    val maxSpeed = speedMultiplier

    var jumpAvailable = true

    var pieceLayouts = ArrayList<Int>()
    var colliders = ArrayList<ImageView>()
    var activePieces = ArrayList<View>()

    var stageLowerCooldown = true
    var lifetimeCounter = 0

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

        pieceLayouts.add(R.layout.piece_1)




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

        addPiece(R.layout.piece_start).visibility = View.VISIBLE

        for (i in 1..3) {
            addPiece(pieceLayouts[Random().nextInt(pieceLayouts.size)])
        }

        loop = Timer {
            lifetimeCounter++

            if (!isDotColliding()) { //in air
                characterTracker.yMomentum += 0.1F
            } else { // on a platform
                characterTracker.yMomentum = min(0f, characterTracker.yMomentum)
                jumpAvailable = true
            }

            characterTracker.y += min((characterTracker.yMomentum * speedMultiplier).toInt(), maxSpeed)

            if (stageLowerCooldown) {
                val constraintSet = ConstraintSet()
                constraintSet.clone(binding!!.pieces)
                constraintSet.connect(activePieces[0].id, ConstraintSet.BOTTOM, binding!!.pieces.id, ConstraintSet.BOTTOM, activePieces[0].marginBottom - 1)
                constraintSet.applyTo(binding!!.pieces)

                stageLowerCooldown = false
            } else {
                stageLowerCooldown = true
            }

            characterTracker.update()

            if (characterTracker.y > 1900) {
                sharedViewModel.addPoints(lifetimeCounter)
                findNavController().navigate(R.id.action_gameScreen_to_mainMenu)
            }
        }

        loop.delay = 33

        loop.start()
    }
    override fun onDestroyView() {
        (activity as MainActivity).touchPadActive = false
        loop.stop()
        binding = null
        super.onDestroyView()
    }

    fun isDotColliding(): Boolean {
        for (collider in colliders) {

            var array = IntArray(2)
            collider.getLocationInWindow(array)
            //Log.w("searcher", collider.toString() + " " + array[1].toString())

            if (Collision.testForCollision(binding!!.dot, collider)) {
                return true
            }
        }

        //Log.w("wack", "woah")

        return false
    }

    fun addPiece(layoutID: Int): View {
        var piece = ViewStub(binding!!.pieces.context)
        piece.layoutResource = layoutID
        piece.id = View.generateViewId()
        piece.inflatedId = View.generateViewId()
        binding!!.pieces.addView(piece, 0)

        var inflatedPiece = piece.inflate()
        piece.visibility = View.VISIBLE

        var foundViews = ArrayList<View>()
        inflatedPiece.findViewsWithText(foundViews, "platform", View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION)


        for (view in foundViews) {
            colliders.add(view as ImageView)
        }


        if (activePieces.size > 0) {
            val constraintSet = ConstraintSet()
            constraintSet.clone(binding!!.pieces)
            constraintSet.connect(inflatedPiece.id, ConstraintSet.START, activePieces.get(activePieces.size - 1).id, ConstraintSet.START, 0)
            constraintSet.connect(inflatedPiece.id, ConstraintSet.BOTTOM, activePieces.get(activePieces.size - 1).id, ConstraintSet.TOP, 0)
            constraintSet.applyTo(binding!!.pieces)
        } else {
            val constraintSet = ConstraintSet()
            constraintSet.clone(binding!!.pieces)
            constraintSet.connect(inflatedPiece.id, ConstraintSet.START, binding!!.pieces.id, ConstraintSet.START, 0)
            constraintSet.connect(inflatedPiece.id, ConstraintSet.BOTTOM, binding!!.pieces.id, ConstraintSet.BOTTOM, 0)
            constraintSet.applyTo(binding!!.pieces)
        }

        activePieces.add(inflatedPiece)

        return inflatedPiece

    }
}
