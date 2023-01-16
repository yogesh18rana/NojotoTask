package com.example.cameraapplication.retrofit

data class Data(
    val cid: String,
    val description: String,
    val error: Boolean,
    val message: String,
    val result: List<Any>,
    val statusCode: Int,
    val success: Boolean
)