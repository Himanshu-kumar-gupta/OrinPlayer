package com.HimanshuKumarGupta.orinplayer

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.HimanshuKumarGupta.orinplayer.databinding.ActivityMainBinding
import java.io.File
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var musicAdapter: MusicAdapter
    private var checkStorage = false

    companion object {
        lateinit var musicListMA: ArrayList<Music>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolNav)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toggle = ActionBarDrawerToggle(this, binding.root,R.string.open,R.string.close)
        binding.root.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        requestRuntimePermissions()

        //Calling after permissions are granted
        if (checkStorage)
            initializeMusic()

        clickEventsMA()
    }

    private fun clickEventsMA() {

        //Shuffle button accesses the musicListMA , so calling after
        if(checkStorage) {
            binding.ShuffleBtn.setOnClickListener {
                val intent = Intent(this@MainActivity, player_activity::class.java)
                intent.putExtra("index", 0)
                intent.putExtra("class", "MainActivityShuffle")
                startActivity(intent)
            }
        }

        binding.favouriteBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, favourite_activity::class.java))
        }

        binding.playlistBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, playlistActivity::class.java))
        }

        binding.navigationView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.navFeedback -> Toast.makeText(this,"Feedback clicked", Toast.LENGTH_SHORT).show()
                R.id.navSettings -> Toast.makeText( this,"Settings clicked", Toast.LENGTH_SHORT).show()
                R.id.navAbout -> Toast.makeText(this,"About Section Clicked", Toast.LENGTH_SHORT).show()
                R.id.navExit -> exitProcess(1)
            }
            true
        }
    }

    private fun initializeMusic() {
        musicListMA = getAllMusicFiles()
        binding.MusicRecycleView.setHasFixedSize(true)
        binding.MusicRecycleView.setItemViewCacheSize(15)
        binding.MusicRecycleView.layoutManager = LinearLayoutManager(this@MainActivity)
        musicAdapter = MusicAdapter(this@MainActivity, musicListMA)
        binding.MusicRecycleView.adapter = musicAdapter
        binding.totalSongs.text = "Total Songs : "+ musicAdapter.itemCount
    }

    private fun requestRuntimePermissions() {
        checkStorage = if(ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),11)
            false
        } else
            true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode==11) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkStorage = true
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                //Calling the below functions as permission are granted first time, so these functions will not be called
                //they will be called after restart of app
                initializeMusic()
                clickEventsMA()
            }
            else if (shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //can display permission denied in recycler view
                ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),11)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }

    private fun getAllMusicFiles(): ArrayList<Music> {
        val tempMusicList = ArrayList<Music>()
        val selection = MediaStore.Audio.Media.IS_MUSIC + " !=0"
        val projection = arrayOf( MediaStore.Audio.Media._ID,
                                MediaStore.Audio.Media.TITLE,
                                MediaStore.Audio.Media.ALBUM,
                                MediaStore.Audio.Media.ARTIST,
                                MediaStore.Audio.Media.DURATION,
                                MediaStore.Audio.Media.DATE_ADDED,
                                MediaStore.Audio.Media.DATA,
                                MediaStore.Audio.Media.ALBUM_ID)

        val cursor = this.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection, selection, null,
            MediaStore.Audio.Media.DATE_ADDED + " DESC",null)

        if (cursor != null) {
            if (cursor.moveToFirst())
                do {
                    // Appending C in variable to denote cursor variable
                    val songNameC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    val idC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                    val albumC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    val artistC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val pathC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val durationC = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    val songImageC= getImageArt(pathC)
                    val musicObj = Music(
                        id = idC, songNameM = songNameC, album = albumC,
                        artist = artistC, duration = durationC, path = pathC, songImageMusic = songImageC)

                    val file = File(pathC)
                    if (file.exists())
                        tempMusicList.add(musicObj)
                }while (cursor.moveToNext())

            cursor.close()
        }

        return tempMusicList
    }
}

