package com.HimanshuKumarGupta.orinplayer

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.HimanshuKumarGupta.orinplayer.databinding.ActivityPlayerBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class player_activity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayerBinding

    companion object {
        lateinit var musicListPA : ArrayList<Music>
        var songPosition: Int = 0
        var mediaPlayer : MediaPlayer? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolActivity)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setLayout()
        clickEventsPA()
    }

    private fun clickEventsPA() {
        binding.playPauseBtn.setOnClickListener {
            if (mediaPlayer!!.isPlaying) pauseMusic()
            else playMusic()
        }

        binding.previousBtn.setOnClickListener {
            setSongPosition(false)
            setImageText()
            createMediaPlayer()
        }

        binding.nextBtn.setOnClickListener {
            setSongPosition(true)
            setImageText()
            createMediaPlayer()
        }
    }

    private fun setSongPosition(increment : Boolean) {
        if(increment) {
            if (songPosition == musicListPA.size -1)
                songPosition = 0
            else
                songPosition++
        }
        else {
            if (songPosition == 0)
                songPosition = musicListPA.size-1
            else
                songPosition--
        }
    }

    private fun setLayout() {
        songPosition = intent.getIntExtra("index", 0)
        when(intent.getStringExtra("class")) {
            "MusicAdapter" -> {
                //Creating a reference to music list in main activity
                musicListPA = MainActivity.musicListMA
                setImageText()
                createMediaPlayer()
            }
        }
    }

    private fun setImageText() {
        Glide.with(this)
            .load(musicListPA[songPosition].artUri)
            .apply(RequestOptions().placeholder(R.drawable.app_icon).centerCrop())
            .into(binding.songImagePA)

        binding.songNamePA.text = musicListPA[songPosition].songNameM
    }

    private fun createMediaPlayer() {
        if(mediaPlayer == null) mediaPlayer = MediaPlayer()
        mediaPlayer!!.reset()
        mediaPlayer!!.setDataSource(musicListPA[songPosition].path)
        mediaPlayer!!.prepare()
        mediaPlayer!!.start()
    }

    private fun playMusic() {
        binding.playPauseBtn.setIconResource(R.drawable.pause_button)
        mediaPlayer!!.start()
    }

    private fun pauseMusic() {
        binding.playPauseBtn.setIconResource(R.drawable.play_btn)
        mediaPlayer!!.pause()
    }
}