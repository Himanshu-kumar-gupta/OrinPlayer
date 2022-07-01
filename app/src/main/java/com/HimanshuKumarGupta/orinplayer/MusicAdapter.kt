package com.HimanshuKumarGupta.orinplayer

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.HimanshuKumarGupta.orinplayer.databinding.MusicListViewBinding

class MusicAdapter(private val context: Context,private val musicList: ArrayList<String>) :
    RecyclerView.Adapter<MusicAdapter.MusicHolder>() {

    class MusicHolder(binding: MusicListViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val song_name = binding.SongNameMusicView
        val song_album = binding.SongAlbumMusicView
        val song_image = binding.SongImageMusicView
        val song_duration = binding.SongDurationMusicView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicHolder {
        return MusicHolder(MusicListViewBinding.inflate(LayoutInflater.from(context),parent, false))
    }

    override fun onBindViewHolder(holder: MusicHolder, position: Int) {
        holder.song_name.text = musicList[position]
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

}