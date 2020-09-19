package com.gao.sunnyweather.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object SunnyWeatherNetwork {
    private val placeService = ServiceCreator.create(SunnyWeatherService::class.java)

    suspend fun searchPlace(query: String) = placeService.searchPlace(query).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine {
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, throwable: Throwable) {
                    it.resumeWithException(throwable)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) {
                        it.resume(body)
                    } else {
                        it.resumeWithException(RuntimeException("response body is null"))
                    }
                }
            })
        }
    }
}