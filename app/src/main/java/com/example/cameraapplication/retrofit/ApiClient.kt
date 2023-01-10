package com.example.cameraapplication.retrofit

import com.example.cameraapplication.RequestBody
import retrofit2.http.Field
import retrofit2.http.Multipart
import retrofit2.http.POST

interface ApiClient {
  @Multipart
  @POST("acontent.php?cid=7ec99b415af3e88205250e3514ce0fa7")
  suspend fun postData( requestBody: RequestBody)
}