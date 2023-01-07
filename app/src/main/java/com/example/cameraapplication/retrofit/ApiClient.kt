package com.example.cameraapplication.retrofit

import retrofit2.http.GET

interface ApiClient {
  @GET("api/users")
  suspend fun call()
}