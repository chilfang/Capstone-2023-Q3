package com.example.capstoneproject.helper

import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.FragmentGameScreenBinding
import com.example.capstoneproject.fragments.GameScreen
import kotlin.math.log

class TouchPadTracker(var startEvent: MotionEvent, var fragment: GameScreen) {
    var xStart: Int = 0
    var yStart: Int = 0

    var xResult: Float = 0F
    var yResult: Float = 0F

    private var touchPadImage: ImageView? = null

    lateinit var timer: Timer

    fun openPad(runnable: Runnable) {
        xStart = startEvent.x.toInt() - 50
        yStart = startEvent.y.toInt() - 280

        touchPadImage = ImageView(fragment.binding!!.dot.context)
        touchPadImage!!.id = View.generateViewId()
        fragment.binding!!.layout.addView(touchPadImage)
        touchPadImage!!.setImageResource(R.drawable.pad)
        touchPadImage!!.alpha = 0.5f

        val constraintSet = ConstraintSet()
        constraintSet.clone(fragment.binding!!.layout)
        constraintSet.connect(touchPadImage!!.id, ConstraintSet.START, fragment.binding!!.layout.id, ConstraintSet.START, xStart - 50)
        constraintSet.connect(touchPadImage!!.id, ConstraintSet.TOP, fragment.binding!!.layout.id, ConstraintSet.TOP, yStart - 50)
        constraintSet.applyTo(fragment.binding!!.layout)


        timer = Timer(runnable)
        timer.delay = 33
        timer.start()
    }

    fun testPosition(event: MotionEvent) {
        xResult = (event.x - xStart - 50) / 100
        yResult = (event.y - yStart - 280) / 100
    }

    fun closePad() {
        timer.stop()
        fragment.binding!!.layout.removeView(touchPadImage)
        touchPadImage = null
    }
}