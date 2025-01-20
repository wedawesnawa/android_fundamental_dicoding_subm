//package com.example.appevents.ui.home
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.example.appevents.databinding.ItemEventBinding
//import com.example.appevents.ui.models.Event
//
//class EventAdapter(private val events: List<Event>, private val onItemClick: (Event) -> Unit) :
//    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {
//
//    class EventViewHolder(val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
//        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return EventViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
//        val event = events[position]
//        holder.binding.eventName.text = event.name
//        Glide.with(holder.itemView.context).load(event.imageLogo).into(holder.binding.eventImage)
//
//        holder.itemView.setOnClickListener { onItemClick(event) }
//    }
//
//    override fun getItemCount(): Int = events.size
//}
