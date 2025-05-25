package com.example.studentapp2

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.studentapp2.model.Student
import com.example.studentapp2.model.StudentRepository
import com.google.android.material.textfield.TextInputEditText

class EditStudentActivity : AppCompatActivity() {
    
    private lateinit var nameEditText: TextInputEditText
    private lateinit var idEditText: TextInputEditText
    private lateinit var statusCheckBox: CheckBox
    private lateinit var saveButton: Button
    private lateinit var deleteButton: Button
    private lateinit var cancelButton: Button
    
    private var studentId: String = ""
    private var student: Student? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)
        
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
        nameEditText = findViewById(R.id.name_edit_text)
        idEditText = findViewById(R.id.id_edit_text)
        statusCheckBox = findViewById(R.id.status_checkbox)
        saveButton = findViewById(R.id.save_button)
        deleteButton = findViewById(R.id.delete_button)
        cancelButton = findViewById(R.id.cancel_button)
        
        // Populate fields with student data
        student?.let {
            nameEditText.setText(it.name)
            idEditText.setText(it.id)
            statusCheckBox.isChecked = it.isChecked
        }
        
        // Set up click listeners
        saveButton.setOnClickListener {
            updateStudent()
        }
        
        deleteButton.setOnClickListener {
            confirmDelete()
        }
        
        cancelButton.setOnClickListener {
            finish()
        }
    }
    
    private fun updateStudent() {
        val name = nameEditText.text.toString().trim()
        val newId = idEditText.text.toString().trim()
        val isChecked = statusCheckBox.isChecked
        
        // Validate inputs
        if (name.isEmpty() || newId.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Check if the new ID is already used by another student
        if (newId != studentId && StudentRepository.getStudentById(newId) != null) {
            Toast.makeText(this, "Student ID already exists", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Create updated student
        val updatedStudent = Student(newId, name, isChecked)
        
        // Update the student in repository
        StudentRepository.updateStudent(updatedStudent, studentId)
        
        Toast.makeText(this, "Student updated successfully", Toast.LENGTH_SHORT).show()
        finish()
    }
    
    private fun confirmDelete() {
        AlertDialog.Builder(this)
            .setTitle("Delete Student")
            .setMessage("Are you sure you want to delete this student?")
            .setPositiveButton("Delete") { _, _ ->
                StudentRepository.deleteStudent(studentId)
                Toast.makeText(this, "Student deleted", Toast.LENGTH_SHORT).show()
                finish()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
} 