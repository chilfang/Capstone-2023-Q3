package com.example.capstoneproject.helper

import android.os.Handler
import android.os.Looper




class Timer(var runnable: Runnable) {
    private val handler: Handler = Handler(Looper.getMainLooper())

    private val statusChecker: Runnable = object : Runnable {
        override fun run() {
            try {
                runnable.run() //this function can change value of mInterval.
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                handler.postDelayed(this, delay)
            }
        }
    }

    var delay: Long = 2000

    fun start() {
        statusChecker.run()
    }

    fun stop() {
        handler.removeCallbacks(statusChecker)
    }


}