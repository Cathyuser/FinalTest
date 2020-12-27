package cn.edu.finaltest.ViewModel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.edu.finaltest.BlankFragment2
import cn.edu.finaltest.ChatFile
import java.io.ObjectInputStream
import java.io.Serializable
import java.lang.Exception
import java.lang.StringBuilder

class blankViewModel:ViewModel() {
    lateinit var msgList: MutableList<Msg>

    fun addMsg(str:String): Msg {
        val randCount = (0..10).random()
        val builder = StringBuilder()
        for (i in 0..randCount){
            builder.append(str)
        }
        return Msg(builder.toString(), (0..1).random())
    }
    data class Msg(val content:String, val type:Int): Serializable {
        companion object {
            const val RECEIVE = 0
            const val SEND = 1
        }
    }

}