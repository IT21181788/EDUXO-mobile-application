package com.example.eduxo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class user_Activity : AppCompatActivity() {

    private lateinit var tvsubId: TextView
    private lateinit var tvsubjectname: TextView
    private lateinit var tvnote: TextView
    private lateinit var tvlink: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        initView()
        setValuesToViews()





    }

    private fun initView() {
        tvsubId = findViewById(R.id.tvsubId)
        tvsubjectname = findViewById(R.id.tvsubjectname)
        tvnote = findViewById(R.id.tvnote)
        tvlink = findViewById(R.id.tvlink)

    }

    private fun setValuesToViews() {

        tvsubjectname.text = intent.getStringExtra("subjectname")
        tvnote.text = intent.getStringExtra("note")
        tvlink.text = intent.getStringExtra("link")

    }





}