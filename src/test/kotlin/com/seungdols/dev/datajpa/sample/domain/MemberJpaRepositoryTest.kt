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
}
