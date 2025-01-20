//package com.example.appevents.ui.home
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.appevents.databinding.FragmentHomeBinding
//import com.example.appevents.ui.details.EventDetailActivity
//import com.example.appevents.ui.models.EventResponse
//import com.example.appevents.ui.models.RetrofitClient
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class HomeFragment : Fragment() {
//
//    private lateinit var binding: FragmentHomeBinding
//    private lateinit var upcomingAdapter: EventAdapter
//    private lateinit var finishedAdapter: EventAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentHomeBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        setupRecyclerViews()
//
//        fetchUpcomingEvents()
//        fetchFinishedEvents()
//    }
//
//    private fun setupRecyclerViews() {
//        upcomingAdapter = EventAdapter(emptyList()) { event ->
//            val intent = Intent(requireContext(), EventDetailActivity::class.java).apply {
//                putExtra("id", event.id)
//                putExtra("imageLogo", event.imageLogo)
//                putExtra("name", event.name)
//                putExtra("ownerName", event.ownerName)
//                putExtra("beginTime", event.beginTime)
//                putExtra("quota", event.quota)
//                putExtra("registrants", event.registrants)
//                putExtra("description", event.description)
//                putExtra("link", event.link)
//            }
//            startActivity(intent)
//        }
//        binding.upcomingEventsRecyclerView.apply {
//            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//            adapter = upcomingAdapter
//        }
//
//        finishedAdapter = EventAdapter(emptyList()) { event ->
//            val intent = Intent(requireContext(), EventDetailActivity::class.java).apply {
//                putExtra("id", event.id)
//                putExtra("imageLogo", event.imageLogo)
//                putExtra("name", event.name)
//                putExtra("ownerName", event.ownerName)
//                putExtra("beginTime", event.beginTime)
//                putExtra("quota", event.quota)
//                putExtra("registrants", event.registrants)
//                putExtra("description", event.description)
//                putExtra("link", event.link)
//            }
//            startActivity(intent)
//        }
//        binding.finishedEventsRecyclerView.apply {
//            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//            adapter = finishedAdapter
//        }
//    }
//
//    private fun fetchUpcomingEvents() {
//        binding.homeProgressBar.visibility = View.VISIBLE
//        RetrofitClient.instance.getEvents(active = 1, limit = 5).enqueue(object : Callback<EventResponse> {
//            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
//                binding.homeProgressBar.visibility = View.GONE
//                if (response.isSuccessful) {
//                    response.body()?.listEvents?.let { events ->
//                        upcomingAdapter = EventAdapter(events) { event ->
//                            val intent = Intent(requireContext(), EventDetailActivity::class.java).apply {
//                                putExtra("id", event.id)
//                                putExtra("imageLogo", event.imageLogo)
//                                putExtra("name", event.name)
//                                putExtra("ownerName", event.ownerName)
//                                putExtra("beginTime", event.beginTime)
//                                putExtra("quota", event.quota)
//                                putExtra("registrants", event.registrants)
//                                putExtra("description", event.description)
//                                putExtra("link", event.link)
//                            }
//                            startActivity(intent)
//                        }
//                        binding.upcomingEventsRecyclerView.adapter = upcomingAdapter
//                    }
//                }else {
//                    showError("Failed to load finished events.")
//                }
//            }
//
//            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
//                binding.homeProgressBar.visibility = View.GONE
//                showError("Error: ${t.message}")
//            }
//        })
//    }
//
//    private fun fetchFinishedEvents() {
//        RetrofitClient.instance.getEvents(active = 0, limit = 5).enqueue(object : Callback<EventResponse> {
//            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
//                binding.homeProgressBar.visibility = View.GONE
//                if (response.isSuccessful) {
//                    response.body()?.listEvents?.let { events ->
//                        finishedAdapter = EventAdapter(events) { event ->
//                            val intent = Intent(requireContext(), EventDetailActivity::class.java).apply {
//                                putExtra("id", event.id)
//                                putExtra("imageLogo", event.imageLogo)
//                                putExtra("name", event.name)
//                                putExtra("ownerName", event.ownerName)
//                                putExtra("beginTime", event.beginTime)
//                                putExtra("quota", event.quota)
//                                putExtra("registrants", event.registrants)
//                                putExtra("description", event.description)
//                                putExtra("link", event.link)
//                            }
//                            startActivity(intent)
//                        }
//                        binding.finishedEventsRecyclerView.adapter = finishedAdapter
//                    }
//                }else {
//                    showError("Failed to load finished events.")
//                }
//            }
//
//            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
//                showError("Error: ${t.message}")
//                binding.homeProgressBar.visibility = View.GONE
//            }
//        })
//    }
//    private fun showError(message: String) {
//        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
//        binding.errorTextView.text = message
//        binding.errorTextView.visibility = View.VISIBLE
//        binding.eventDicodingTitle.visibility = View.GONE
//        binding.finishedEventsTitle.visibility = View.GONE
//        binding.upcomingEventsTitle.visibility = View.GONE
//    }
//}
//
