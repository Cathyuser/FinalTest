package cn.edu.finaltest.ViewModel

import android.app.Application
import android.content.Intent
import android.util.Log
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.edu.finaltest.CityItem
import cn.edu.finaltest.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.concurrent.thread

class CityViewModel(application: Application) : AndroidViewModel(application) {
    private var _cities : MutableLiveData<List<CityItem>> = MutableLiveData()
    val cities:LiveData<List<CityItem>> = _cities

    init{
        thread {
            val str = readFileFromRaw(R.raw.citycode)
            val gson = Gson()
            val Citytype = object: TypeToken<List<CityItem>>(){}.type
            var cts:List<CityItem> = gson.fromJson(str,Citytype)
            cts = cts.filter { it.city_code!=""}
            _cities.postValue(cts)
        }
    }
    fun readFileFromRaw(rawName: Int): String? {
        try {
            val inputReader = InputStreamReader(getApplication<Application>().resources.openRawResource(rawName))
            val bufReader = BufferedReader(inputReader)
            var line: String? = ""
            var result: String? = ""
            while (bufReader.readLine().also({ line = it }) != null) {
                result += line
            }
            return result
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}