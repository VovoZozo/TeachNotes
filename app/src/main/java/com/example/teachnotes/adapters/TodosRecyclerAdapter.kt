package com.example.teachnotes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.teachnotes.R
import com.example.teachnotes.models.Todo

class TodosRecyclerAdapter(
    private val todos: List<Todo>
) : RecyclerView.Adapter<TodosRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = todos[position]
        holder.itemPreviewTodoText.text = itemsViewModel.todoText
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemPreviewTodoText: TextView = itemView.findViewById(R.id.todo_text)
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}