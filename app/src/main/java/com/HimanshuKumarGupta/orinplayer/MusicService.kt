package com.HimanshuKumarGupta.orinplayer

import android.app.PendingIntent
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

        val previousButtonIntent = Intent(baseContext, NotificationReceiver::class.java)
            .setAction(ApplicationNotification.previousAppN)
        val previousPendingIntent = PendingIntent.
        getBroadcast(baseContext, 0, previousButtonIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val playButtonIntent = Intent(baseContext, NotificationReceiver::class.java)
            .setAction(ApplicationNotification.playAppN)
        val playPendingIntent = PendingIntent.
        getBroadcast(baseContext, 0, playButtonIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val nextButtonIntent = Intent(baseContext, NotificationReceiver::class.java)
            .setAction(ApplicationNotification.nextAppN)
        val nextPendingIntent = PendingIntent.
        getBroadcast(baseContext, 0, nextButtonIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val exitButtonIntent = Intent(baseContext, NotificationReceiver::class.java)
            .setAction(ApplicationNotification.exitAppN)
        val exitPendingIntent = PendingIntent.
        getBroadcast(baseContext, 0, exitButtonIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(baseContext, ApplicationNotification.channelIdAppN)
            .setContentTitle(player_activity.musicListPA[player_activity.songPosition].songNameM)
            .setContentText(player_activity.musicListPA[player_activity.songPosition].album)
            .setSmallIcon(R.drawable.favorite_icon)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.orinplayer_icon))
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle().setMediaSession(mediaSession.sessionToken))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOnlyAlertOnce(true)
            .addAction(R.drawable.previous_button, "Previous", previousPendingIntent )
            .addAction(R.drawable.play_btn, "Play", playPendingIntent)
            .addAction(R.drawable.forward_button, "Next", nextPendingIntent)
            .addAction(R.drawable.exit_icon, "Exit", exitPendingIntent)
            .build()

        startForeground(11, notification)
    }

}