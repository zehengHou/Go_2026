package com.example.kt_lib.design_pattern

import kotlin.math.log

interface LoginService {
    fun login()
}

class RealLoginService : LoginService {
    override fun login() {
        println("真实登录逻辑")
    }
}

class LoginServiceProxy(
    private val target: LoginService
) : LoginService {
    override fun login() {
        println("登录前埋点")
        target.login()
        println("登录后日志")
    }
}

fun main() {
    val loginServiceProxy = LoginServiceProxy(RealLoginService())
    loginServiceProxy.login()
}