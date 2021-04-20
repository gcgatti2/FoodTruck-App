package com.example.redfinapp.presentation

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout.HORIZONTAL
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.redfinapp.R
import com.example.redfinapp.data.FoodTruck
import com.example.redfinapp.databinding.FragmentFoodTruckListBinding
import com.example.redfinapp.util.*

class FoodTruckListFragment : Fragment(R.layout.fragment_food_truck_list) {

    private val foodTruckAdapter = FoodTruckAdapter()
    private val binding by viewBinding(FragmentFoodTruckListBinding::bind)
    private val viewModel: FoodTruckViewModel by activityViewModels()
    private lateinit var foodTrucks: List<FoodTruck>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerview()
        observeViewModel()

        binding.toolbar.setOnMenuItemClickListener {
            findNavController().navigate(FoodTruckListFragmentDirections.actionFoodTruckListFragmentToFoodTruckMapsFragment(foodTrucks.toTypedArray()))
            true
        }
    }

    private fun observeViewModel() {
        viewModel.getStateData().observe(viewLifecycleOwner, EventObserver{ state->
            when(state) {
                FoodTruckViewModel.DataState.LOADING -> {
                    binding.progressBar.makeVisible()
                }
                FoodTruckViewModel.DataState.LOADED -> {
                    binding.progressBar.makeGone()
                }
                FoodTruckViewModel.DataState.ERROR -> {
                    binding.progressBar.makeGone()
                    requireActivity().showSnackbar(requireView(), "Error occurred.", true)
                }
            }
        })
        viewModel.getFoodTruckData().observe(viewLifecycleOwner, {
            foodTrucks = it
            foodTruckAdapter.submitList(it)
        })
    }

    private fun initRecyclerview() {
        binding.rvFoodTrucks.apply {
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = foodTruckAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}