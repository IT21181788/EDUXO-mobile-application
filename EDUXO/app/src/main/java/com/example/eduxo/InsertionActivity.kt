package com.example.eduxo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var etsubjectname: EditText
    private lateinit var etnote: EditText
    private lateinit var etlink: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etsubjectname = findViewById(R.id.etsubjectname)
        etnote = findViewById(R.id.etnote)
        etlink = findViewById(R.id.etlink)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("SUBJECT")

        btnSaveData.setOnClickListener {
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData() {

        //getting values
        val subjectname = etsubjectname.text.toString()
        val note = etnote.text.toString()
        val link = etlink.text.toString()

        if (subjectname.isEmpty()) {
            etsubjectname.error = "Please enter subject name"
        }
        if (subjectname.isEmpty()) {
            Toast.makeText(this, "Please enter a subject name", Toast.LENGTH_LONG).show()
            return
        }

        if (note.isEmpty()) {
            Toast.makeText(this, "Note should be less than 100 characters", Toast.LENGTH_LONG).show()
            return
        }

        if (!link.startsWith("http://") && !link.startsWith("https://")) {
            Toast.makeText(this, "Please enter a valid link", Toast.LENGTH_LONG).show()
            return
        }
        if (note.isEmpty()) {
            etnote.error = "Please Enter notes "
        }
        if (link.isEmpty()) {
            etlink.error = "Please enter a link"
        }

        val subId = dbRef.push().key!!

        val employee = SubjectModel(subId, subjectname, note, link)

        dbRef.child(subId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this, "subject contain inserted successfully", Toast.LENGTH_LONG).show()

                etsubjectname.text.clear()
                etnote.text.clear()
                etlink.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}