package com.example.redfinapp.presentation

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.redfinapp.R
import com.example.redfinapp.databinding.FragmentDialogFoodTruckInfoBinding
import com.example.redfinapp.util.viewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FoodTruckInfoDialogFragment: BottomSheetDialogFragment() {

    private val navArgs by lazy { FoodTruckInfoDialogFragmentArgs.fromBundle(arguments ?: Bundle.EMPTY) }
    private val binding by viewBinding(FragmentDialogFoodTruckInfoBinding::bind)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_food_truck_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lytFoodTruck.apply {
            tvFoodTruckName.text = navArgs.foodTruck.name
            tvFoodDetails.text = navArgs.foodTruck.description
            tvFoodTruckHours.text = requireContext().getString(R.string.operating_hours).format(navArgs.foodTruck.startTime, navArgs.foodTruck.endTime)
            tvFoodTruckLocation.text = navArgs.foodTruck.location
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)?.let {
                BottomSheetBehavior.from(it).also { behavior ->
                    // this will expand the dialog fully right away
                    behavior.state = BottomSheetBehavior.STATE_EXPANDED
                    behavior.skipCollapsed = true
                    behavior.isHideable = true
                }
            }
        }
        return dialog
    }
}