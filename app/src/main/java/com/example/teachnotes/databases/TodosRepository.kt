package com.example.teachnotes.databases

import com.example.teachnotes.models.Todo
import com.github.javafaker.Faker

object TodosRepository {
    private val todoFaker = Faker()
    var todos = mutableListOf<Todo>()

    init {
        repeat(100) {
            addRandomTodo()
        }
    }

    private fun addRandomTodo() {
        todos.add(createRandomTodo())
    }

    private fun createRandomTodo() = Todo(
        todoFaker.harryPotter().spell()
    )
}