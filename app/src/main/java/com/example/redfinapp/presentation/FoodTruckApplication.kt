package com.example.redfinapp.presentation

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class FoodTruckApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}