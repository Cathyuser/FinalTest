package cn.edu.finaltest.ViewModel

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.edu.finaltest.gameFile
import cn.edu.sicnu.cardgame.CardMatchingGame
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.Exception

class gameViewModel : ViewModel() {
     var game = CardMatchingGame(24)

}