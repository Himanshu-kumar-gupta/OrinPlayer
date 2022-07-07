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


    companion object {
        lateinit var binding: ActivityPlayerBinding
        lateinit var musicListPA : ArrayList<Music>
        var songPosition: Int = 0
        var musicService: MusicService? = null

        private fun playMusic() {
            musicService!!.showNotification(R.drawable.pause_button)
            binding.playPauseBtn.setIconResource(R.drawable.pause_button)
            musicService!!.mediaPlayer!!.start()
        }

        private fun pauseMusic() {
            musicService!!.showNotification(R.drawable.play_btn)
            binding.playPauseBtn.setIconResource(R.drawable.play_btn)
            musicService!!.mediaPlayer!!.pause()
        }

        fun setPlayPause() {
            if (musicService!!.mediaPlayer!!.isPlaying) pauseMusic()
            else playMusic()
        }
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
            setPlayPause()
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
            // If increment at last position set it first song
            if (songPosition == musicListPA.size -1)
                songPosition = 0
            else
                songPosition++
        }
        else {
            // If decrement at first position set it last song
            if (songPosition == 0)
                songPosition = musicListPA.size-1
            else
                songPosition--
        }
    }

    private fun setLayout() {
        songPosition = intent.getIntExtra("index", 0)
        when(intent.getStringExtra("class")) {
            //Passing from recycler view adapter
            "MusicAdapter" -> {
                //Creating a reference to music list in main activity
                musicListPA = MainActivity.musicListMA
                setImageText()
            }

            //Passing from shuffle button
            "MainActivityShuffle" -> {
                musicListPA = ArrayList()
                musicListPA.addAll(MainActivity.musicListMA)
                musicListPA.shuffle()
                setImageText()
            }
        }
    }

    private fun setImageText() {
        Glide.with(this@player_activity)
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


    override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
        //type-casting p1 to MusicService
        val binder = p1 as MusicService.MyBinder
        musicService = binder.currentService()
        createMediaPlayer()
        musicService!!.showNotification(R.drawable.pause_button)
    }

    override fun onServiceDisconnected(p0: ComponentName?) {
        musicService = null
    }
}