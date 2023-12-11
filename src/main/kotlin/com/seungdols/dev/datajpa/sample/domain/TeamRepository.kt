package com.seungdols.dev.datajpa.sample.domain

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository

@Repository
class TeamRepository(
    @PersistenceContext
    private val em: EntityManager
) {

    fun save(team: Team): Team {
        em.persist(team)
        return team
    }

    fun delete(team: Team): Unit {
        em.remove(team)
    }

    fun findAll(team: Team): List<Team> {
        return em.createQuery("select team from Team team", Team::class.java).resultList
    }

    fun findById(id: Long): Team {
        return em.find(Team::class.java, id)
    }

    fun count(): Long {
        return em.createQuery("select count(team) from Team team", Long::class.java).singleResult
    }
}
