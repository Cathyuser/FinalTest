package cn.edu.finaltest

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cn.edu.finaltest.ViewModel.clockViewModel
import kotlinx.android.synthetic.main.fragment_clock.*

class ClockFragment3 :Fragment(){
    lateinit var viewmodel:clockViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel=ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(clockViewModel::class.java)
        viewmodel.seconds.observe(viewLifecycleOwner, Observer {
            val hours = it / 3600
            val minutes = (it % 3600) / 60
            val secs = it % 60
            textView_clock.text = String.format("%02d:%02d:%02d", hours, minutes, secs)
        })
        button_start.setOnClickListener {
            viewmodel.start()
        }
        button_stop.setOnClickListener {
            viewmodel.stop()
        }
        button_restart.setOnClickListener {
            viewmodel.restart()
        }
    }



}