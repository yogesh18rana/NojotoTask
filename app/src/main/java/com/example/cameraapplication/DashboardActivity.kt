package com.example.cameraapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cameraapplication.databinding.ActivityDashboard2Binding
import com.example.cameraapplication.recycler.DashboardAdapter
import com.example.cameraapplication.recycler.NameProfile

class DashboardActivity : AppCompatActivity() {

  lateinit var binding: ActivityDashboard2Binding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityDashboard2Binding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

    binding.cameraButton.setOnClickListener{
      val intent = Intent(this, MainActivity::class.java)
      startActivity(intent)
    }

    val uploadImages = mutableListOf<NameProfile>() as ArrayList<NameProfile>
    uploadImages.add(NameProfile((R.drawable.landcape), "Jonny depp","Posted on 8:34 PM,  10 January 2023",
    "Johnny Depp : my favourite place on earth","12 likes" , "86 comments" ))
    uploadImages.add(NameProfile((R.drawable.mountain), "Jonny depp","Posted on 8:34 PM,  10 January 2023",
      "Johnny Depp : my favourite place on earth","12 likes" , "86 comments" ))
    uploadImages.add(NameProfile((R.drawable.jhonny), "Jonny depp","Posted on 8:34 PM,  10 January 2023",
      "Johnny Depp : my favourite place on earth","12 likes" , "86 comments" ))


    binding.rvImage.adapter = DashboardAdapter(uploadImages)
    binding.rvImage.layoutManager = LinearLayoutManager(this)
  }
}