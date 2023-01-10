package com.example.cameraapplication.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
  private val baseUrl = "https://sapna.dev.nojoto.com/api/beta/"

  private val retrofit = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).client(
    OkHttpClient.Builder().build()
  ).build()
  val apiClient = retrofit.create(ApiClient::class.java)
}