package com.dmitryvim.accounter.telegram

import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.UpdatesListener
import com.pengrad.telegrambot.request.SendMessage


class Bot(config: BotConfig) {
    init {
        val bot = TelegramBot(config.token)
        bot.setUpdatesListener { updates ->
            updates.forEach { update ->
                run {
                    val chatId = update.message().chat().id()
                    val message = """
                        text: ${update.message().text()}
                        chat: ${update.message().chat().id()}
                        chatUsername: ${update.message().chat().username()}
                        author: ${update.message().from().username()}
                    """.trimIndent()
                    println(message)
                    bot.execute(SendMessage(chatId, message))
                }
            }
            UpdatesListener.CONFIRMED_UPDATES_ALL
        }
    }
}

data class BotConfig(var token: String)