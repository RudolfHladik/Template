package com.rudolfhladik.kmm.template

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}
