package com.HimanshuKumarGupta.orinplayer

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.HimanshuKumarGupta.orinplayer.databinding.MusicListViewBinding

class MusicAdapter(private val context: Context,private val musicList: ArrayList<String>) :
    RecyclerView.Adapter<MusicAdapter.MusicHolder>() {

    class MusicHolder(binding: MusicListViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val songName = binding.SongNameMusicView
        val songAlbum = binding.SongAlbumMusicView
        val songImage = binding.SongImageMusicView
        val songDuration = binding.SongDurationMusicView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicHolder {
        return MusicHolder(MusicListViewBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: MusicHolder, position: Int) {
        holder.songName.text = musicList[position]
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

}