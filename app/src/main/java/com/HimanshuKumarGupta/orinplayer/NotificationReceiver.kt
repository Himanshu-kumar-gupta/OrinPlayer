package com.HimanshuKumarGupta.orinplayer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import kotlin.system.exitProcess

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        when(p1?.action) {
            ApplicationNotification.previousAppN ->
                Toast.makeText(p0, "Previous Button Clicked", Toast.LENGTH_SHORT).show()

            ApplicationNotification.playAppN ->
                Toast.makeText(p0, "Play Button Clicked", Toast.LENGTH_SHORT).show()

            ApplicationNotification.nextAppN ->
                Toast.makeText(p0, "Next button clicked", Toast.LENGTH_SHORT).show()

            ApplicationNotification.exitAppN -> exitProcess(1)
        }
    }
}