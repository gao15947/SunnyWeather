package com.gao.sunnyweather.logic

import androidx.lifecycle.liveData
import com.gao.sunnyweather.logic.model.Place
import com.gao.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException

object Repository {
    fun searchPlace(cityName: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunnyWeatherNetwork.searchPlace(cityName)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure<List<Place>>(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (exception: Exception) {
            Result.failure<List<Place>>(exception)
        }
        emit(result)
    }
}