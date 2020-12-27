package cn.edu.finaltest.musicfragment

import android.app.Service
import android.content.ContentValues.TAG
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.provider.MediaStore
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MusicService : Service() {
    var mediaPlayer = MediaPlayer()
    var musicList = mutableListOf<String>()
    var musicNameList = mutableListOf<String>()
    var current=0
    var isPause=false
    val TAG="MusicService"
    val binder = MusicBinder()
    companion object{
        val command="operate"
    }
    inner class MusicBinder : Binder(){
        val musicName
            get() = musicNameList.get(current)
        var currentPosition
            get()=mediaPlayer.currentPosition
            set(value) = mediaPlayer.seekTo(value)
        var duration = 0
            get() = mediaPlayer.duration
        val size
            get() = musicList.size
        val currentIndex
            get() = current
    }
    override fun onCreate() {
        super.onCreate()
        getMusicList()
        mediaPlayer.setOnPreparedListener{
            it.start()
        }
        mediaPlayer.setOnCompletionListener {
            current++
            if (current>=musicList.size){
                current=0
            }
            play()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val operate = intent?.getIntExtra(command,1) ?:1
        when (operate){
            1 -> play()
            2 -> pause()
            3 -> stop()
            4 -> next()
            5 -> prev()
        }
        return super.onStartCommand(intent, flags, startId)
    }
    fun pause(){
        if (isPause){
            mediaPlayer.start()
            isPause=false
        }
        else{
            mediaPlayer.pause()
            isPause=true
        }
    }
    fun stop(){
        mediaPlayer.stop()
        stopSelf()
    }
    fun next(){
        current++
        if (current>=musicList.size){
            current=0
        }
        play()
    }
    fun prev(){
        current--
        if (current<0){
            current=musicList.size-1
        }
        play()
    }

    fun play(){
        if (musicList.size==0) return
        val path = musicList[current]
        mediaPlayer.reset()
        mediaPlayer.setDataSource(path)
        mediaPlayer.prepareAsync()
    }
    private fun getMusicList(){
        val cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,null)
        if (cursor!=null){
            while (cursor.moveToNext()){
                val musicPath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                musicList.add(musicPath)
                val musicName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                musicNameList.add(musicName)
                Log.d(TAG,"getMusicList:$musicPath name:$musicName")
            }
        }
    }
    override fun onBind(intent: Intent): IBinder {
        return binder
    }

}
