package com.si.restro.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.si.restro.R
import com.si.restro.data.Restaurants
import com.si.restro.databinding.ItemRestaurantBinding

class RestaurantsListAdapter(private val restaurantsList: ArrayList<Restaurants>) :
    RecyclerView.Adapter<RestaurantsListAdapter.RestaurantViewHolder>(),
    RestaurantItemClickListener {

    fun updateRestaurantList(newRestaurantsList: List<Restaurants>) {
        restaurantsList.clear()
        restaurantsList.addAll(newRestaurantsList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =
            DataBindingUtil.inflate<ItemRestaurantBinding>(
                inflater,
                R.layout.item_restaurant,
                parent,
                false
            )

        return RestaurantViewHolder(view)
    }

    override fun getItemCount() = restaurantsList.size

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.view.restaurant = restaurantsList[position]
        holder.view.listener = this
    }

    override fun onItemClicked(v: View) {
        val uuid = v.findViewById<TextView>(R.id.restaurant_id).text.toString().toInt()
        val action = HomeFragmentDirections.actionDetailsFragment()
        action.restaurantId = uuid
        Navigation.findNavController(v).navigate(action)
    }

    class RestaurantViewHolder(var view: ItemRestaurantBinding) : RecyclerView.ViewHolder(view.root)
}