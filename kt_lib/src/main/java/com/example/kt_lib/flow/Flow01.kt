package com.example.kt_lib.flow

import com.example.utils.Logger
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOf

suspend fun main() {
    val flow = flowOf(1, 2, 3, 4, 5, 6)

//    flow.conflate().collect {
//        Logger.print(it)
//    }
    flow.buffer(capacity = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST).collectLatest {
        Logger.print(it)
    }
}