package com.lrdb.la_roja_de_bruselas_kt.Networking

import com.lrdb.la_roja_de_bruselas_kt.Model.Players
import retrofit2.Response
import retrofit2.http.GET

interface LaRojaApi {
    @GET("/players")
    suspend fun getLaRojaPlayers() : Response<Players>
}