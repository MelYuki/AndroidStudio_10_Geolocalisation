package be.tftic.web2023.demo10_geolocalisation.fragments

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.tftic.web2023.demo10_geolocalisation.R
import be.tftic.web2023.demo10_geolocalisation.databinding.FragmentDemoBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

/**
 * A simple [Fragment] subclass.
 * Use the [DemoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DemoFragment private constructor( ): Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = DemoFragment()
    }

    private lateinit var binding: FragmentDemoBinding
    private lateinit var fusedLocationClient : FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentDemoBinding.inflate(inflater, container, false)

        // Listener
        binding.btnFragDemoGpsLast.setOnClickListener {
            getGpsLastInfo()
        }

        binding.btnFragDemoGpsCurrent.setOnClickListener {
            getGpsInfo()
        }

        // Return view
        return binding.root
    }

    @SuppressLint("MissingPermission")
    private fun getGpsLastInfo() {

        fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
            if(location == null) {
                binding.tvFragDemoResult.text = getString(R.string.no_coord)
            }
            else {
                binding.tvFragDemoResult.text =
                    getString(R.string.coord, location.longitude.toString(), location.latitude.toString())
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getGpsInfo() {

        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location : Location ->
                binding.tvFragDemoResult.text =
                    getString(R.string.coord, location.longitude.toString(), location.latitude.toString())
            }
    }
}