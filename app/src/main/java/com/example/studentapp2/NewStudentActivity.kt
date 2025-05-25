package com.example.studentapp2

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studentapp2.model.Student
import com.example.studentapp2.model.StudentRepository
import com.google.android.material.textfield.TextInputEditText

class NewStudentActivity : AppCompatActivity() {
    
    private lateinit var nameEditText: TextInputEditText
    private lateinit var idEditText: TextInputEditText
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_student)
        
        // Initialize views
        nameEditText = findViewById(R.id.name_edit_text)
        idEditText = findViewById(R.id.id_edit_text)
        saveButton = findViewById(R.id.save_button)
        cancelButton = findViewById(R.id.cancel_button)
        
        // Set up click listeners
        saveButton.setOnClickListener {
            saveStudent()
        }
        
        cancelButton.setOnClickListener {
            finish()
        }
    }
    
    private fun saveStudent() {
        val name = nameEditText.text.toString().trim()
        val id = idEditText.text.toString().trim()
        
        // Validate inputs
        if (name.isEmpty() || id.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Check if ID is already used
        if (StudentRepository.getStudentById(id) != null) {
            Toast.makeText(this, "Student ID already exists", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Save the new student
        val newStudent = Student(id, name)
        StudentRepository.addStudent(newStudent)
        
        Toast.makeText(this, "Student added successfully", Toast.LENGTH_SHORT).show()
        finish()
    }
} 