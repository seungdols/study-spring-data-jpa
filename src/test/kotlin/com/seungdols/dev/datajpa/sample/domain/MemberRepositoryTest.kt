package com.seungdols.dev.datajpa.sample.domain

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
@Rollback(false)
class MemberRepositoryTest(
    val memberRepository: MemberRepository,
    val teamRepository: TeamRepository,
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
        val saveMember1 = memberRepository.save(member1)
        val saveMember2 = memberRepository.save(member2)

        val members = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15)
        assertThat(members.first().username).isEqualTo("AAA")
        assertThat(members.first().age).isEqualTo(20)
    }
    @Test
    fun `findUser 테스트`() {
        val member1 = Member().apply {
            username = "AAA"
            age = 10
        }
        val member2 = Member().apply {
            username = "AAA"
            age = 20
        }
        val saveMember1 = memberRepository.save(member1)
        val saveMember2 = memberRepository.save(member2)

        val members = memberRepository.findUser("AAA", 10)
        assertThat(members.first().username).isEqualTo("AAA")
        assertThat(members.first().age).isEqualTo(10)
    }

    @Test
    fun `findUsernames 테스트`() {
        val member1 = Member().apply {
            username = "AAA"
            age = 10
        }
        val member2 = Member().apply {
            username = "AAA"
            age = 20
        }
        val saveMember1 = memberRepository.save(member1)
        val saveMember2 = memberRepository.save(member2)

        val members = memberRepository.findUsernames()
        assertThat(members.size).isEqualTo(2)
    }
    @Test
    fun `findMemberDto 테스트`() {
        val team1 = Team().apply {
            name = "Team1"
        }
        teamRepository.save(team1)
        val member1 = Member().apply {
            username = "AAA"
            age = 10
        }
        member1.changeTeam(team1)
        val saveMember1 = memberRepository.save(member1)

        val member2 = Member().apply {
            username = "BBB"
            age = 20
        }
        member2.changeTeam(team1)
        val saveMember2 = memberRepository.save(member2)

        val members = memberRepository.findMemberDto()
        for(member in members) {
           println(member)
        }
        assertThat(members.size).isEqualTo(2)
    }

    @Test
    fun `findByNames 테스트`() {
        val member1 = Member().apply {
            username = "AAA"
            age = 10
        }
        val member2 = Member().apply {
            username = "BBB"
            age = 20
        }
        val saveMember1 = memberRepository.save(member1)
        val saveMember2 = memberRepository.save(member2)

        val members = memberRepository.findByNames(listOf("AAA","BBB"))
        assertThat(members.size).isEqualTo(2)
    }
    @Test
    fun `find return type 테스트`() {
        val member1 = Member().apply {
            username = "AAA"
            age = 10
        }
        val member2 = Member().apply {
            username = "BBB"
            age = 20
        }
        val saveMember1 = memberRepository.save(member1)
        val saveMember2 = memberRepository.save(member2)

        val members = memberRepository.findListByUsername("AAA")
        assertThat(members.size).isEqualTo(1)
    }

    @Test
    fun `paging 테스트`() {
        @Test
        fun `paging 테스트`() {
            memberRepository.save(Member().apply {
                username = "member1"
                age = 10
            })
            memberRepository.save(Member().apply {
                username = "member2"
                age = 10
            })
            memberRepository.save(Member().apply {
                username = "member3"
                age = 10
            })
            memberRepository.save(Member().apply {
                username = "member4"
                age = 10
            })
            memberRepository.save(Member().apply {
                username = "member5"
                age = 10
            })

            val age = 10
            val offset = 0
            val limit = 3

            val pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"))
            val memberPage = memberRepository.findByAge(age, pageRequest)

            assertThat(memberPage.content.size).isEqualTo(3)
            assertThat(memberPage.totalPages).isEqualTo(5)
        }
    }
}
