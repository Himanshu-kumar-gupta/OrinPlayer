package com.HimanshuKumarGupta.orinplayer

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Build.VERSION_CODES.O

class ApplicationNotification : Application() {

    companion object {
        const val channelIdAppN = "channel1"
        const val playAppN = "play"
        const val nextAppN = "next"
        const val previousAppN = "previous"
        const val exitAppN = "exit"
    }

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= O) {
            val notificationChannel = NotificationChannel(channelIdAppN, "Now Playing Song",
                NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.description = "This is an important channel for showing songs"
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}