package com.example.kt_lib.design_pattern

import com.example.utils.Logger
import kotlin.reflect.KClass

interface IPay {
    fun pay()
}

class AliIPay : IPay {
    override fun pay() {
        Logger.print("pay")
    }
}

class WxPay : IPay {
    override fun pay() {
        Logger.print("pay")
        val x = WxPay::class
    }
}

object Factory01 {
    fun <T : IPay> createPay(pay: KClass<T>): IPay {
        return when (pay) {
            WxPay::class -> WxPay()
            AliIPay::class -> AliIPay()
            else -> {
                WxPay()
            }
        }
    }
}

fun main() {
    val pay = Factory01.createPay(WxPay::class)
    Logger.print(pay.toString())
    pay.pay()
}