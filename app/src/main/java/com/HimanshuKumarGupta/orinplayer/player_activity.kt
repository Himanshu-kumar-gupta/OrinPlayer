package com.HimanshuKumarGupta.orinplayer

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.HimanshuKumarGupta.orinplayer.databinding.ActivityPlayerBinding

class player_activity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding

    companion object {
        lateinit var musicListPA : ArrayList<Music>
        var songPosition: Int = 0
        lateinit var mediaPlayer : MediaPlayer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolActivity)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_player)

        songPosition = intent.getIntExtra("index", 0)
        when(intent.getStringExtra("class")) {
            "MusicAdapter" -> {
                //Creating a reference to music list in main activity
                musicListPA = MainActivity.musicListMA
                mediaPlayer = MediaPlayer()
                mediaPlayer.reset()
                mediaPlayer.setDataSource(musicListPA[songPosition].path)
                mediaPlayer.prepare()
                mediaPlayer.start()

            }

        }
    }
}