package com.lrdb.la_roja_de_bruselas_kt.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lrdb.la_roja_de_bruselas_kt.Model.Player
import com.lrdb.la_roja_de_bruselas_kt.Networking.LaRojaService
import com.lrdb.la_roja_de_bruselas_kt.Networking.NetworkManager
import com.lrdb.la_roja_de_bruselas_kt.R
import com.lrdb.la_roja_de_bruselas_kt.View.PlayerAdapter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity : AppCompatActivity() {

    companion object {
        val mutableLiveList= MutableLiveData<MutableList<Player.PlayerItem>>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val currentList = NetworkManager.fetch()
        var sorted = true

        mutableLiveList.observe(
                this, Observer{
            playerList_recycler_view.layoutManager = LinearLayoutManager(this)
            playerList_recycler_view.adapter = PlayerAdapter(currentList)
        })

        floatingActionButton2.setOnClickListener {
            currentList.sortBy {
                it.name
            }
            mutableLiveList.value = currentList
            sorted = true
        }

        floatingActionButton3.setOnClickListener {
            when (sorted) {
                false -> {
                    currentList.sortBy { it.name }
                    mutableLiveList.value = currentList
                    sorted = true
                }
                else -> {
                    currentList.sortByDescending { it.name }
                    mutableLiveList.value = currentList
                    sorted = false
                }
            }
        }
    }
}