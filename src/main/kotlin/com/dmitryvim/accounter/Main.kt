package com.dmitryvim.accounter

import com.dmitryvim.accounter.common.Route
import com.dmitryvim.accounter.telegram.Bot
import io.javalin.Javalin


fun main() {
    val config = readConfig("properties.yaml")

    val app = Javalin.create().start(config.web.port)
    listOf(
            HelloWorldRoute()
    ).forEach { r -> app.get(r.path, r.lambda) }

    println("api initialized")

    Bot(config.telegram)

    println("bot initialized")
}

class HelloWorldRoute : Route("/", { ctx -> ctx.result("Hello World") })