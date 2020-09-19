package com.gao.sunnyweather.ui.place

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gao.sunnyweather.R
import kotlinx.android.synthetic.main.fragemnt_place.*

class PlaceFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProvider(this).get(PlaceViewModel::class.java)
    }

    private lateinit var placeAdapter: PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragemnt_place, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        placeResultRecycleView.layoutManager = layoutManager
        placeAdapter = PlaceAdapter(this, viewModel.placeList)
        placeResultRecycleView.adapter = placeAdapter
        placeSearchEdit.addTextChangedListener {
            if (it.isNotEmpty()) {
                viewModel.searchPlaces(it)
            } else {
                placeResultRecycleView.visibility = View.GONE
                placeBgImage.visibility = View.VISIBLE
                placeAdapter.notifyDataSetChanged()
            }
        }

        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer {
            val places = it.getOrNull()
            if (places != null) {
                placeResultRecycleView.visibility = View.VISIBLE
                placeBgImage.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                placeAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "未能查询到任何地点", Toast.LENGTH_SHORT).show()
                it.exceptionOrNull()?.message
            }
        })
    }

    private fun EditText.addTextChangedListener(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }
}