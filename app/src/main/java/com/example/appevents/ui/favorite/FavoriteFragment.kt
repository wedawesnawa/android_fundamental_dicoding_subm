package com.example.appevents.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appevents.R
import com.example.appevents.databinding.FragmentFavoriteBinding
import com.example.appevents.ui.database.EventDatabase
import com.example.appevents.ui.details.EventDetailActivity
import com.example.appevents.ui.models.Event

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FavoriteEventsAdapter
    private lateinit var eventDatabase: EventDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        eventDatabase = EventDatabase.getDatabase(requireContext())

        adapter = FavoriteEventsAdapter { event -> onEventClick(event) }
        binding.recyclerViewFavorite.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewFavorite.adapter = adapter

        loadFavoriteEvents()


        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.title_favorite)

        return binding.root
    }

    private fun loadFavoriteEvents() {
//        eventDatabase.eventDao().getAllFavoriteEvents().observe(viewLifecycleOwner) { favoriteEvents ->
//            adapter.submitList(favoriteEvents)
//        }
        try {
            eventDatabase.eventDao().getAllFavoriteEvents().observe(viewLifecycleOwner) { favoriteEvents ->
                if (favoriteEvents.isNullOrEmpty()) {
                    binding.errorTextView.text = getString(R.string.no_favorites)
                    binding.errorTextView.visibility = View.VISIBLE
                    binding.recyclerViewFavorite.visibility = View.GONE
                } else {
                    adapter.submitList(favoriteEvents)
                    binding.errorTextView.visibility = View.GONE
                    binding.recyclerViewFavorite.visibility = View.VISIBLE
                }
                binding.progressBar.visibility = View.GONE
            }
        } catch (e: Exception) {
            binding.errorTextView.text = getString(R.string.error_loading_data)
            binding.errorTextView.visibility = View.VISIBLE
            binding.recyclerViewFavorite.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun onEventClick(event: Event) {
        val intent = Intent(activity, EventDetailActivity::class.java).apply {
            putExtra("event", event)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
