package com.HimanshuKumarGupta.orinplayer

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.HimanshuKumarGupta.orinplayer.databinding.MusicListViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MusicAdapter(private val context: Context,private val musicList: ArrayList<Music>) :
    RecyclerView.Adapter<MusicAdapter.MusicHolder>() {

    class MusicHolder(binding: MusicListViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val songName = binding.SongNameMusicView
        val songAlbum = binding.SongAlbumMusicView
        val songImage = binding.SongImageMusicView
        val songDuration = binding.SongDurationMusicView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicHolder {
        return MusicHolder(MusicListViewBinding.inflate(LayoutInflater.from(context),parent, false))
    }

    override fun onBindViewHolder(holder: MusicHolder, position: Int) {
        holder.songName.text = musicList[position].songNameM
        holder.songAlbum.text = musicList[position].album
        holder.songDuration.text = musicList[position].duration.toString()
        Glide.with(context)
            .load(musicList[position].artUri)
            .apply(RequestOptions().placeholder(R.drawable.app_icon).centerCrop())
            .into(holder.songImage)
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

}