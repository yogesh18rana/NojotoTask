package com.example.cameraapplication

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.cameraapplication.databinding.ActivityPreviewBinding
import com.example.cameraapplication.retrofit.Data
import com.example.cameraapplication.retrofit.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
                   val v= RetrofitHelper.apiClient.postData(
                        "7ec99b415af3e88205250e3514ce0fa7","media",File(uri.path!!).path
                    )
                    v.enqueue(object :Callback<Data> {
                        override fun onResponse(call: Call<Data>, response: Response<Data>) {
                            println("PreviewActivity.onResponse "+response.body()+" "+response.raw().request().url())
                        }

                        override fun onFailure(call: Call<Data>, t: Throwable) {
                        }
                    })
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