package com.example.redfinapp.data

import com.example.redfinapp.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface ApiService {

    @GET("resource/jjew-r69b.json")
    fun getFoodTrucks(): Single<List<FoodTruck>>

    companion object {
        private val client: OkHttpClient =
            OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build()

        private val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

        private var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()

        fun <S> createService(serviceClass: Class<S>): S {
            return retrofit.create(serviceClass)
        }

    }
}