package com.example.cameraapplication.retrofit

import com.example.cameraapplication.Constant
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private val baseUrl = Constant.BASE_URL
    private val retrofit =
        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder().build()
            ).build()
    val apiClient = retrofit.create(ApiClient::class.java)
}