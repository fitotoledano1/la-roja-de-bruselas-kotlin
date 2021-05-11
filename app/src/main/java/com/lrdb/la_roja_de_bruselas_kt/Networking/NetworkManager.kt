package com.lrdb.la_roja_de_bruselas_kt.Networking

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.lrdb.la_roja_de_bruselas_kt.Model.Player
import com.lrdb.la_roja_de_bruselas_kt.UI.HomeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NetworkManager {

    companion object {
        // var currentPlayerList = fetch()

        fun fetch(): ArrayList<Player.PlayerItem>{

            var tempList = arrayListOf<Player.PlayerItem>()
            // Log.d("gabo", "Coroutine 01 ------------ ")
            CoroutineScope(Dispatchers.IO).launch {
                val response = LaRojaService
                        .getLaRojaDataService()
                        .getLaRojaPlayers()

                // Log.d("gabo", "Coroutine 02 ------------ ")

                withContext(Dispatchers.Main) {
                    if(response.isSuccessful) {
                        val temp = response.body()

                        // Log.d("gabo", "Coroutine 03 ------------ ${tempList[1]?.name}")

                        var index = 0
                        temp?.forEach {

                            tempList.add(Player.PlayerItem(it.active, it.bio, it.gamesPlayed, it.goalsScored, it.name,
                                    it.number, it.playerPictureUrl, it.position, it.profilePictureUrl, it.seasonsActive))

                            index += 1
                        }
                        HomeActivity.mutableLiveList.value = tempList
                    }
                }
            }
            // Log.d("gabo", "Coroutine 04 ------------ ${tempList[1]?.name} ")
            return tempList
        }

    }


}