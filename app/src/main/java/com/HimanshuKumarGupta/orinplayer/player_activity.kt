package com.HimanshuKumarGupta.orinplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.HimanshuKumarGupta.orinplayer.databinding.ActivityPlayerBinding

class player_activity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolActivity)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_player)
    }
}