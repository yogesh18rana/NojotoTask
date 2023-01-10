package com.example.cameraapplication

import java.io.File

data class RequestBody(val type : String, val file: File, val src : String) {

}