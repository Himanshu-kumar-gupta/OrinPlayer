package com.HimanshuKumarGupta.orinplayer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.HimanshuKumarGupta.orinplayer.databinding.FragmentNowPlayingBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class NowPlaying : Fragment() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var binding: FragmentNowPlayingBinding
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_now_playing, container, false)
        binding = FragmentNowPlayingBinding.bind(view)

        // Removing visibility on creation as the
        // fragment should be visible when music is playing
        binding.root.visibility = View.INVISIBLE

        binding.playPauseBtnNowP.setOnClickListener {
            player_activity.setPlayPause()
        }

        binding.nextBtnNowP.setOnClickListener {
            setSongPosition(increment = true)
            player_activity.musicService!!.createMediaPlayer()

            Glide.with(this@NowPlaying)
                .load(player_activity.musicListPA[player_activity.songPosition].songImageMusic)
                .apply(RequestOptions().placeholder(R.drawable.orinplayer_icon).centerCrop())
                .into(binding.songImgNowP)

            binding.songNameNowP.text = player_activity.musicListPA[player_activity.songPosition].songNameM
            player_activity.playMusic()
        }

        return view
    }

    override fun onResume() {
        super.onResume()

        // Making visible when music is playing
        // and set layout accordingly
        if(player_activity.musicService!=null) {
            binding.root.visibility = View.VISIBLE
            Glide.with(this@NowPlaying)
                .load(player_activity.musicListPA[player_activity.songPosition].songImageMusic)
                .apply(RequestOptions().placeholder(R.drawable.orinplayer_icon).centerCrop())
                .into(binding.songImgNowP)

            binding.songNameNowP.text = player_activity.musicListPA[player_activity.songPosition].songNameM

            if(player_activity.musicService!!.mediaPlayer!!.isPlaying)
                binding.playPauseBtnNowP.setIconResource(R.drawable.pause_button)
            else
                binding.playPauseBtnNowP.setIconResource(R.drawable.play_btn)
        }
    }
}