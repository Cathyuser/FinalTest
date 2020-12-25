package cn.edu.finaltest.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.edu.sicnu.cardgame.CardMatchingGame

class gameViewModel : ViewModel() {
     var game = CardMatchingGame(24)

}