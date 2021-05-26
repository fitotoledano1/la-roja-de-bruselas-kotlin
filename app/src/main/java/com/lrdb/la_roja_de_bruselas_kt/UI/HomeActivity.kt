package com.lrdb.la_roja_de_bruselas_kt.UI

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lrdb.la_roja_de_bruselas_kt.Model.Players
import com.lrdb.la_roja_de_bruselas_kt.R
import com.lrdb.la_roja_de_bruselas_kt.View.PlayerAdapter
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    companion object {
        var mutableLiveList = MutableLiveData<MutableList<Players.PlayerItem>>()
        var filteredPlayers = currentList

        fun updatePlayerList(newPlayersList: ArrayList<Players.PlayerItem>) {
            mutableLiveList.value = newPlayersList
        }
    }


    private var sorted = true
    private var filtered = false
    private val currentList = lazy {
        app.playerRepository.fetchPlayers()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        configureMutableLiveObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_home_menu, menu)

        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.search_menu_option)
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                searchView.setQuery("", false)
                searchItem.collapseActionView()
                Toast.makeText(this@HomeActivity, "Looking for $query", Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if(newText!!.isNotBlank()) {

                }
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.filter_menu_option -> {
                Log.d("gabriel", "filtered players = $filtered ,  $filteredPlayers")
                when (filtered) {
                    false -> {
                        filteredPlayers.filterNot { it.active }
                        updatePlayerList(filteredPlayers)
                        filteredPlayers = currentList
                    }
                    else -> updatePlayerList(currentList)
                }
                filtered = !filtered
                updatePlayerList(filteredPlayers)
                Log.d("gabriel", "filtered players = $filtered ,  $filteredPlayers")
            }
            R.id.sort_menu_option -> {
                when (sorted) {
                    false -> currentList.sortBy { it.name }
                    else -> currentList.sortByDescending { it.name }
                }
                updatePlayerList(currentList)
                sorted = !sorted
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun configureMutableLiveObserver() {
                mutableLiveList.observe(
            this, Observer{
                playerList_recycler_view.layoutManager = LinearLayoutManager(this)
                playerList_recycler_view.adapter = PlayerAdapter(currentList)
            })
    }
}