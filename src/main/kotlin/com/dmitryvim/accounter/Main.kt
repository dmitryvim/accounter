package com.dmitryvim.accounter

import com.dmitryvim.accounter.telegram.Bot
import com.dmitryvim.accounter.telegram.TelegramProvider
import com.dmitryvim.accounter.web.WebApplication


fun main() {
    val config = readConfig("properties.yaml")
    WebApplication(config.web, TelegramProvider(config.telegram))
    Bot(config.telegram)
}