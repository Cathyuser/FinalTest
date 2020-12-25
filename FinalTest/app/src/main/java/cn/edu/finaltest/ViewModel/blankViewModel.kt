package cn.edu.finaltest.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.edu.finaltest.BlankFragment2
import java.lang.StringBuilder

class blankViewModel:ViewModel() {
    lateinit var msgList: MutableList<BlankFragment2.Msg>

    fun addMsg(str:String): BlankFragment2.Msg {
        val randCount = (0..10).random()
        val builder = StringBuilder()
        for (i in 0..randCount){
            builder.append(str)
        }
        return BlankFragment2.Msg(builder.toString(), (0..1).random())
    }
}