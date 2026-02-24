package com.example.untils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Logger {
    fun d(message: String) {
        val time = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault()).format(Date())

        val stackTrace = Throwable().stackTrace
        val caller = stackTrace[1]

        val className = caller.className.substringAfterLast(".")
        val methodName = caller.methodName

        Log.d("$time $className#$methodName : ", message)
    }

    fun print(message: String) {
        val time = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault()).format(Date())

        val stackTrace = Throwable().stackTrace
        val caller = stackTrace[1]

        val className = caller.className.substringAfterLast(".")
        val methodName = caller.methodName

        println("$time $className#$methodName : $message")
    }
}