package com.dmitryvim.accounter.telegram

import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.UpdatesListener
import com.pengrad.telegrambot.request.SendMessage


class Bot(config: TelegramConfig, handlers: RequestHandlers) {
    init {
        val bot = TelegramBot(config.token)
        bot.setUpdatesListener { updates ->
            updates.forEach { update ->
                run {
                    try {
                        val chatId = update.message().chat().id()
                        val sender = update.message().from()
                        val user = User(userId = sender.id() ?: 0,
                                userName = update.message().from().username(),
                                displayName = sender.firstName() + " " + sender.lastName())
                        val request = Request(text = update.message().text(),
                                user = user)
                        val response = handlers.handle(request)
                        bot.execute(SendMessage(chatId, response.text))
                    } catch (e: RuntimeException) {
                        e.printStackTrace() // TODO handling
                    }
                }
            }
            UpdatesListener.CONFIRMED_UPDATES_ALL
        }
    }
}

data class TelegramConfig(var token: String)

// TODO move to dedicated package

data class User(var userId: Int, var userName: String, var displayName: String)

class RequestHandlers(var handlers: List<RequestHandler>) {

    constructor(vararg hds: RequestHandler) : this(hds.toList())

    fun handle(request: Request): Response = handlers.asSequence()
            .map { it.handler(request) }
            .filter { it != null }
            .firstOrNull() ?: Response("command not found")
}

class RequestHandler(var handler: (Request) -> Response?) {

    constructor(left: String, handler: (RequestContext) -> Response) : this({ request ->
        if (request.text.startsWith(left)) {
            val right = request.text.substring(left.length).trim()
            val ctx = RequestContext(cmd = left, text = right, user = request.user)
            handler(ctx)
        } else {
            null
        }
    })
}

class RequestContext(var cmd: String, var text: String, var user: User)

data class Request(var text: String, var user: User)

data class Response(var text: String)