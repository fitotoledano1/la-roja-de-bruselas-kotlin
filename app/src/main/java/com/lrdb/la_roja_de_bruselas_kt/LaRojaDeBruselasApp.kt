package com.lrdb.la_roja_de_bruselas_kt

import android.app.Application
import com.lrdb.la_roja_de_bruselas_kt.Networking.PlayerRepository

class LaRojaDeBruselasApp: Application() {

    val playerRepository by lazy {
        PlayerRepository()
    }

}