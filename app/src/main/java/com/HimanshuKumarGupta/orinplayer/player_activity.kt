package com.HimanshuKumarGupta.orinplayer

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.HimanshuKumarGupta.orinplayer.databinding.ActivityPlayerBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class player_activity : AppCompatActivity(), ServiceConnection, MediaPlayer.OnCompletionListener {


    companion object {
        lateinit var binding: ActivityPlayerBinding
        lateinit var musicListPA : ArrayList<Music>
        var songPosition: Int = 0
        var musicService: MusicService? = null
        var repeat: Boolean = false

        fun playMusic() {
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

    private fun previousSong() {
        setSongPosition(false)
        setImageText()
        createMediaPlayer()
    }

    private fun nextSong() {
        setSongPosition(true)
        createMediaPlayer()

        //Placing in try-catch block so that if application in foreground is closed
        //So we need not set image text for player_activity layout
        //And application can run in background with notification
        try {
            setImageText() } catch (e:Exception) { return }
    }

    private fun clickEventsPA() {
        binding.playPauseBtn.setOnClickListener {
            setPlayPause()
        }

        binding.previousBtn.setOnClickListener {
            previousSong()
        }

        binding.nextBtn.setOnClickListener {
            nextSong()
        }

        binding.seekBarPA.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, changeByUser: Boolean) {
                if (changeByUser) musicService!!.mediaPlayer!!.seekTo(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) = Unit
            override fun onStopTrackingTouch(p0: SeekBar?) = Unit
        })

        binding.repeatBtn.setOnClickListener {
            //Changing color on being selected
            if(!repeat) {
                repeat = true
                binding.repeatBtn.setColorFilter(ContextCompat
                    .getColor(this@player_activity, R.color.orange1))
            }else {
                //Reverting to original color if being selected again to unrepeated
                repeat= false
                binding.repeatBtn.setColorFilter(ContextCompat
                    .getColor(this@player_activity, R.color.default_button))
            }
        }

        binding.backBtnPlayerA.setOnClickListener {
            finish()
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

            "NotificationNext" -> {
                nextSong()
            }
        }
    }

    private fun setImageText() {
        Glide.with(this@player_activity)
            .load(musicListPA[songPosition].songImageMusic)
            .apply(RequestOptions().placeholder(R.drawable.orinplayer_icon).centerCrop())
            .into(binding.songImagePA)

        binding.songNamePA.text = musicListPA[songPosition].songNameM

        if(repeat) binding.repeatBtn.setColorFilter(ContextCompat
            .getColor(this@player_activity, R.color.orange1))
    }

    private fun createMediaPlayer() {
        if(musicService!!.mediaPlayer == null) musicService!!.mediaPlayer = MediaPlayer()
        musicService!!.mediaPlayer!!.reset()
        musicService!!.mediaPlayer!!.setDataSource(musicListPA[songPosition].path)
        musicService!!.mediaPlayer!!.prepare()
        musicService!!.mediaPlayer!!.start()
        musicService!!.showNotification(R.drawable.pause_button)

        //Settings for seekbar
        binding.seekBarStartPA.text = formatDuration(musicService!!.mediaPlayer!!.currentPosition.toLong())
        binding.seekBarEndPA.text = formatDuration(musicService!!.mediaPlayer!!.duration.toLong())
        binding.seekBarPA.progress = 0
        binding.seekBarPA.max= musicService!!.mediaPlayer!!.duration

        //On song completion
        musicService!!.mediaPlayer!!.setOnCompletionListener(this@player_activity)
    }


    override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
        //type-casting p1 to MusicService
        val binder = p1 as MusicService.MyBinder
        musicService = binder.currentService()
        createMediaPlayer()
        musicService!!.seekBarSetup()
    }

    override fun onServiceDisconnected(p0: ComponentName?) {
        musicService = null
    }

    //On song completion
    override fun onCompletion(p0: MediaPlayer?) {
        nextSong()
    }
}