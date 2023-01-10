package com.example.cameraapplication

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.cameraapplication.databinding.ActivityPreviewBinding
import com.example.cameraapplication.retrofit.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class PreviewActivity : AppCompatActivity() {

  lateinit var binding: ActivityPreviewBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityPreviewBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

   //  val uri = intent.extras?.getString("imageUri")

    val uri = Uri.parse( intent.getStringExtra("imageUri"))
    if (uri != null){
      binding.previewUploadImg.setImageURI(uri)
    }

    binding.imageUploadBtn.setOnClickListener {
    lifecycleScope.launch(Dispatchers.IO){
      RetrofitHelper.apiClient.postData(RequestBody("", File(uri.path!!), uri.path!!))
    }
    }
  }
}