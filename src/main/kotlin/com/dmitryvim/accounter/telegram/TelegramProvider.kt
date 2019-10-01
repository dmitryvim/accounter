package com.dmitryvim.accounter.telegram

import com.pengrad.telegrambot.TelegramBot
import org.apache.commons.codec.binary.Hex
import java.security.MessageDigest
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class TelegramProvider(var config: TelegramConfig) {

    fun createBot(): TelegramBot {
        return TelegramBot(config.token)
    }

    fun verify(line: String, hash: String): Boolean {
        val sha256Hmac: Mac = Mac.getInstance("HmacSHA256")
        val digest = MessageDigest.getInstance("SHA-256").digest(config.token.toByteArray())
        val secretKeySpec = SecretKeySpec(digest, "SHA-256")
        sha256Hmac.init(secretKeySpec)
        return hash == Hex.encodeHexString(sha256Hmac.doFinal(line.toByteArray()))
    }
}