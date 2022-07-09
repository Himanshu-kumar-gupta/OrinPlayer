package com.HimanshuKumarGupta.orinplayer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlin.system.exitProcess

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        when(p1?.action) {
            ApplicationNotification.previousAppN -> player_activity().previousSong()

            ApplicationNotification.playAppN -> player_activity.setPlayPause()

            ApplicationNotification.nextAppN -> {
                val intent = Intent(p0, player_activity::class.java)
                intent.putExtra("class", "NotificationNext")
            }

            ApplicationNotification.exitAppN -> {
                player_activity.musicService!!.stopForeground(true)
                player_activity.musicService = null
                exitProcess(1)
            }
        }
    }
}