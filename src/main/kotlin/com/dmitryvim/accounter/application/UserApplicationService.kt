package com.dmitryvim.accounter.application

import com.dmitryvim.accounter.common.EntityRepository
import com.dmitryvim.accounter.domain.*
import java.util.UUID.randomUUID

class UserApplicationService(var userRepository: EntityRepository<UserId, User>,
                             var treasuryRepository: EntityRepository<TreasuryId, Treasury>,
                             var treasureUserRepository: EntityRepository<TreasuryUserId, TreasuryUser>) {

    fun createUser(tgUserId: Int,
                   tgUserName: String,
                   displayName: String): User {
        return userRepository.add(User(
                id = UserId(randomUUID()),
                tgUserId = tgUserId,
                tgUserName = tgUserName,
                displayName = displayName,
                activeTreasury = null
        ))
    }

    fun createTreasury(tgUserId: Int,
                       treasuryName: String): Treasury {
        val user = userRepository.find { it.tgUserId == tgUserId }.firstOrNull() ?: throw IllegalArgumentException()
        val treasury = treasuryRepository.add(Treasury(
                id = TreasuryId(randomUUID()),
                ownerId = user.id,
                name = treasuryName))
        treasureUserRepository.add(TreasuryUser(TreasuryUserId(randomUUID()), treasury.id, user.id))
        userRepository.update(user.switchTreasury(treasury))
        return treasury
    }

    fun switchTreasury(tgUserId: Int,
                       treasuryName: String): User {
        val user = userRepository.find { it.tgUserId == tgUserId }.firstOrNull()
                ?: throw IllegalArgumentException()
        val treasuryIds = treasureUserRepository.find { it.userId == user.id }.map { it.treasuryId }
        val treasury = treasuryRepository.findAll(treasuryIds).find { it.name == treasuryName }
                ?: throw IllegalArgumentException()
        return userRepository.update(user.switchTreasury(treasury))
    }
}