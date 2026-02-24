package com.example.kt_lib.flow

import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOf

suspend fun main() {
    val flow = flowOf(1, 2, 3, 4, 5, 6)

    flow.conflate().collect {

    }
}