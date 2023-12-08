package com.seungdols.dev.datajpa.sample.domain

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository

@Repository
class MemberJpaRepository(
    @PersistenceContext
    private val em: EntityManager
) {
    fun save(member: Member): Member {
        em.persist(member)
        return member
    }

    fun find(id: Long): Member = em.find(Member::class.java, id)
}
