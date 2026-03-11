package com.example.kt_lib.flow

import com.example.utils.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate

suspend fun main() {
    val mutableStateFlow = MutableStateFlow(0)

    repeat(10) {
        mutableStateFlow.value = it + 1
    }

    mutableStateFlow.collect {
        Logger.print(it)
    }

//    mutableStateFlow.collectLatest {
//        Logger.print(it)
//    }
}