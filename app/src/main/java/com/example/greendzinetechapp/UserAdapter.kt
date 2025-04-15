package com.example.greendzinetechapp

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView

class UserAdapter(private val context: Context, private var userList: List<User>) : BaseAdapter(), Filterable {

    private var filteredList: List<User> = userList

    override fun getCount(): Int = filteredList.size
    override fun getItem(position: Int): Any = filteredList[position]
    override fun getItemId(position: Int): Long = position.toLong()

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_user, parent, false)
        val user = filteredList[position]

        view.findViewById<TextView>(R.id.tvId).text = "ID: ${user.id}"
        view.findViewById<TextView>(R.id.tvName).text = "${user.first_name} ${user.last_name}"
        view.findViewById<TextView>(R.id.tvEmail).text = user.email

        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val result = FilterResults()
                val query = constraint?.toString()?.lowercase() ?: ""
                result.values = if (query.isEmpty()) {
                    userList
                } else {
                    userList.filter {
                        it.first_name.lowercase().contains(query)
                    }
                }
                return result
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as List<User>
                notifyDataSetChanged()
            }
        }
    }
}
