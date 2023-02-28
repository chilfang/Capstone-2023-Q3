//Ian Marler
//2/22/2023

package com.example.capstoneproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MotionEvent
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.capstoneproject.fragments.GameScreen
import com.example.capstoneproject.helper.CharacterTracker
import com.example.capstoneproject.helper.TouchPadTracker
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    var touchPadActive = false
    lateinit var characterTracker: CharacterTracker
    lateinit var touchPadTracker: TouchPadTracker
    var fragment: Fragment? = null

    var deadZone = 0.0 //defined in GameScreen.kt
    var speedMultiplier = 0 //defined in GameScreen.kt
    var maxSpeed = 0 //defined in GameScreen.kt

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        setupActionBarWithNavController(navController)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!touchPadActive) {return false}

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                touchPadTracker = TouchPadTracker(event, (fragment as GameScreen))
                touchPadTracker.openPad {
                    if (touchPadTracker.xResult > deadZone) { //left
                        characterTracker.x += min((touchPadTracker.xResult * speedMultiplier).toInt(), maxSpeed)
                    } else if (touchPadTracker.xResult < -deadZone) { //right
                        characterTracker.x -= min((abs(touchPadTracker.xResult) * speedMultiplier).toInt(), maxSpeed)
                    }

                    /*// NOT NEEDED
                    if (!(fragment as GameScreen).isDotColliding() && touchPadTracker.yResult > deadZone) { //down
                        characterTracker.y += min((touchPadTracker.yResult * speedMultiplier).toInt(), maxSpeed)
                    } else if (touchPadTracker.yResult < -deadZone) { //up
                        characterTracker.y -= min((abs(touchPadTracker.yResult) * speedMultiplier).toInt(), maxSpeed)
                    }
                    */

                    if(touchPadTracker.yResult < -deadZone * 10 && (fragment as GameScreen).jumpAvailable) {
                        Log.w("Jump", (fragment as GameScreen).jumpAvailable.toString())
                        characterTracker.yMomentum = -2f
                        (fragment as GameScreen).jumpAvailable = false
                    }
                }

            }

            MotionEvent.ACTION_MOVE -> {
                touchPadTracker.testPosition(event)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                touchPadTracker.closePad()
            }
        }
        return true
    }
}