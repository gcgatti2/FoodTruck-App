package com.example.redfinapp.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.redfinapp.R
import com.example.redfinapp.databinding.FragmentFoodTruckMapsBinding
import com.example.redfinapp.util.viewBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class FoodTruckMapsFragment : Fragment(R.layout.fragment_food_truck_maps), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var googleMap: GoogleMap

    private val navArgs by lazy { FoodTruckMapsFragmentArgs.fromBundle(arguments ?: Bundle.EMPTY) }
    private val binding by viewBinding(FragmentFoodTruckMapsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)

        binding.toolbar.setOnMenuItemClickListener {
            findNavController().popBackStack()
            true
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        var avgLatitude  = 0.0
        var avgLongitude = 0.0
        navArgs.foodtrucks.forEachIndexed { index, foodTruck ->
            val location = LatLng(foodTruck.latitude.toDouble(), foodTruck.longitude.toDouble())
            avgLatitude += foodTruck.latitude.toDouble()
            avgLongitude += foodTruck.longitude.toDouble()
            this.googleMap.addMarker(MarkerOptions()
                    .position(location)).tag = index
        }
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(avgLatitude/navArgs.foodtrucks.size, avgLongitude/navArgs.foodtrucks.size), 12.0f))
        this.googleMap.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        marker?.let {
            findNavController().navigate(FoodTruckMapsFragmentDirections.actionFoodTruckMapsFragmentToFoodTruckInfoDialogFragment(navArgs.foodtrucks[it.tag as Int]))
        }
        return true
    }

    override fun onStart() {
        binding.mapView.onStart()
        super.onStart()
    }

    override fun onResume() {
        binding.mapView.onResume()
        super.onResume()
    }

    override fun onStop() {
        binding.mapView.onStop()
        super.onStop()
    }

    override fun onPause() {
        binding.mapView.onPause()
        super.onPause()
    }

    override fun onLowMemory() {
        binding.mapView.onLowMemory()
        super.onLowMemory()
    }

}