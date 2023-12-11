package com.seungdols.dev.datajpa.sample.domain

import org.assertj.core.api.Assertions
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

    @Test
    fun `findById 테스트`() {
        val member1 = Member().apply { username = "memberA" }
        val member2 = Member().apply { username = "memberB" }
        memberRepository.save(member1)
        memberRepository.save(member2)

        val findMember1 = memberRepository.findById(member1.id).get()
        val findMember2 = memberRepository.findById(member2.id).get()

        assertThat(findMember1).isEqualTo(member1)
        assertThat(findMember2).isEqualTo(member2)
    }

    @Test
    fun `findAll 테스트`() {
        val member1 = Member().apply { username = "memberA" }
        val member2 = Member().apply { username = "memberB" }
        memberRepository.save(member1)
        memberRepository.save(member2)

        val findAll = memberRepository.findAll()
        assertThat(findAll.size).isEqualTo(2)

        val count = memberRepository.count()
        assertThat(count).isEqualTo(2)
    }

    @Test
    fun `count 테스트`() {
        val member1 = Member().apply { username = "memberA" }
        val member2 = Member().apply { username = "memberB" }
        memberRepository.save(member1)
        memberRepository.save(member2)

        val count = memberRepository.count()
        assertThat(count).isEqualTo(2)
    }

    @Test
    fun `delete 테스트`() {
        val member1 = Member().apply { username = "memberA" }
        val member2 = Member().apply { username = "memberB" }
        memberRepository.save(member1)
        memberRepository.save(member2)

        memberRepository.delete(member1)
        memberRepository.delete(member2)

        val count = memberRepository.count()
        assertThat(count).isEqualTo(0)
    }
}
