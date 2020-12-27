package cn.edu.finaltest

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import cn.edu.finaltest.R.string
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.weathersecond.*

class ActivitySecond  : AppCompatActivity() {

    val baseURL = "http://t.weather.itboy.net/api/weather/city/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weathersecond)
        val cityCode = intent.getStringExtra("city_code")
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(baseURL+cityCode,{
            val gson = Gson()
            val WeatherType = object :TypeToken<Weather>(){}.type
            val weather = gson.fromJson<Weather>(it,WeatherType)
            textView_city.text = weather.cityInfo.city
            textView_province.text = weather.cityInfo.parent
            textView_shidu.text = getString(string.shidu)+weather.data.shidu
            textView_wendu.text = getString(string.wendu)+weather.data.wendu
            val firstday = weather.data.forecast.first()
            when(firstday.type){
                "晴" -> imageView.setImageResource(R.drawable.sun)
                "阴" -> imageView.setImageResource(R.drawable.cloud)
                "多云" -> imageView.setImageResource(R.drawable.mcloud)
                "阵雨" -> imageView.setImageResource(R.drawable.rain)
                else -> imageView.setImageResource(R.drawable.thunder)
            }
            val adapter = ArrayAdapter<Forecast>(this,android.R.layout.simple_list_item_1,weather.data.forecast)
            listview.adapter = adapter
            Log.d("ActivitySecond","${weather.cityInfo.city} ${weather.cityInfo.parent}")
        },{
            Log.d("ActivitySecond","$it")
        })
        queue.add(stringRequest)
    }

}