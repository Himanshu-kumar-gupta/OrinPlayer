package com.HimanshuKumarGupta.orinplayer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        when(p1?.action) {
            ApplicationNotification.previousAppN -> previousNextSong(increment = false, context = p0!!)
            ApplicationNotification.playAppN -> player_activity.setPlayPause()
            ApplicationNotification.nextAppN -> previousNextSong(increment = true, context = p0!!)
            ApplicationNotification.exitAppN -> exitApplication()
        }
    }

    private fun previousNextSong(increment: Boolean, context: Context ) {
        setSongPosition(increment = increment)
        player_activity.musicService!!.createMediaPlayer()

        // Changed image and text of Player Activity
        Glide.with(context)
            .load(player_activity.musicListPA[player_activity.songPosition].songImageMusic)
            .apply(RequestOptions().placeholder(R.drawable.orinplayer_icon).centerCrop())
            .into(player_activity.binding.songImagePA)

        player_activity.binding.songNamePA.text = player_activity.musicListPA[player_activity.songPosition].songNameM

        // Changed image and text of Now playing fragment
        Glide.with(context)
            .load(player_activity.musicListPA[player_activity.songPosition].songImageMusic)
            .apply(RequestOptions().placeholder(R.drawable.orinplayer_icon).centerCrop())
            .into(NowPlaying.binding.songImgNowP)

        NowPlaying.binding.songNameNowP.text = player_activity.musicListPA[player_activity.songPosition].songNameM

        player_activity.playMusic()
    }

}