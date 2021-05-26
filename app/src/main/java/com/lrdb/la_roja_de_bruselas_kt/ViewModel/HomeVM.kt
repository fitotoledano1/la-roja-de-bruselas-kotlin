package com.lrdb.la_roja_de_bruselas_kt.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lrdb.la_roja_de_bruselas_kt.Model.Players
import com.lrdb.la_roja_de_bruselas_kt.Networking.PlayerRepository

class HomeVM(playerRepository: PlayerRepository): ViewModel() {
    val playerList = MutableLiveData<Players>()
    init {
        viewModelScope.launch {
            
        }
    }
}