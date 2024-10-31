package com.example.listsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.EditText
import android.widget.TextView

class Student {
    var id: String? = null
    var fullName: String? = null

    constructor(id: String, fullName: String) {
        this.id = id
        this.fullName = fullName
    }
}

class StudentAdapter(private val students: ArrayList<Student>) :
    RecyclerView.Adapter<StudentAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTextView: TextView = itemView.findViewById(R.id.id_text_view)
        val fullNameTextView: TextView = itemView.findViewById(R.id.full_name_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.student_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.idTextView.text = students[position].id
        holder.fullNameTextView.text = students[position].fullName
    }

    override fun getItemCount(): Int {
        return students.size
    }
}

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var studentAdapter: StudentAdapter
    private var studentList: ArrayList<Student> = ArrayList()
    private var filteredList: ArrayList<Student> = ArrayList()
    private lateinit var searchEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        searchEditText = findViewById(R.id.search_edit_text)

        studentList.add(Student("20200001", "Nguyen Abc"))
        studentList.add(Student("20200002", "Tran Def"))
        studentList.add(Student("20200003", "Tran Fad"))
        studentList.add(Student("20200004", "Phung Asd"))
        studentList.add(Student("20200005", "Mai Awe"))

        studentAdapter = StudentAdapter(studentList)
        recyclerView.adapter = studentAdapter

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val keyword = s.toString().trim()
                if (keyword.length > 2) {
                    filteredList.clear()
                    for (student in studentList) {
                        if (student.id?.contains(keyword)!! || student.fullName?.contains(keyword)!!) {
                            filteredList.add(student)
                        }
                    }
                    studentAdapter = StudentAdapter(filteredList)
                    recyclerView.adapter = studentAdapter
                } else {
                    studentAdapter = StudentAdapter(studentList)
                    recyclerView.adapter = studentAdapter
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }
}