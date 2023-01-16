package com.example.cameraapplication.retrofit

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiClient {
    @FormUrlEncoded
    @POST("/api/beta/content.php")
    fun postData(
        @Field("cid") str: String,
        @Field("type") type: String,
        @Field("image") src: String
    ): Call<Data>
}