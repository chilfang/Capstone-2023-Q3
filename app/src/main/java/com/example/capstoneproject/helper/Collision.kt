package com.example.capstoneproject.helper

import android.util.Log
import android.widget.ImageView
import kotlin.math.max as Max
import kotlin.math.min as Min

class Collision {
    companion object {
        fun testForCollision(object1: ImageView, object2: ImageView): Boolean {
            var location1 = IntArray(2)
            var location2 = IntArray(2)
            
            object1.getLocationInWindow(location1)
            object2.getLocationInWindow(location2)

            val left = Max(location1[0], location2[0])
            val right = Min(location1[0] + scaledWidth(object1), location2[0] + scaledWidth(object2))
            val top = Max(location1[1], location2[1])
            val bottom = Min(location1[1] + scaledHeight(object1), location2[1] + scaledHeight(object2))

            if (left < right && top < bottom) {
                return true
            }

            return false
        }

        private fun scaledWidth(input: ImageView): Float {
            return input.width * input.scaleX
        }

        private fun scaledHeight(input: ImageView): Float {
            return input.height * input.scaleY
        }
    }


}