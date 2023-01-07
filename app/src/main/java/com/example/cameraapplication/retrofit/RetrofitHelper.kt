package com.example.cameraapplication.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
  val baseUrl = "https://reqres.in/"

  val retrofit = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).client(
    OkHttpClient.Builder().build()
  ).build()
  val apiClient = retrofit.create(ApiClient::class.java)
}