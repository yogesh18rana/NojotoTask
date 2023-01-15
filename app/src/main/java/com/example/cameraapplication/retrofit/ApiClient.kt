package com.example.cameraapplication.retrofit

import com.example.cameraapplication.RequestBody
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiClient {
  @Multipart
  @POST("content.php?cid=7ec99b415af3e88205250e3514ce0fa7")
  suspend fun postData(@Part("media")requestBody: RequestBody, @Part image: MultipartBody.Part)
}