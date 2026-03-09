package com.example.kt_lib.design_pattern

/**
fun pay(type: String, amount: Int) {

if (type == "ALIPAY") {
println("使用支付宝支付 $amount")
}
else if (type == "WECHAT") {
println("使用微信支付 $amount")
}
else if (type == "CARD") {
println("使用银行卡支付 $amount")
}

}*/

interface PayStrategy {
    fun pay(amount: Int)
}

class AliPayStrategy : PayStrategy {

    override fun pay(amount: Int) {
        println("支付宝支付 $amount")
    }

}

class WeChatPayStrategy : PayStrategy {

    override fun pay(amount: Int) {
        println("微信支付 $amount")
    }

}

class CardPayStrategy : PayStrategy {

    override fun pay(amount: Int) {
        println("银行卡支付 $amount")
    }

}

class PaymentContext(
    private val strategy: PayStrategy
) {

    fun executePay(amount: Int) {
        strategy.pay(amount)
    }

}

fun main() {
    val strategy = strategyMap["ALIPAY"]

    val context = PaymentContext(strategy!!)

    context.executePay(100)
}

val strategyMap = mapOf(
    "ALIPAY" to AliPayStrategy(),
    "WECHAT" to WeChatPayStrategy(),
    "CARD" to CardPayStrategy()
)