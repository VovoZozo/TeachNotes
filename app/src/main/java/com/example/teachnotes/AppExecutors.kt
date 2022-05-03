package com.example.teachnotes

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object AppExecutors {
    val ioExecutor: ExecutorService = Executors.newFixedThreadPool(4)
}