package com.HimanshuKumarGupta.orinplayer

data class Music(
    val id: String,
    val songNameM: String,
    val album: String,
    val artist: String,
    val duration: Long = 0,
    val path: String,
    val artUri:String)
