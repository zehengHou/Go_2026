package com.example.kt_lib.design_pattern

interface Coffee {
    fun cost(): Int
}

class BasicCoffee : Coffee {
    override fun cost() = 10
}

class MilkDecorator(
    private val coffee: Coffee
) : Coffee {
    override fun cost() = coffee.cost() + 2
}

fun main() {
    val coffee = MilkDecorator(BasicCoffee())
    println(coffee.cost()) // 12

}
