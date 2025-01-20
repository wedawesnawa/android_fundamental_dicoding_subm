package com.example.appevents.ui.finished

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
import com.example.appevents.databinding.FragmentFinishedBinding
import com.example.appevents.ui.details.EventDetailActivity
import com.example.appevents.ui.models.Event

class FinishedFragment : Fragment() {

    private lateinit var viewModel: FinishedViewModel
    private lateinit var adapter: FinishedEventsAdapter
    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[FinishedViewModel::class.java]

        val recyclerView = binding.recyclerViewFinished
        adapter = FinishedEventsAdapter { event -> onEventClick(event) }
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
                binding.recyclerViewFinished.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
            }
        }

        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.title_notifications)

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
