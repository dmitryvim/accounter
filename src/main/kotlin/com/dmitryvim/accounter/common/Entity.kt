package com.dmitryvim.accounter.common

import java.util.*
import java.util.concurrent.ConcurrentHashMap

open class Entity<ID>(public var id: ID)
        where ID : EntityId {

}

open class EntityId(private var id: UUID)

interface EntityRepository<ID, E>
        where ID : EntityId,
              E : Entity<ID> {
    fun find(id: ID): E?
    fun findAll(ids: Iterable<ID>): Set<E>
    fun add(e: E): E
    fun update(e: E): E


    @Deprecated("remove switching to DB")
    fun find(predicate: (E) -> Boolean): List<E>
}

class StubEntityRepository<ID, E> : EntityRepository<ID, E>
        where ID : EntityId,
              E : Entity<ID> {
    var map: MutableMap<ID, E> = ConcurrentHashMap()

    override fun find(id: ID): E? = map[id]

    override fun findAll(ids: Iterable<ID>): Set<E> = ids.asSequence().map { map[it] }.filterNotNull().toSet()

    override fun find(predicate: (E) -> Boolean): List<E> = map.values.asSequence().filter { predicate(it) }.toList()

    override fun add(e: E): E {
        map.computeIfPresent(e.id, { _, _ -> throw IllegalStateException() })
        return map.computeIfAbsent(e.id, { _ -> e })
    }

    override fun update(e: E): E {
        map.computeIfAbsent(e.id, { _ -> throw IllegalStateException() })
        return map.computeIfPresent(e.id, { _, _ -> e })!!
    }

}