package com.dmitryvim.accounter

import com.dmitryvim.accounter.application.UserApplicationService
import com.dmitryvim.accounter.common.StubEntityRepository
import com.dmitryvim.accounter.domain.*
import com.dmitryvim.accounter.telegram.Bot
import com.dmitryvim.accounter.telegram.RequestHandler
import com.dmitryvim.accounter.telegram.RequestHandlers
import com.dmitryvim.accounter.telegram.Response


fun main() {
    val config = readConfig("properties.yaml")
//    WebApplication(config.web, TelegramProvider(config.telegram))


    val userRepository = StubEntityRepository<UserId, User>()
    val treasuryRepository = StubEntityRepository<TreasuryId, Treasury>()
    val treasuryUserRepository = StubEntityRepository<TreasuryUserId, TreasuryUser>()
    val userApplicationService = UserApplicationService(userRepository, treasuryRepository, treasuryUserRepository)

    // TODO move to dedicated configurer
    val handlers = RequestHandlers(
            RequestHandler("test") { Response(it.text) },
            RequestHandler("__list users") {
                Response(userRepository.map.values.asSequence()
                        .map { "${it.tgUserId} ${it.tgUserName} ${it.displayName}" }
                        .reduce { left, right -> "$left\n$right" })
            },

            RequestHandler("/start") {
                val user = userApplicationService.createUser(
                        tgUserId = it.user.userId,
                        tgUserName = it.user.userName,
                        displayName = it.user.displayName)
                Response("Welcome ${user.displayName}")
            },

            RequestHandler("create treasury") {
                val treasury = userApplicationService.createTreasury(it.user.userId, it.text)
                Response("Treasury ${treasury.name} created and switched as active")
            },
            RequestHandler("switch treasury") {
                val user = userApplicationService.switchTreasury(it.user.userId, it.text)
                Response("${user.displayName} switched active treasury")
            }
    )

    Bot(config.telegram, handlers)
}