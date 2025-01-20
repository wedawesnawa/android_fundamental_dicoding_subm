package com.example.appevents.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appevents.R
import com.example.appevents.ui.models.Event
import com.example.appevents.ui.models.EventResponse
import com.example.appevents.ui.models.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorTextView: TextView
    private lateinit var searchAdapter: SearchAdapter
    private var eventList: List<Event> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)

        searchView = rootView.findViewById(R.id.searchView)
        recyclerView = rootView.findViewById(R.id.recyclerView)
        progressBar = rootView.findViewById(R.id.progressBar)
        errorTextView = rootView.findViewById(R.id.errorTextView)

        recyclerView.layoutManager = LinearLayoutManager(context)

        searchAdapter = SearchAdapter(eventList)
        recyclerView.adapter = searchAdapter

        setupSearch()

        return rootView
    }
    private fun setupSearch() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchEvents(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun searchEvents(query: String) {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        errorTextView.visibility = View.GONE

        RetrofitClient.instance.searchEvents(query).enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful && response.body() != null) {
                    val eventResponse = response.body()!!
                    if (eventResponse.listEvents.isNotEmpty()) {
                        eventList = eventResponse.listEvents
                        searchAdapter.updateEvents(eventList)
                        recyclerView.visibility = View.VISIBLE
                    } else {
                        showError("No events found.")
                    }
                } else {
                    showError("Failed to retrieve events.")
                }
            }


            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                showError("No internet connection. Please check your connection and try again.")
            }
        })
    }
    private fun showError(message: String) {
        errorTextView.text = message
        errorTextView.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }
}