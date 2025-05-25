package com.example.studentapp2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studentapp2.model.Student
import com.example.studentapp2.model.StudentRepository

class StudentDetailsActivity : AppCompatActivity() {
    
    private lateinit var nameText: TextView
    private lateinit var idText: TextView
    private lateinit var statusText: TextView
    private lateinit var editButton: Button
    private lateinit var backButton: Button
    
    private var studentId: String = ""
    private var student: Student? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)
        
        // Get student ID from intent
        studentId = intent.getStringExtra("student_id") ?: ""
        if (studentId.isEmpty()) {
            Toast.makeText(this, "Error: Student ID not provided", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        
        // Find student by ID
        student = StudentRepository.getStudentById(studentId)
        if (student == null) {
            Toast.makeText(this, "Error: Student not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        
        // Initialize views
        nameText = findViewById(R.id.name_text)
        idText = findViewById(R.id.id_text)
        statusText = findViewById(R.id.status_text)
        editButton = findViewById(R.id.edit_button)
        backButton = findViewById(R.id.back_button)
        
        // Display student data
        displayStudentData()
        
        // Set up click listeners
        editButton.setOnClickListener {
            val intent = Intent(this, EditStudentActivity::class.java)
            intent.putExtra("student_id", studentId)
            startActivity(intent)
        }
        
        backButton.setOnClickListener {
            finish()
        }
    }
    
    override fun onResume() {
        super.onResume()
        // Refresh data when returning to this activity
        student = StudentRepository.getStudentById(studentId)
        if (student == null) {
            // Student might have been deleted
            Toast.makeText(this, "Student no longer exists", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        displayStudentData()
    }
    
    private fun displayStudentData() {
        student?.let {
            nameText.text = it.name
            idText.text = it.id
            statusText.text = if (it.isChecked) "Checked" else "Not checked"
        }
    }
} 