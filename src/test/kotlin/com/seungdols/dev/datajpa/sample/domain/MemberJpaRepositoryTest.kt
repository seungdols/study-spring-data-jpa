package com.seungdols.dev.datajpa.sample.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
@Rollback(false)
class MemberJpaRepositoryTest(
    val memberJpaRepository: MemberJpaRepository
) {

    @Test
    fun `member 테스트`() {
        val member = Member().apply { username = "memberA" }

        val saveMember = memberJpaRepository.save(member)

        val findMember = memberJpaRepository.find(saveMember.id)
        assertThat(findMember.id).isEqualTo(member.id)
        assertThat(findMember.username).isEqualTo(member.username)
        assertThat(findMember).isEqualTo(member)
    }

    @Test
    fun `findByUsernameAndAgeGreaterThan 테스트`() {
        val member1 = Member().apply {
            username = "AAA"
            age = 10
        }
        val member2 = Member().apply {
            username = "AAA"
            age = 20
        }
        val saveMember1 = memberJpaRepository.save(member1)
        val saveMember2 = memberJpaRepository.save(member2)

        val members = memberJpaRepository.findByUsernameAndAgeGreaterThan("AAA", 15)
        assertThat(members.first().username).isEqualTo("AAA")
        assertThat(members.first().age).isEqualTo(20)
    }
}
