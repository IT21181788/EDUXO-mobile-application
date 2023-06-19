package com.example.eduxo

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
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
    private lateinit var ratingBar: RatingBar

    private val database = FirebaseDatabase.getInstance().reference.child("Subjects")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        initView()
        setValuesToViews()

        // Retrieve the rating bar and set an OnRatingBarChangeListener
        ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            val subId = intent.getStringExtra("subId")

            if (subId != null) {
                database.child(subId).child("rating").setValue(rating.toInt().toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Rating updated", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Failed to update rating", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            }

        }
    }

    private fun initView() {
        tvsubId = findViewById(R.id.tvsubId)
        tvsubjectname = findViewById(R.id.tvsubjectname)
        tvnote = findViewById(R.id.tvnote)
        tvlink = findViewById(R.id.tvlink)
    }

    private fun setValuesToViews() {
        val subId = intent.getStringExtra("subId")
        tvsubjectname.text = intent.getStringExtra("subjectname")
        tvnote.text = intent.getStringExtra("note")

        // Get the link URL and text
        val linkUrl = intent.getStringExtra("link")
        val linkText = "Click here to open link"

        // Create a SpannableString to represent the link
        val spannableString = SpannableString(linkText)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Handle the link click
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkUrl))
                startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                // Customize the link appearance
                ds.color = Color.BLUE
                ds.isUnderlineText = true
            }
        }
        spannableString.setSpan(clickableSpan, 0, linkText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Set the text of tvlink to the SpannableString
        tvlink.text = spannableString
        tvlink.movementMethod = LinkMovementMethod.getInstance()
        // Get the rating from the database and set the rating bar
        if (subId != null) {
            database.child(subId).child("rating").get().addOnSuccessListener { snapshot ->
                val rating = snapshot.value?.toString()?.toFloat()
                if (rating != null) {
                    ratingBar.rating = rating
                }
            }
        }
    }
    }

