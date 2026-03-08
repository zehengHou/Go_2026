package com.example.kt_lib.design_pattern

sealed class OrderState {
    object Created : OrderState()
    object Paid : OrderState()
    object Shipped : OrderState()
    object Completed : OrderState()
    object Cancelled : OrderState()
}

sealed class OrderEvent {
    object Pay : OrderEvent()
    object Ship : OrderEvent()
    object Confirm : OrderEvent()
    object Cancel : OrderEvent()
}

fun reduce(state: OrderState, event: OrderEvent): OrderState {
    return when (state) {
        OrderState.Created -> when (event) {
            OrderEvent.Pay -> OrderState.Paid
            OrderEvent.Cancel -> OrderState.Cancelled
            else -> state
        }

        OrderState.Paid -> when (event) {
            OrderEvent.Ship -> OrderState.Shipped
            OrderEvent.Cancel -> OrderState.Cancelled
            else -> state
        }

        OrderState.Shipped -> when (event) {
            OrderEvent.Confirm -> OrderState.Completed
            else -> state
        }

        OrderState.Completed,
        OrderState.Cancelled -> state
    }
}

fun main() {
    var state: OrderState = OrderState.Created
    println("初始状态: $state")

    state = reduce(state, OrderEvent.Pay)
    println("支付后状态: $state")

    state = reduce(state, OrderEvent.Ship)
    println("发货后状态: $state")

    state = reduce(state, OrderEvent.Confirm)
    println("确认收货后状态: $state")
}
