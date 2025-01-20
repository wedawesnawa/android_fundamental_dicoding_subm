package com.example.appevents.ui.upcoming

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appevents.R
import com.example.appevents.databinding.FragmentUpcomingBinding
import com.example.appevents.ui.details.EventDetailActivity
import com.example.appevents.ui.models.Event

class UpcomingFragment : Fragment() {

    private lateinit var viewModel: UpcomingViewModel
    private lateinit var adapter: UpcomingEventsAdapter
    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[UpcomingViewModel::class.java]

        val recyclerView = binding.recyclerViewUpcoming
        adapter = UpcomingEventsAdapter { event -> onEventClick(event) }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        binding.progressBar.visibility = View.VISIBLE

        viewModel.eventList.observe(viewLifecycleOwner, Observer { events ->
            adapter.submitList(events)

            binding.progressBar.visibility = View.GONE
        })
        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                binding.errorTextView.text = error
                binding.errorTextView.visibility = View.VISIBLE
                binding.recyclerViewUpcoming.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
            }
        }

        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.title_dashboard)

        return binding.root
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