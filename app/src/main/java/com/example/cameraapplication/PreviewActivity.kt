package com.example.cameraapplication

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.cameraapplication.databinding.ActivityPreviewBinding
import com.example.cameraapplication.retrofit.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import java.io.File

class PreviewActivity : AppCompatActivity() {

    lateinit var binding: ActivityPreviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //  val uri = intent.extras?.getString("imageUri")

        val uri = Uri.parse(intent.getStringExtra("imageUri"))
        if (uri != null) {
            binding.previewUploadImg.setImageURI(uri)
        }

        binding.imageUploadBtn.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val body = RequestBody("", File(uri.path!!), uri.path!!)
                val a = async {
                    RetrofitHelper.apiClient.postData(
                        RequestBody("", File(uri.path!!), uri.path!!),
                        MultipartBody.Part.createFormData("image", File(uri.path!!).name)
                    )
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@PreviewActivity, "Uploading", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                a.await()
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@PreviewActivity,
                        getString(R.string.image_upload_success),
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }
        }
    }
}