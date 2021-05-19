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

        fun fetchPlayers(): ArrayList<Player.PlayerItem>{

            Log.d("Gabriel", "network call being made")

            val tempList = arrayListOf<Player.PlayerItem>()

            CoroutineScope(Dispatchers.IO).launch {
                val response = LaRojaService
                        .getLaRojaDataService()
                        .getLaRojaPlayers()


                if(response.isSuccessful) {
                    val temp = response.body()

                    var index = 0
                    temp?.forEach {
                        tempList.add(Player.PlayerItem(it.active, it.bio, it.gamesPlayed, it.goalsScored, it.name,
                                it.number, it.playerPictureUrl, it.position, it.profilePictureUrl, it.seasonsActive))

                        index += 1
                    }

                    withContext(Dispatchers.Main) {
                        tempList.sortBy { it.name }
                        HomeActivity.mutableLiveList.value = tempList
                    }
                }
            }
            return tempList
        }
    }
}