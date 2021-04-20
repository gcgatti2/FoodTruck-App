package com.example.redfinapp.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FoodTruck (
    @Json(name = "applicant")
    val name: String,
    val location: String,
    @Json(name = "optionaltext")
    val description: String?,
    @Json(name = "starttime")
    val startTime: String,
    @Json(name = "endtime")
    val endTime: String,
    @Json(name = "dayofweekstr")
    val dayOfWeekStr: String,
    val start24: String,
    val end24: String,
    val latitude: String,
    val longitude: String
): Parcelable