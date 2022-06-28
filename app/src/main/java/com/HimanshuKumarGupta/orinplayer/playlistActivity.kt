package com.HimanshuKumarGupta.orinplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.HimanshuKumarGupta.orinplayer.databinding.ActivityPlaylistBinding

class playlistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlaylistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_OrinPlayer)
        binding = ActivityPlaylistBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_playlist)
    }
}