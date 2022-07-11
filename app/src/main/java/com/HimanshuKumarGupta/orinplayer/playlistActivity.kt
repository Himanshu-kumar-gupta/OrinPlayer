package com.HimanshuKumarGupta.orinplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.HimanshuKumarGupta.orinplayer.databinding.ActivityPlaylistBinding

class playlistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlaylistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolActivity)
        binding = ActivityPlaylistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtnPlaylistA.setOnClickListener {
            finish()
        }
    }
}