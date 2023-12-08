package com.seungdols.dev.datajpa.sample.domain

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
@Rollback(false)
class MemberRepositoryTest(
    val memberRepository: MemberRepository
) {
    @Test
    fun `member 테스트`() {
        val member = Member().apply { username = "memberA" }

        val savedMember = memberRepository.save(member)
        val findMember = memberRepository.findById(savedMember.id).get()
        Assertions.assertThat(findMember.id).isEqualTo(member.id)
        Assertions.assertThat(findMember.username).isEqualTo(member.username)
        Assertions.assertThat(findMember).isEqualTo(member)
    }
}
