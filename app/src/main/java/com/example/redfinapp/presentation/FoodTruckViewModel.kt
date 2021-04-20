package com.example.redfinapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.redfinapp.data.FoodTruck
import com.example.redfinapp.data.FoodTruckRepository
import com.example.redfinapp.util.LiveEvent
import com.example.redfinapp.util.constants.militaryHourMinFormat
import com.example.redfinapp.util.logException
import com.example.redfinapp.util.newEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import org.threeten.bp.LocalTime
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

class FoodTruckViewModel: ViewModel() {

    private val disposables = CompositeDisposable()
    private val foodTruckRepo = FoodTruckRepository()

    enum class DataState {
        LOADING,
        LOADED,
        ERROR
    }

    private val stateData = MutableLiveData<LiveEvent<DataState>>()
    fun getStateData(): LiveData<LiveEvent<DataState>> = stateData

    private val foodTruckData = MutableLiveData<List<FoodTruck>>()
    fun getFoodTruckData(): LiveData<List<FoodTruck>> = foodTruckData

    init {
        stateData.newEvent(DataState.LOADING)
        loadData()
    }

    private fun loadData() {
        val date = ZonedDateTime.now()

        foodTruckRepo.getFoodTrucks()
            .map {
                val dayOfWeek = date.dayOfWeek.name

                it.filter { foodTruck ->
                    val startTime = LocalTime.parse(foodTruck.start24, DateTimeFormatter.ofPattern(militaryHourMinFormat))
                    val endTime = LocalTime.parse(foodTruck.end24, DateTimeFormatter.ofPattern(militaryHourMinFormat))
                    foodTruck.dayOfWeekStr.equals(dayOfWeek, true) && (date.toLocalTime().isAfter(startTime) && date.toLocalTime().isBefore(endTime))
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    { foodTrucks ->
                        stateData.newEvent(DataState.LOADED)
                        foodTruckData.value = foodTrucks.sortedBy { it.name }
                    },
                    {
                        logException(it)
                        stateData.newEvent(DataState.ERROR)
                    }
            )
            .addTo(disposables)
    }
}