package com.gao.sunnyweather.logic.network

import com.gao.sunnyweather.SunnyWeatherApplication
import com.gao.sunnyweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SunnyWeatherService {
    @GET("/v2/place?token=${SunnyWeatherApplication.TOKEN}")
    fun searchPlace(@Query("query") query: String, @Query("lang") lang: String = "zh_CN"): Call<PlaceResponse>
}