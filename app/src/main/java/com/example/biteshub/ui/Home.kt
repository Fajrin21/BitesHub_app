package com.example.biteshub.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.biteshub.Camera
import com.example.biteshub.Postingan
import com.example.biteshub.R
import com.example.biteshub.Reels
import com.example.biteshub.databinding.ActivityHomeBinding

class home : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Postingan())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.post -> replaceFragment(Postingan())
                R.id.camera -> replaceFragment(Camera())
                R.id.reels -> replaceFragment(Reels())

                else ->{


                }

            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.framelayout,fragment)
        fragmentTransaction.commit()
    }
}