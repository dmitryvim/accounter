package com.dmitryvim.accounter

import com.dmitryvim.accounter.telegram.BotConfig
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
        var telegram: BotConfig,
        var web: WebConfig)

data class WebConfig(var port: Int)