package com.dmitryvim.accounter.common

import java.util.*

open class Entity<ID>(private var id: EntityId)
        where ID : EntityId

open class EntityId(private var id: UUID)

interface EntityRepository<ID, E> {
    fun find(id: ID): E?
    fun add(e: E)
    fun update(e: E)
}