package com.example.appevents.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appevents.databinding.ItemEventBinding
import com.example.appevents.ui.models.Event

class FavoriteEventsAdapter(
    private val onClick: (Event) -> Unit
) : RecyclerView.Adapter<FavoriteEventsAdapter.FavoriteViewHolder>() {

    private var eventList: List<Event> = emptyList()

    fun submitList(events: List<Event>) {
        eventList = events
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemEventBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val event = eventList[position]
        holder.bind(event)
        holder.itemView.setOnClickListener { onClick(event) }
    }

    override fun getItemCount(): Int = eventList.size

    inner class FavoriteViewHolder(private val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event) {
            binding.eventName.text = event.name
            Glide.with(binding.eventImage.context).load(event.imageLogo).into(binding.eventImage)
        }
    }
}
