package cn.edu.finaltest

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.edu.finaltest.ViewModel.CityViewModel
import kotlinx.android.synthetic.main.fragment_weathermain.*


class WeatherFragment4 : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weathermain, container, false)
    }
    lateinit var viewModel: CityViewModel
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(activity!!.application)).get(
            CityViewModel::class.java
        )
        viewModel.cities.observe(activity!!, Observer {
            val cities = it
            val adapter: ArrayAdapter<CityItem> = ArrayAdapter<CityItem>(
                activity!!,
                android.R.layout.simple_list_item_1,
                cities
            )
            listview.adapter = adapter
            listview.setOnItemClickListener { _, _, position, _ ->
                val cityCode = cities[position].city_code
                val intent = Intent(activity, ActivitySecond::class.java)
                intent.putExtra("city_code", cityCode)
                startActivity(intent)
            }
        })
    }
}