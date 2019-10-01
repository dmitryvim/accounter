package com.dmitryvim.accounter.web

import com.dmitryvim.accounter.common.Route
import com.dmitryvim.accounter.telegram.TelegramProvider
import io.javalin.Javalin


class WebApplication(config: WebConfig, tgProvider: TelegramProvider) {
    init {
        val app = Javalin.create().start(config.port)
        listOf(
                HelloWorldRoute(),
                LoginPageRoute(),
                SignedPageRoute(tgProvider)
        ).forEach { r -> app.get(r.path, r.lambda) }
    }
}

data class WebConfig(var port: Int)


class HelloWorldRoute : Route("/", { ctx -> ctx.result("Hello World") })

class LoginPageRoute : Route("/signin", { context ->
    run {
        val inputStream = {}.javaClass.classLoader.getResourceAsStream("html/index.html")
        context.result(inputStream!!).contentType("text/html")
    }
})

class SignedPageRoute(tgProvider: TelegramProvider) : Route("/signed", { context ->
    run {
        val hash = context.queryParam("hash")!!
        val concat = context.queryParamMap()
                .entries
                .filter { entry -> entry.key != "hash" }
                .map { entry -> "${entry.key}=${entry.value[0]}" }
                .sorted()
                .joinToString("\n")
        context.result("""
            verify=${tgProvider.verify(concat, hash)}
        """.trimIndent())
    }
})
