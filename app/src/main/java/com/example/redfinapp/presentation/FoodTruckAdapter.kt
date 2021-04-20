package com.example.redfinapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.redfinapp.R
import com.example.redfinapp.data.FoodTruck

class FoodTruckAdapter : RecyclerView.Adapter<FoodTruckAdapter.ViewHolder>() {

    private var foodTrucks: MutableList<FoodTruck> = mutableListOf()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tvFoodTruckName)
        val tvLocation = view.findViewById<TextView>(R.id.tvFoodTruckLocation)
        val tvFoodDetails = view.findViewById<TextView>(R.id.tvFoodDetails)
        val tvHours = view.findViewById<TextView>(R.id.tvFoodTruckHours)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_food_truck, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val foodTruck = foodTrucks[position]
        holder.tvName.text = foodTruck.name
        holder.tvLocation.text = foodTruck.location
        holder.tvFoodDetails.text = foodTruck.description
        holder.tvHours.text = holder.itemView.context.getString(R.string.operating_hours).format(foodTruck.startTime, foodTruck.endTime)
    }

    fun submitList(data: List<FoodTruck>) {
        foodTrucks = data.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = foodTrucks.size
}