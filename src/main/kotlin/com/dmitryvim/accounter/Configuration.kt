package com.dmitryvim.accounter

import com.dmitryvim.accounter.telegram.TelegramConfig
import com.dmitryvim.accounter.web.WebConfig
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule

fun readConfig(filename: String): Configuration {
    val text = {}.javaClass.classLoader
            .getResource(filename) ?: throw IllegalStateException()
    val mapper = ObjectMapper(YAMLFactory()).registerModule(KotlinModule())
    return mapper.readValue(text, Configuration::class.java)
}

data class Configuration(
        var telegram: TelegramConfig,
        var web: WebConfig)