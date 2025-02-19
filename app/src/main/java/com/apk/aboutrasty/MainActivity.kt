package com.apk.aboutrasty

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.apk.aboutrasty.databinding.ActivityMainBinding
import android.content.Intent

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setup()
    }

    private fun setup() {
        binding.btnKeluar.setOnClickListener {
            val builder = AlertDialog.Builder (this)
            builder.setTitle("About Me")
            builder.setMessage("Saya Rasty Salsabila.")
            builder.setPositiveButton("OK"){dialog, _ ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
            Toast.makeText(applicationContext, "About me", Toast.LENGTH_LONG).show()
        }

        binding.btnKeluar.setOnClickListener {
            finish()
        }

        binding.btnKalkulator.setOnClickListener {
            val intent = Intent(this, CalculatorActivity::class.java)
            startActivity(intent)
        }
    }
}