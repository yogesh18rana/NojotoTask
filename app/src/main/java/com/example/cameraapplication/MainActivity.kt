 package com.example.cameraapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.cameraapplication.databinding.ActivityMainBinding

 class MainActivity : AppCompatActivity() {

   lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    val homeFragment = HomeFragment()
    val cartFragment = CartFragment()

    setCurrentFragment(homeFragment)

    binding.bottomNavView.setOnItemReselectedListener {
      when (it.itemId) {
        R.id.home_fragment -> setCurrentFragment(homeFragment)
        R.id.cart_fragment -> setCurrentFragment(cartFragment)
      }
      true
    }
  }

 private fun setCurrentFragment(fragment: Fragment)=
   supportFragmentManager.beginTransaction().
     replace(R.id.nav_host_fragment,fragment)
     .commit()
}