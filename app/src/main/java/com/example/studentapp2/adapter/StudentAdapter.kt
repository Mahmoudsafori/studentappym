package com.example.studentapp2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentapp2.R
import com.example.studentapp2.model.Student
import com.example.studentapp2.model.StudentRepository

class StudentAdapter(
    private val onItemClick: (Student) -> Unit,
    private val onCheckedChange: (Student) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private var students: List<Student> = StudentRepository.getStudents()

    fun updateStudents() {
        students = StudentRepository.getStudents()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.bind(student, onItemClick, onCheckedChange)
    }

    override fun getItemCount(): Int = students.size

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val avatar: ImageView = itemView.findViewById(R.id.student_avatar)
        private val name: TextView = itemView.findViewById(R.id.student_name)
        private val id: TextView = itemView.findViewById(R.id.student_id)
        private val checkbox: CheckBox = itemView.findViewById(R.id.student_checkbox)

        fun bind(
            student: Student,
            onItemClick: (Student) -> Unit,
            onCheckedChange: (Student) -> Unit
        ) {
            name.text = student.name
            id.text = "ID: ${student.id}"
            checkbox.isChecked = student.isChecked
            
            itemView.setOnClickListener {
                onItemClick(student)
            }
            
            checkbox.setOnClickListener {
                onCheckedChange(student)
            }
        }
    }
} 