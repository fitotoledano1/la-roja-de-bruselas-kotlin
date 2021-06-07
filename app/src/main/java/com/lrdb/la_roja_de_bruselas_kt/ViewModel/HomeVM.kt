package com.lrdb.la_roja_de_bruselas_kt.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lrdb.la_roja_de_bruselas_kt.Model.Players
import com.lrdb.la_roja_de_bruselas_kt.Networking.PlayerRepository
import kotlinx.coroutines.launch
import java.util.*

class HomeVM(playerRepository: PlayerRepository): ViewModel() {
    // TODO observable list
    val playerList = Observab
        // mutableListOf<Players.PlayerItem>()

    init {
        viewModelScope.launch {
            playerList.addAll(playerRepository.fetchPlayers())
        }
    }
}