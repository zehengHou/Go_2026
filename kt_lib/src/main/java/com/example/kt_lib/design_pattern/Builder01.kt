package com.example.kt_lib.design_pattern

class Request private constructor(
    val url: String,
    val method: String
) {
    class Builder {
        private var url: String = ""
        private var method: String = "GET"

        fun url(url: String) = apply { this.url = url }
        fun method(method: String) = apply { this.method = method }

        fun build(): Request {
            require(url.isNotEmpty())
            return Request(url, method)
        }
    }
}

fun main() {
    val request = Request.Builder()
        .method("")
        .url("")
        .build()
}
