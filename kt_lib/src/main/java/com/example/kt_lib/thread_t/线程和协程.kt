package com.example.kt_lib.thread_t

import com.example.utils.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

val lock = Any()
fun main() {
    //    test01()
    //    test2()
}

private fun test01() {
    runBlocking {
        val cores = Runtime.getRuntime().availableProcessors()
        Logger.print("CPU cores: $cores")

        repeat(cores) { index ->
            launch(Dispatchers.Default) {
                Logger.print("Blocking coroutine $index on ${Thread.currentThread().name}")
                Thread.sleep(10_000) // 阻塞线程
                Logger.print("Done $index")
            }
        }

        launch(Dispatchers.Default) {
            Logger.print("⚠️ This may never execute if thread pool is exhausted")
        }
    }
}

private fun test2() {
    runBlocking {
        repeat(4) { index ->
            launch(Dispatchers.Default) {
                synchronized(lock) {
                    println("Coroutine $index got lock on ${Thread.currentThread().name}")
//                    delay(2000)
                    // 协程挂起 The 'delay' suspension point is inside a critical section.
                    println("Coroutine $index done")
                }
            }
        }
    }
}

@Synchronized
fun test3(): Unit {

}