package com.dmitryvim.accounter

import com.dmitryvim.accounter.common.Route
import com.dmitryvim.accounter.telegram.Bot
import io.javalin.Javalin


fun main() {
    val app = Javalin.create().start(7000)
    listOf(
            HelloWorldRoute()
    ).forEach { r -> app.get(r.path, r.lambda) }

    println("api initialized")

    Bot().init()

    println("bot initialized")
}

class HelloWorldRoute : Route("/", { ctx -> ctx.result("Hello World") })

