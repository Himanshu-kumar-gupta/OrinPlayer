package com.HimanshuKumarGupta.orinplayer

import android.media.MediaMetadataRetriever
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

data class Music(
    val id: String,
    val songNameM: String,
    val album: String,
    val artist: String,
    val duration: Long = 0,
    val path: String,
    val songImageMusic: ByteArray?
)

fun formatDuration(duration: Long) : String {
    val minutes = TimeUnit.MINUTES.convert(duration,TimeUnit.MILLISECONDS)
    val remainSec =  TimeUnit.SECONDS.convert(duration,TimeUnit.MILLISECONDS) -
            minutes*TimeUnit.SECONDS.convert(1,TimeUnit.MINUTES)

    return String.format("%02d:%02d", minutes, remainSec)
}

fun getImageArt(path: String): ByteArray? {
    val retriever = MediaMetadataRetriever()
    retriever.setDataSource(path)
    return retriever.embeddedPicture
}

fun setSongPosition(increment : Boolean) {

    if (!player_activity.repeat) {
        if (increment) {
            // If increment at last position set it first song
            if (player_activity.songPosition == player_activity.musicListPA.size - 1)
                player_activity.songPosition = 0
            else
                player_activity.songPosition++
        } else {
            // If decrement at first position set it last song
            if (player_activity.songPosition == 0)
                player_activity.songPosition = player_activity.musicListPA.size - 1
            else
                player_activity.songPosition--
        }
    }
}

fun exitApplication() {
    if (player_activity.musicService !=null) {
        player_activity.musicService!!.stopForeground(true)
        player_activity.musicService!!.mediaPlayer!!.release()
        player_activity.musicService = null
    }
    exitProcess(1)
}