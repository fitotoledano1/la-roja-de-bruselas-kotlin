package com.lrdb.la_roja_de_bruselas_kt.View

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lrdb.la_roja_de_bruselas_kt.Model.Players
import com.lrdb.la_roja_de_bruselas_kt.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.home_player_list_recycler_cell.view.*

class PlayerAdapter (var playersList : MutableList<Players.PlayerItem>): RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    class PlayerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.home_player_list_recycler_cell, parent, false))
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {

        holder.itemView.name_textView.text = playersList[position].name
        val profilePictureView = holder.itemView.home_profile_image
        Picasso.with(holder.itemView.context)
                .load(playersList[position].profilePictureUrl)
                .fit()
                .into(profilePictureView)
    }

    override fun getItemCount(): Int {
        return playersList.size
    }
}