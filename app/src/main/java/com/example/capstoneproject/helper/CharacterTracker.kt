package com.example.capstoneproject.helper

import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.marginLeft
import androidx.core.view.marginTop

class CharacterTracker(var movementTarget: ImageView, var layout: ConstraintLayout) {
    var x = movementTarget.marginLeft
    var y = movementTarget.marginTop

    var yMomentum = 0.0F

    fun update() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(layout)
        constraintSet.connect(movementTarget.id, ConstraintSet.START,layout.id, ConstraintSet.START, x)
        constraintSet.connect(movementTarget.id, ConstraintSet.TOP, layout.id, ConstraintSet.TOP, y)
        constraintSet.applyTo(layout)


        //Log.w("dot", movementTarget.marginLeft.toString())
        //Log.w("dot", movementTarget.marginTop.toString())
    }
}