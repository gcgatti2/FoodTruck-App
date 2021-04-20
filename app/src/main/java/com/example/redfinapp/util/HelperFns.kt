package com.example.redfinapp.util

import android.util.Log

fun logException(e: Throwable) {
    Log.e("", e.message, e)
    e.printStackTrace()
}