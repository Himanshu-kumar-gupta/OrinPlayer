package com.HimanshuKumarGupta.orinplayer

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.HimanshuKumarGupta.orinplayer.databinding.MusicListViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MusicAdapter(private val context: Context,private var musicList: ArrayList<Music>) :
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
            when {
                // Change PlayerActivity music list acc to search results
                MainActivity.search -> openPlayerActivity(reference = "MusicAdapterSearch", pos = position)
                else -> openPlayerActivity(reference = "MusicAdapter", pos = position)
            }
        }
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    // Updates music list of Adapter after search
    @SuppressLint("NotifyDataSetChanged")
    fun updateMusicList() {
        musicList = ArrayList()
        musicList.addAll(MainActivity.musicSearchList)
        notifyDataSetChanged()
    }

    private fun openPlayerActivity(reference: String, pos: Int) {
        val intent = Intent(context, player_activity::class.java)
        intent.putExtra("index", pos)
        intent.putExtra("class", reference)
        ContextCompat.startActivity(context,intent,null)
    }
}