package com.HimanshuKumarGupta.orinplayer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlin.system.exitProcess

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        when(p1?.action) {
            ApplicationNotification.previousAppN -> previousNextSong(increment = false, context = p0!!)

            ApplicationNotification.playAppN -> player_activity.setPlayPause()

            ApplicationNotification.nextAppN -> previousNextSong(increment = true, context = p0!!)


            ApplicationNotification.exitAppN -> {
                player_activity.musicService!!.stopForeground(true)
                player_activity.musicService!!.mediaPlayer!!.release()
                player_activity.musicService = null
                exitProcess(1)
            }
        }
    }

    private fun previousNextSong(increment: Boolean, context: Context ) {
        setSongPosition(increment = increment)
        player_activity.musicService!!.createMediaPlayer()
        Glide.with(context)
            .load(player_activity.musicListPA[player_activity.songPosition].songImageMusic)
            .apply(RequestOptions().placeholder(R.drawable.orinplayer_icon).centerCrop())
            .into(player_activity.binding.songImagePA)

        player_activity.binding.songNamePA.text = player_activity.musicListPA[player_activity.songPosition].songNameM
        player_activity.playMusic()
    }

}