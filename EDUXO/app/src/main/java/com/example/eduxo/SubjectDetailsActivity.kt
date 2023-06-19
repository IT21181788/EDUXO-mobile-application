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

class SubjectDetailsActivity : AppCompatActivity() {

    private lateinit var tvsubId: TextView
    private lateinit var tvsubjectname: TextView
    private lateinit var tvnote: TextView
    private lateinit var tvlink: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("subId").toString(),
                intent.getStringExtra("subjectname").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("subId").toString()
            )
        }

    }

    private fun initView() {
        tvsubId = findViewById(R.id.tvsubId)
        tvsubjectname = findViewById(R.id.tvsubjectname)
        tvnote = findViewById(R.id.tvnote)
        tvlink = findViewById(R.id.tvlink)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvsubId.text = intent.getStringExtra("subId")
        tvsubjectname.text = intent.getStringExtra("subjectname")
        tvnote.text = intent.getStringExtra("note")
        tvlink.text = intent.getStringExtra("link")

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("SUBJECT").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Subject data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(
        subId: String,
        subjectname: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val etsubjectname = mDialogView.findViewById<EditText>(R.id.etsubjectname)
        val etnote= mDialogView.findViewById<EditText>(R.id.etnote)
        val etlink = mDialogView.findViewById<EditText>(R.id.etlink)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etsubjectname.setText(intent.getStringExtra("subjectname").toString())
        etnote.setText(intent.getStringExtra("note").toString())
        etlink.setText(intent.getStringExtra("link").toString())

        mDialog.setTitle("Updating $subjectname Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateEmpData(
                subId,
                etsubjectname.text.toString(),
                etnote.text.toString(),
                etlink.text.toString()
            )

            Toast.makeText(applicationContext, "Subject Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvsubjectname.text = etsubjectname.text.toString()
            tvnote.text = etnote.text.toString()
            tvlink.text = etlink.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateEmpData(
        id: String,
        name: String,
        note: String,
        link: String
    )
    {
        // Check if the input values are valid
        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter a subject name", Toast.LENGTH_LONG).show()
            return
        }

        if (note.isEmpty()) {
            Toast.makeText(this, "Note should be greater than 100 characters", Toast.LENGTH_LONG).show()
            return
        }

        if (!link.startsWith("http://") && !link.startsWith("https://")) {
            Toast.makeText(this, "Please enter a valid link", Toast.LENGTH_LONG).show()
            return
        }

        // If the input is valid, update the data
        val dbRef = FirebaseDatabase.getInstance().getReference("SUBJECT").child(id)
        val empInfo = SubjectModel(id, name, note, link)
        dbRef.setValue(empInfo)
    }

}