package be.tftic.web2023.demo10_geolocalisation.activities

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import be.tftic.web2023.demo10_geolocalisation.R
import be.tftic.web2023.demo10_geolocalisation.databinding.ActivityMainBinding
import be.tftic.web2023.demo10_geolocalisation.fragments.DemoFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkLocationPermission()

        showInitialFrag()
    }

    private fun showInitialFrag() {

        val fragDemo = DemoFragment.newInstance()

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fcv_main_content, fragDemo)
        }
    }

    val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "La permission est nécessaire...", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun checkLocationPermission(): Unit {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
}