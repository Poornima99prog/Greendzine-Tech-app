package com.example.greendzinetechapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var etSearch: EditText
    private lateinit var adapter: UserAdapter
    private var userList = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        etSearch = findViewById(R.id.etSearch)

        fetchData()

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s)
            }
        })
    }

    private fun fetchData() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://reqres.in/api/users?page=2"

        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                val data = response.getJSONArray("data")
                for (i in 0 until data.length()) {
                    val obj = data.getJSONObject(i)
                    val user = User(
                        obj.getInt("id"),
                        obj.getString("first_name"),
                        obj.getString("last_name"),
                        obj.getString("email")
                    )
                    userList.add(user)
                }
                adapter = UserAdapter(this, userList)
                listView.adapter = adapter
            },
            { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            })

        queue.add(request)
    }
}

