package com.example.redfinapp.data

import io.reactivex.Single

class FoodTruckRepository {

    val apiService = ApiService.createService(ApiService::class.java)

    fun getFoodTrucks(): Single<List<FoodTruck>> {
        return apiService.getFoodTrucks()
    }
}