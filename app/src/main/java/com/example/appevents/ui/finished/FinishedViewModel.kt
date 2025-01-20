package com.example.appevents.ui.finished

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appevents.ui.models.Event
import com.example.appevents.ui.models.EventResponse
import com.example.appevents.ui.models.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FinishedViewModel : ViewModel() {

    private val _eventList = MutableLiveData<List<Event>>()
    val eventList: LiveData<List<Event>> get() = _eventList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    init {
        fetchFinishedEvents()
    }

    private fun fetchFinishedEvents() {
        RetrofitClient.instance.getFinishedEvents().enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    _eventList.value = response.body()?.listEvents ?: emptyList()
//                    Log.d("API Success", response.body()?.listEvents.toString())
                } else {
                    _eventList.value = emptyList()
                    _errorMessage.value = "Error: Response code ${response.code()}"
//                    Log.e("API Error", "Response code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _eventList.value = emptyList()
                _errorMessage.value = "Error: ${t.message ?: "Unknown error"}"
//                Log.e("API Error", t.message ?: "Unknown error")
            }
        })
    }
}
