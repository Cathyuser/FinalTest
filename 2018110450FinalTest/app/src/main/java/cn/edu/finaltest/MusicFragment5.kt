package cn.edu.finaltest

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cn.edu.finaltest.ViewModel.MusicViewModel
import cn.edu.finaltest.ViewModel.clockViewModel
import cn.edu.finaltest.musicfragment.MusicService
import kotlinx.android.synthetic.main.fragment_music.*
import kotlin.concurrent.thread


class MusicFragment5:Fragment(),ServiceConnection {
    var binder:MusicService.MusicBinder?=null
    val list: List<String> = listOf("#7C66A6", "#D5C963", "#87B2D5", "#83D387", "#D17393")
    var index=0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_music, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (ContextCompat.checkSelfPermission(
                activity!!,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                0
            )
        }else{
            startMusicService()
        }
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    binder?.currentPosition = progress
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
        activity?.findViewById<Button>(R.id.button_play)?.setOnClickListener {
            onPlay(view!!)
        }
        activity?.findViewById<Button>(R.id.button_pause)?.setOnClickListener {
            onPause(view!!)
        }
        activity?.findViewById<Button>(R.id.button_stop)?.setOnClickListener {
            onStop(view!!)
        }
        activity?.findViewById<Button>(R.id.button_next)?.setOnClickListener {
            onNext(view!!)
        }
        activity?.findViewById<Button>(R.id.button_prev)?.setOnClickListener {
            onPrev(view!!)
        }
    }
    fun onPlay(v: View) {
        val intent = Intent(activity, MusicService::class.java)
        intent.putExtra(MusicService.command, 1)
        activity?.startService(intent)
    }
    fun onPause(v: View){
        val intent= Intent(activity, MusicService::class.java)
        intent.putExtra(MusicService.command, 2)
        activity?.startService(intent)
    }
    fun onStop(v: View){
        val intent= Intent(activity, MusicService::class.java)
        intent.putExtra(MusicService.command, 3)
        activity?.startService(intent)
    }
    fun onNext(v: View){
        index=(index+1)%4
        linearLayout.setBackgroundColor(Color.parseColor(list.get(index)))
        val intent= Intent(activity, MusicService::class.java)
        intent.putExtra(MusicService.command, 4)
        activity?.startService(intent)
    }
    fun onPrev(v: View){
        index=(index+1)%4
        linearLayout.setBackgroundColor(Color.parseColor(list.get(index)))
        val intent= Intent(activity, MusicService::class.java)
        intent.putExtra(MusicService.command, 5)
        activity?.startService(intent)
    }
    fun startMusicService(){
        val intent = Intent(activity, MusicService::class.java)
        intent.putExtra(MusicService.command, 1)
        activity?.startService(intent)
        activity?.bindService(intent, this, Context.BIND_AUTO_CREATE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.unbindService(this)//解绑服务
    }
    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        binder=service as MusicService.MusicBinder//有返回内容时
        thread {
            while (binder!=null){
                Thread.sleep(1000)
                activity?.runOnUiThread{
                    seekBar.max = binder!!.duration
                    seekBar.progress = binder!!.currentPosition
                    textView_count.text = binder!!.musicName
                    textView2_name.text = "${binder!!.currentIndex+1}/${binder?.size}"
                }
            }
        }
    }
    override fun onServiceDisconnected(name: ComponentName?) {
        binder=null
        //没有返回内容
    }
}