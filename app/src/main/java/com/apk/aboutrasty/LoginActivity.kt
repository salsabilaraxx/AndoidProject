package com.apk.aboutrasty

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.apk.aboutrasty.databinding.ActivityLoginBinding
import com.google.android.material.color.DynamicColors

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        databaseHelper = DatabaseHelper(this)

        if (!databaseHelper.isUserAvailable()) {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        binding.btnSubmit.setOnClickListener{
            val username = binding.txtUsername.text.toString()
            val password = binding.txtPassword.text.toString()
            loginDatabase(username, password)
        }

        binding.btnRegister.setOnClickListener{
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun loginDatabase(username: String, password: String){
        val userExist = databaseHelper.readUser(username, password)
        if (userExist){
            Toast.makeText(this, "Login sukses!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        else{
            Toast.makeText(this, "Login gagal. Silakan coba lagi!", Toast.LENGTH_SHORT).show()
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Login gagal.")
            builder.setMessage("Username atau password yang Anda masukkan salah. Silakan coba lagi.")
            builder.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
        }
    }
}