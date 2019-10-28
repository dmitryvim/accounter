package com.dmitryvim.accounter.domain

import com.dmitryvim.accounter.common.Entity
import com.dmitryvim.accounter.common.EntityId
import java.util.*

class TreasuryId(id: UUID) : EntityId(id)

class Treasury(id: TreasuryId,
               var ownerId: UserId,
               var name: String) : Entity<TreasuryId>(id)

class TreasuryUserId(id: UUID) : EntityId(id)

class TreasuryUser(id: TreasuryUserId,
                   var treasuryId: TreasuryId,
                   var userId: UserId) : Entity<TreasuryUserId>(id)