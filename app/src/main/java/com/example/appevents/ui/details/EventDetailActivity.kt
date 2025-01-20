package com.example.appevents.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.example.appevents.R
import com.example.appevents.databinding.ActivityEventDetailBinding
import com.example.appevents.ui.database.EventDatabase
import com.example.appevents.ui.models.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventDetailBinding
    private lateinit var eventDatabase: EventDatabase
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val event: Event? = intent.getParcelableExtra("event")

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = event?.name ?: getString(R.string.app_name)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        eventDatabase = EventDatabase.getDatabase(this)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


        event?.let {
            loadEventDetails(it)
            checkIfFavorite(it)
        }
    }

    private fun loadEventDetails(event: Event) {
        Glide.with(this).load(event.imageLogo).into(binding.eventImage)
        binding.eventName.text = event.name
        binding.eventOwner.text = event.ownerName
        binding.eventTime.text = event.beginTime
        val balance = event.quota - event.registrants
        binding.eventQuota.text = getString(R.string.sisa_kouta, balance)
        binding.eventDescription.text = HtmlCompat.fromHtml(event.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.openLinkButton.setOnClickListener {
            val url = event.link
            if (url.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(url)
                }
                startActivity(intent)
            }
        }
        binding.favoriteButton.setOnClickListener {
            toggleFavorite(event)
        }
    }

    private fun checkIfFavorite(event: Event) {
        GlobalScope.launch(Dispatchers.IO) {
            val eventInDb = eventDatabase.eventDao().getEventById(event.id)
            isFavorite = eventInDb != null

            withContext(Dispatchers.Main) {
                updateFavoriteButton(event)
            }
        }
    }


    private fun toggleFavorite(event: Event) {
        val isFavorite = !event.isFavorite
        event.isFavorite = isFavorite

        GlobalScope.launch(Dispatchers.IO) {
            if (isFavorite) {
                eventDatabase.eventDao().addFavorite(event)
            } else {
                eventDatabase.eventDao().removeFavorite(event)
            }

            withContext(Dispatchers.Main) {
                updateFavoriteButton(event)
            }
        }
    }

    private fun updateFavoriteButton(event: Event) {
        val iconResId = if (event.isFavorite) {
            R.drawable.baseline_favorite_24
        } else {
            R.drawable.baseline_favorite_border_24
        }
        binding.favoriteButton.setImageResource(iconResId)
    }
}
