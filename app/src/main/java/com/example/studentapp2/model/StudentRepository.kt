package com.example.studentapp2.model

object StudentRepository {
    private val students = mutableListOf<Student>()

    init {
        // Add some sample data
        addStudent(Student("123456789", "John Doe"))
        addStudent(Student("987654321", "Jane Smith"))
        addStudent(Student("555555555", "Bob Johnson"))
    }

    fun getStudents(): List<Student> {
        return students.toList()
    }

    fun getStudentById(id: String): Student? {
        return students.find { it.id == id }
    }

    fun addStudent(student: Student) {
        students.add(student)
    }

    fun updateStudent(student: Student, oldId: String) {
        val index = students.indexOfFirst { it.id == oldId }
        if (index >= 0) {
            students[index] = student
        }
    }

    fun deleteStudent(id: String) {
        students.removeAll { it.id == id }
    }

    fun toggleStudentCheck(id: String): Boolean {
        val student = getStudentById(id)
        student?.let {
            it.isChecked = !it.isChecked
            return it.isChecked
        }
        return false
    }
} 