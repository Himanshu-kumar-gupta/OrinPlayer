package com.HimanshuKumarGupta.orinplayer

import android.os.Build.VERSION_CODES.M
import java.util.concurrent.TimeUnit

data class Music(
    val id: String,
    val songNameM: String,
    val album: String,
    val artist: String,
    val duration: Long = 0,
    val path: String,
    val artUri:String)

fun formatDuration(duration: Long) : String {
    val minutes = TimeUnit.MINUTES.convert(duration,TimeUnit.MILLISECONDS)
    val remainSec =  TimeUnit.SECONDS.convert(duration,TimeUnit.MILLISECONDS) -
            minutes*TimeUnit.SECONDS.convert(1,TimeUnit.MINUTES)

    return String.format("%02d:%02d", minutes, remainSec)
}