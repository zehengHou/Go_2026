package com.example.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Pure JVM Logger (no Android dependencies)
object Logger {
    fun print(message: Any) {
        val time = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault()).format(Date())

        val stackTrace = Throwable().stackTrace
        val caller = stackTrace[1]

        val className = caller.className.substringAfterLast(".")
        val methodName = caller.methodName

        println("$time $className#$methodName : $message")
    }
}