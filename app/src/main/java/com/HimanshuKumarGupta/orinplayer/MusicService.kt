package com.HimanshuKumarGupta.orinplayer

import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat

class MusicService: Service() {
    private var myBinder = MyBinder()
    var mediaPlayer:MediaPlayer? = null
    private lateinit var mediaSession: MediaSessionCompat

    override fun onBind(p0: Intent?): IBinder {
        mediaSession = MediaSessionCompat(baseContext, "My Music")
        return myBinder
    }

    inner class MyBinder:Binder() {
        fun currentService(): MusicService {
            return this@MusicService
        }
    }

    fun showNotification() {
        val notification = NotificationCompat.Builder(baseContext, ApplicationNotification.channelIdAppN)
            .setContentTitle(player_activity.musicListPA[player_activity.songPosition].songNameM)
            .setContentText(player_activity.musicListPA[player_activity.songPosition].album)
            .setSmallIcon(R.drawable.playlist_icon)
            .setLargeIcon(BitmapFactory.decodeResource(resources,R.drawable.app_icon))
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle().setMediaSession(mediaSession.sessionToken))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOnlyAlertOnce(true)
            .addAction(R.drawable.back_button, "Previous", null)
            .addAction(R.drawable.play_btn, "Play", null)
            .addAction(R.drawable.forward_button, "Next", null)
            .addAction(R.drawable.exit_icon, "Exit", null)
            .build()

        startForeground(11, notification)
    }


}