package com.example.android_utils

import android.util.Log
import com.example.utils.Logger
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Android-specific logger using Android Log API
object AndroidLogger {
    private const val TAG = "AndroidLogger"
    
    fun d(message: String) {
        val time = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault()).format(Date())
        val stackTrace = Throwable().stackTrace
        val caller = stackTrace[1]
        val className = caller.className.substringAfterLast(".")
        val methodName = caller.methodName
        
        Log.d(TAG, "$time $className#$methodName : $message")
    }
    
    fun i(message: String) {
        val time = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault()).format(Date())
        val stackTrace = Throwable().stackTrace
        val caller = stackTrace[1]
        val className = caller.className.substringAfterLast(".")
        val methodName = caller.methodName
        
        Log.i(TAG, "$time $className#$methodName : $message")
    }
    
    fun e(message: String, throwable: Throwable? = null) {
        val time = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault()).format(Date())
        val stackTrace = Throwable().stackTrace
        val caller = stackTrace[1]
        val className = caller.className.substringAfterLast(".")
        val methodName = caller.methodName
        
        if (throwable != null) {
            Log.e(TAG, "$time $className#$methodName : $message", throwable)
        } else {
            Log.e(TAG, "$time $className#$methodName : $message")
        }
    }
    
    // Fallback to pure JVM logger for testing
    fun printToConsole(message: String) {
        Logger.print(message)
    }
}
