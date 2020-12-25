package cn.edu.finaltest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment1 =  GameFragment()
        val fragment2 = BlankFragment2()
        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout,fragment1)
                .commit()
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNV)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.game ->
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout,fragment1)
                        .commit()
                R.id.blank ->
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout,fragment2)
                        .commit()
            }
            true
        }

        val intentFilter = IntentFilter(CHAT_INTENT)
        val receiver = MyReceiver()
        registerReceiver(receiver,intentFilter)

    }

    fun printFragments() {
        supportFragmentManager.fragments.forEach {
            Log.d("Fragment","id: ${it}")
        }

    }
    class MyReceiver: BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            Log.d("BroadcastReceiver","onReceive")
        }

    }
}