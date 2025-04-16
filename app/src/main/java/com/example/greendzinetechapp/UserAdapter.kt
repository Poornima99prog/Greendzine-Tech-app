package com.example.greendzinetechapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView

class UserAdapter(private val context: Context, private var userList: MutableList<User>) :
    BaseAdapter(), Filterable {

    private var filteredList: MutableList<User> = userList.toMutableList()

    override fun getCount(): Int = filteredList.size

    override fun getItem(position: Int): Any = filteredList[position]

    override fun getItemId(position: Int): Long = filteredList[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_user, parent, false)
        val user = filteredList[position]

        view.findViewById<TextView>(R.id.tvName).text = "${user.first_name} ${user.last_name}"
        view.findViewById<TextView>(R.id.tvEmail).text = user.email
        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint.isNullOrBlank()) {
                    filterResults.values = userList
                } else {
                    val query = constraint.toString().lowercase()
                    filterResults.values = userList.filter {
                        it.first_name.lowercase().contains(query)
                    }
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = (results?.values as? List<User>)?.toMutableList() ?: mutableListOf()
                notifyDataSetChanged()
            }
        }
    }
}
