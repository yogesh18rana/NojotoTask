package com.example.cameraapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cameraapplication.databinding.ActivityDashboard2Binding
import com.example.cameraapplication.recycler.DashboardAdapter
import com.example.cameraapplication.recycler.NameProfile
import com.google.android.material.bottomnavigation.BottomNavigationView


class DashboardActivity : AppCompatActivity() {

    lateinit var binding: ActivityDashboard2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboard2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val navView: BottomNavigationView = binding.navView
        navItemClick(navView)

        val uploadImages = mutableListOf<NameProfile>() as ArrayList<NameProfile>
        uploadImages.add(
            NameProfile(
                (R.drawable.landcape), "Jonny depp", "1 mins ago",
                "Johnny Depp : my favourite place on earth", "12 likes", "86 comments"
            )
        )
        uploadImages.add(
            NameProfile(
                (R.drawable.mountain), "Jonny depp", "3 hrs ago",
                "Johnny Depp : my favourite place on earth", "12 likes", "86 comments"
            )
        )
        uploadImages.add(
            NameProfile(
                (R.drawable.jhonny), "Jonny depp", "20 mins ago",
                "Johnny Depp : my favourite place on earth", "12 likes", "86 comments"
            )
        )


        binding.rvImage.adapter = DashboardAdapter(uploadImages)
        binding.rvImage.layoutManager = LinearLayoutManager(this)
    }

    private fun navItemClick(navView: BottomNavigationView) {
        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_fragment -> {

                }
                R.id.navigation_dashboard -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }
    }
}