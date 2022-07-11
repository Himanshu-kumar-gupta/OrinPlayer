package com.HimanshuKumarGupta.orinplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.HimanshuKumarGupta.orinplayer.databinding.ActivityFavouriteBinding

class favourite_activity : AppCompatActivity() {

    private lateinit var binding: ActivityFavouriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolActivity)
        binding= ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtnFaVA.setOnClickListener {
            finish()
        }
    }
}