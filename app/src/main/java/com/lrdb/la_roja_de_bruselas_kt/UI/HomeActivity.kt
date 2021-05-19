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
import com.lrdb.la_roja_de_bruselas_kt.Model.Player
import com.lrdb.la_roja_de_bruselas_kt.Networking.NetworkManager
import com.lrdb.la_roja_de_bruselas_kt.R
import com.lrdb.la_roja_de_bruselas_kt.View.PlayerAdapter
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    companion object {
        val mutableLiveList = MutableLiveData<MutableList<Player.PlayerItem>>()
        val currentList = NetworkManager.fetchPlayers()
        var filteredPlayers = currentList
    }


    private var sorted = true
    private var filtered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        configureMutableLiveObserver()
        createFilteredPlayersArray()
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
                when (filtered) {
                    false -> currentList.filter { it.active }
                    true -> currentList.filterNot { it.active }
                }
                Log.d("gabriel", "hello $currentList")
                mutableLiveList.value = currentList
                filtered = !filtered
            }
            R.id.sort_menu_option -> {
                when (sorted) {
                    false -> currentList.sortBy { it.name }
                    else -> currentList.sortByDescending { it.name }
                }
                mutableLiveList.value = currentList
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

    fun createFilteredPlayersArray() {
        filteredPlayers = currentList
    }
}