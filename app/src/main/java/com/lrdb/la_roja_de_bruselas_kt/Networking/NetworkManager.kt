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
        // val mutableLiveList= MutableLiveData<MutableList<Player.PlayerItem>>()

        fun fetch(): ArrayList<Player.PlayerItem>{

            val tempList = arrayListOf<Player.PlayerItem>()

            CoroutineScope(Dispatchers.IO).launch {
                val response = LaRojaService
                        .getLaRojaDataService()
                        .getLaRojaPlayers()

                withContext(Dispatchers.Main) {
                    if(response.isSuccessful) {
                        val temp = response.body()

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

            return tempList
        }
    }
}