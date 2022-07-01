package com.HimanshuKumarGupta.orinplayer

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.HimanshuKumarGupta.orinplayer.databinding.ActivityMainBinding
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var musicAdapter: MusicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolNav)
        requestRuntimePermissions()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toggle = ActionBarDrawerToggle(this, binding.root,R.string.open,R.string.close)
        binding.root.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val musicList = ArrayList<String>()
        musicList.add("1 song name")
        musicList.add("2 song name")
        musicList.add("3 song name")
        musicList.add("4 song name")
        musicList.add("5 song name")
        musicList.add("6 song name")

        binding.MusicRecycleView.setHasFixedSize(true)
        binding.MusicRecycleView.setItemViewCacheSize(15)
        binding.MusicRecycleView.layoutManager = LinearLayoutManager(this@MainActivity)
        musicAdapter = MusicAdapter(this@MainActivity, musicList)
        binding.MusicRecycleView.adapter = musicAdapter
        binding.totalSongs.text = "Total Songs : "+ musicAdapter.itemCount

        binding.ShuffleBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, player_activity::class.java))
        }

        binding.favouriteBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, favourite_activity::class.java))
        }

        binding.playlistBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, playlistActivity::class.java))
        }

        binding.navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navFeedback -> Toast.makeText(this,"Feedback clicked", Toast.LENGTH_SHORT).show()
                R.id.navSettings -> Toast.makeText(this,"Settings clicked", Toast.LENGTH_SHORT).show()
                R.id.navAbout -> Toast.makeText(this,"About Section Clicked", Toast.LENGTH_SHORT).show()
                R.id.navExit -> exitProcess(1)
            }
            true
        }

    }

    private fun requestRuntimePermissions(){
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),11)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode==11){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Permission is Granted", Toast.LENGTH_SHORT).show()
            else{
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }
}

