package com.lrdb.la_roja_de_bruselas_kt.Model


import com.google.gson.annotations.SerializedName

class Player : ArrayList<Player.PlayerItem>(){
    data class PlayerItem(
        @SerializedName("active")
        val active: Boolean,
        @SerializedName("bio")
        val bio: String,
        @SerializedName("games_played")
        val gamesPlayed: Int,
        @SerializedName("goals_scored")
        val goalsScored: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("number")
        val number: Int,
        @SerializedName("player_picture_url")
        val playerPictureUrl: String,
        @SerializedName("position")
        val position: String,
        @SerializedName("profile_picture_url")
        val profilePictureUrl: String,
        @SerializedName("seasons_active")
        val seasonsActive: Int
    )
}