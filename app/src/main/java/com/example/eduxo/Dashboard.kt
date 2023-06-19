package com.example.eduxo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val button = findViewById<Button>(R.id.btnlearn) // declare and initialize the button


        button.setOnClickListener {
            val intent = Intent(this@Dashboard, MainActivity::class.java)
            startActivity(intent)
        }
    }
}