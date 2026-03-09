package com.example.kt_lib.design_pattern

import com.example.utils.Logger
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

object EnvConfig {
    val baseUrl: String = "https://api.xxx.com"
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

class Singleton private constructor() {

    companion object {
        fun getInstance() = Holder.instance
    }

    private object Holder {
        val instance = Singleton()
    }
}


class Repo private constructor() {
    companion object {
        val instance: Repo by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            Repo()
        }
    }
}

