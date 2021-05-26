package com.lrdb.la_roja_de_bruselas_kt.Networking

import android.util.Log
import com.lrdb.la_roja_de_bruselas_kt.Model.Players
import kotlinx.coroutines.*

class PlayerRepository {

    val apiScope = CoroutineScope(Dispatchers.IO + Job())

    suspend fun  fetchPlayers(): List<Players.PlayerItem> {

        Log.d("Gabriel", "network call being made")

        val tempList = arrayListOf<Players.PlayerItem>()

        return apiScope.async {
            val response = LaRojaService
                    .getLaRojaDataService()
                    .getLaRojaPlayers()

            if(response.isSuccessful) {
                val temp = response.body()

                var index = 0
                temp?.forEach {
                    tempList.add(Players.PlayerItem(it.active, it.bio, it.gamesPlayed, it.goalsScored, it.name,
                            it.number, it.playerPictureUrl, it.position, it.profilePictureUrl, it.seasonsActive))

                    index += 1
                }
                return@async tempList.sortedBy { it.name }
            }
            throw ApiException(message = "Api didn't work: ${response.code()}")
        }.await()
    }
}