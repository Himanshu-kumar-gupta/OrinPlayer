package com.HimanshuKumarGupta.orinplayer

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
        val rootBind = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicHolder {
        return MusicHolder(MusicListViewBinding.inflate(LayoutInflater.from(context),parent, false))
    }

    override fun onBindViewHolder(holder: MusicHolder, position: Int) {
        holder.songName.text = musicList[position].songNameM
        holder.songAlbum.text = musicList[position].album
        holder.songDuration.text = formatDuration(musicList[position].duration)
        Glide.with(context)
            .load(musicList[position].songImageMusic)
            .apply(RequestOptions().placeholder(R.drawable.orinplayer_icon).centerCrop())
            .into(holder.songImage)

        holder.rootBind.setOnClickListener {
            val intent = Intent(context, player_activity::class.java)
            intent.putExtra("index", position)
            intent.putExtra("class", "MusicAdapter")
            ContextCompat.startActivity(context,intent,null)
        }
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

}