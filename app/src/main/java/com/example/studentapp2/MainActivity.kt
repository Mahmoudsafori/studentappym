package com.example.studentapp2

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentapp2.adapter.StudentAdapter
import com.example.studentapp2.model.StudentRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.students_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        // Initialize adapter with lambda functions
        adapter = StudentAdapter(
            onItemClick = { student ->
                // Navigate to student details
                val detailsIntent = Intent(this, StudentDetailsActivity::class.java)
                detailsIntent.putExtra("student_id", student.id)
                startActivity(detailsIntent)
            },
            onCheckedChange = { student ->
                // Toggle student check status
                StudentRepository.toggleStudentCheck(student.id)
                adapter.updateStudents()
            }
        )
        
        recyclerView.adapter = adapter

        // Set up FAB for adding new students
        findViewById<FloatingActionButton>(R.id.fab_add_student).setOnClickListener {
            val newStudentIntent = Intent(this, NewStudentActivity::class.java)
            startActivity(newStudentIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Update the list whenever we return to this activity
        adapter.updateStudents()
    }
}