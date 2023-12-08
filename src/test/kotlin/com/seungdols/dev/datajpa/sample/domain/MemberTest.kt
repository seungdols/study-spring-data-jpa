package com.seungdols.dev.datajpa.sample.domain

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class MemberTest(
    @PersistenceContext
    val em: EntityManager
) {


    @Test
    @Transactional
    @Rollback(false)
    fun `member entity 테스트`() {
        val teamA = Team().apply { name = "teamA" }
        val teamB = Team().apply { name = "teamB" }
        em.persist(teamA)
        em.persist(teamB)

        val member1 = Member().apply {
            username = "member1"
            age = 20
            team = teamA
            changeTeam(teamA)
        }
        val member2 = Member().apply {
            username = "member2"
            age = 25
            team = teamA
            changeTeam(teamA)
        }
        val member3 = Member().apply {
            username = "member3"
            age = 30
            team = teamB
            changeTeam(teamB)
        }
        val member4 = Member().apply {
            username = "member4"
            age = 40
            team = teamB
            changeTeam(teamB)
        }

        em.persist(member1)
        em.persist(member2)
        em.persist(member3)
        em.persist(member4)

        em.flush()
        em.clear()

        val resultList = em.createQuery("select m from Member m", Member::class.java)
            .resultList

        for (member in resultList) {
            println("member= ${member.id}, ${member.age}, ${member.username}")
            println("-> member.team= ${member.team?.id}, ${member.team?.name}")
        }
    }
}
