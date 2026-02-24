package com.example.kt_lib.design_pattern

import com.example.untils.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

val cs = CoroutineScope(Dispatchers.Default)

fun main() {
    runBlocking {
        repeat(5) {
            launch {
                Logger.print(SingletonInstance.getInstance().toString())
            }
        }
    }
}

class SingletonInstance {
    companion object {
        @Volatile
        private var instance: SingletonInstance? = null

        fun getInstance(): SingletonInstance {
            return instance ?: synchronized(this) {
                instance ?: SingletonInstance().also {
                    instance = it
                }
            }
        }
    }

}
