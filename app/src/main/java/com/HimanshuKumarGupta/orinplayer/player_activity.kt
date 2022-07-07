package com.HimanshuKumarGupta.orinplayer

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.HimanshuKumarGupta.orinplayer.databinding.ActivityPlayerBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class player_activity : AppCompatActivity(), ServiceConnection {
    private lateinit var binding: ActivityPlayerBinding

    companion object {
        lateinit var musicListPA : ArrayList<Music>
        var songPosition: Int = 0
        var musicService: MusicService? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolActivity)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Starting Service
        val intent = Intent(this@player_activity, MusicService::class.java)
        bindService(intent, this@player_activity, BIND_AUTO_CREATE)
        startService(intent)

        setLayout()
        clickEventsPA()
    }

    private fun clickEventsPA() {
        binding.playPauseBtn.setOnClickListener {
            if (musicService!!.mediaPlayer!!.isPlaying) pauseMusic()
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
            }

            "MainActivity" -> {
                musicListPA = ArrayList()
                musicListPA.addAll(MainActivity.musicListMA)
                musicListPA.shuffle()
                setImageText()
            }
        }
    }

    private fun setImageText() {
        Glide.with(this)
            .load(musicListPA[songPosition].artUri)
            .apply(RequestOptions().placeholder(R.drawable.orinplayer_icon).centerCrop())
            .into(binding.songImagePA)

        binding.songNamePA.text = musicListPA[songPosition].songNameM
    }

    private fun createMediaPlayer() {
        if(musicService!!.mediaPlayer == null) musicService!!.mediaPlayer = MediaPlayer()
        musicService!!.mediaPlayer!!.reset()
        musicService!!.mediaPlayer!!.setDataSource(musicListPA[songPosition].path)
        musicService!!.mediaPlayer!!.prepare()
        musicService!!.mediaPlayer!!.start()
    }

    private fun playMusic() {
        binding.playPauseBtn.setIconResource(R.drawable.pause_button)
        musicService!!.mediaPlayer!!.start()
    }

    private fun pauseMusic() {
        binding.playPauseBtn.setIconResource(R.drawable.play_btn)
        musicService!!.mediaPlayer!!.pause()
    }

    override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
        //type-casting p1 to MusicService
        val binder = p1 as MusicService.MyBinder
        musicService = binder.currentService()
        createMediaPlayer()
        musicService!!.showNotification()
    }

    override fun onServiceDisconnected(p0: ComponentName?) {
        musicService = null
    }
}