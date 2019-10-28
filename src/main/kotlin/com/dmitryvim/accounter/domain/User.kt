package com.dmitryvim.accounter.domain

import com.dmitryvim.accounter.common.Entity
import com.dmitryvim.accounter.common.EntityId
import java.util.*

class UserId(id: UUID) : EntityId(id)

class User(id: UserId,
           var displayName: String,
           var tgUserId: Int,
           var tgUserName: String,
           var activeTreasury: TreasuryId?) : Entity<UserId>(id) {

    fun switchTreasury(treasury: Treasury): User = User(id, displayName, tgUserId, tgUserName, treasury.id)

}