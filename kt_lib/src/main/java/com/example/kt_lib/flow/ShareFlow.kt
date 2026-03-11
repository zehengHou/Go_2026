package com.example.kt_lib.flow

import com.example.kt_lib.design_pattern.cs
import com.example.utils.Logger
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    val shareFlow = MutableSharedFlow<Int>()

    val job = launch {
        Logger.print("launched")
        shareFlow
            .conflate()
            .collect {
                Logger.print("collect -> $it")
            }

    }

    delay(1000)

    repeat(10) {
        Logger.print(it + 1)
        shareFlow.emit(it + 1)
    }

    delay(5000)
    job.cancel()
}