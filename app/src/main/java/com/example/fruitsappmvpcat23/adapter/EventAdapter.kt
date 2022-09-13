package com.example.fruitsappmvpcat23.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.fruitsappmvpcat23.databinding.FragmentViewCardBinding
import com.example.fruitsappmvpcat23.model.domain.FruitDomain

private const val TAG = "EventAdapter"

class EventAdapter(
    private val eventDataSet: MutableList<FruitDomain> = mutableListOf()
): RecyclerView.Adapter<EventViewHolder>() {

    fun updateEvent(newEvent: FruitDomain){
        eventDataSet.add(newEvent).also {
            if(it){
                eventDataSet.sortBy { data -> data.fruitName}
            }
        }
        notifyItemInserted(eventDataSet.indexOf(newEvent))
    }

    fun updateAllEvents(data: List<FruitDomain>){
        eventDataSet.clear()
        eventDataSet.addAll(data)
        notifyDataSetChanged()
    }



    fun getEvent(): MutableList<FruitDomain> {
        Log.d("function", "getEvent: $eventDataSet + holder")
        return eventDataSet

    }

    fun indexEvent(event: FruitDomain): Int {
        return eventDataSet.indexOf(event)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder =
        EventViewHolder(
            FragmentViewCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(eventDataSet[position])
    }

    override fun getItemCount(): Int = eventDataSet.size
}

class EventViewHolder(
    private val binding: FragmentViewCardBinding
): RecyclerView.ViewHolder(binding.root){
    fun bind(event: FruitDomain){
        binding.name.text = event.fruitName
        binding.family.text = event.family
        binding.genus.text = event.genus

        Log.d(TAG, "bind: $event")
    }
}
