package com.seungdols.dev.datajpa.sample.domain

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
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
    @PersistenceContext
    val em: EntityManager,
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
//            assertThat(memberPage.totalPages).isEqualTo(5)
        }
    }
    @Test
    fun `bulkUpdate 테스트`() {
        memberRepository.save(Member().apply {
            username = "member1"
            age = 10
        })
        memberRepository.save(Member().apply {
            username = "member2"
            age = 19
        })
        memberRepository.save(Member().apply {
            username = "member3"
            age = 20
        })
        memberRepository.save(Member().apply {
            username = "member4"
            age = 21
        })
        memberRepository.save(Member().apply {
            username = "member5"
            age = 40
        })

        val resultCount = memberRepository.bulkAgePlus(20)
//        em.flush()
//        em.clear()

        assertThat(resultCount).isEqualTo(3)
    }

    @Test
    fun findMemberLazy() {
        val team1 = Team().apply {
            name = "teamA"
        }
        val team2 = Team().apply {
            name = "teamB"
        }
        teamRepository.save(team1)
        teamRepository.save(team2)
        val member1 = memberRepository.save(Member().apply {
            username = "member1"
            age = 10
            changeTeam(team1)
        })
        val member2 = memberRepository.save(Member().apply {
            username = "member2"
            age = 19
            changeTeam(team2)
        })

        em.flush()
        em.clear()

        val members = memberRepository.findMemberEntityGraph()

        for (member in members) {
            println("member = ${member.username}")
            println("member.team = ${member.team?.name}")
        }
    }

    @Test
    fun queryHint() {
        val member = Member().apply {
            username = "member1"
            age = 10
        }
        memberRepository.save(member)

        em.flush()
        em.clear()

        val findMember = memberRepository.findReadOnlyByUsername("member1")
        findMember.username = "member2"

        em.flush()

    }
    @Test
    fun queryLock() {
        val member = Member().apply {
            username = "member1"
            age = 10
        }
        memberRepository.save(member)

        em.flush()
        em.clear()

        val findMembers= memberRepository.findLockByUsername("member1")
    }

    @Test
    fun callCustom() {
        val member = Member().apply {
            username = "member1"
            age = 10
        }
        memberRepository.save(member)

        val result = memberRepository.findMemberCustom()
        for (member in result) {
            println("member = ${member}")
        }
    }

    @Test
    fun jpaEventBaseEntity() {
        val member = Member().apply {
            username = "member1"
            age = 10
        }

        memberRepository.save(member)

        member.username = "member2"

        em.flush()
        em.clear()

        val findMember = memberRepository.findById(member.id).get()

        println("findMember.createdDate = ${findMember.createdDate}")
        println("findMember.lastModifiedDate = ${findMember.lastModifiedDate}")
        println("findMember.createdBy = ${findMember.createdBy}")
        println("findMember.lastModifiedBy = ${findMember.lastModifiedBy}")
    }

    @Test
    fun specifications() {
        val temaA = Team().apply {
            name = "teamA"
        }
        em.persist(temaA)

        val member1 = Member().apply {
            username = "member1"
            age = 10
            team = temaA
        }

        val member2 = Member().apply {
            username = "member2"
            age = 20
            team = temaA
        }

        em.persist(member1)
        em.persist(member2)

        em.flush()
        em.clear()

        val memberSpecification = MemberSpec.username("member1").and(MemberSpec.teamName("teamA"))
        val members = memberRepository.findAll(memberSpecification)

        for (member in members) {
            println("member = ${member}")
        }

        assertThat(members.size).isEqualTo(1)
    }

    @Test
    fun queryByExample() {
        val temaA = Team().apply {
            name = "teamA"
        }
        em.persist(temaA)

        val member1 = Member().apply {
            username = "member1"
            age = 10
            team = temaA
        }

        val member2 = Member().apply {
            username = "member2"
            age = 20
            team = temaA
        }

        em.persist(member1)
        em.persist(member2)

        em.flush()
        em.clear()

       val member = Member().apply {
            username = "member1"
        }


        val example = Example.of(member,
        ExampleMatcher.matching()
            .withIgnorePaths("id")
            .withIgnorePaths("age")
            .withIgnorePaths("lastModifiedDate")
            .withIgnorePaths("createdDate")
            .withIgnorePaths("createdBy")
            .withIgnorePaths("lastModifiedBy")
        )
        val members = memberRepository.findAll(example)

        assertThat(members.size).isEqualTo(1)
    }

    @Test
    fun projections() {
        val temaA = Team().apply {
            name = "teamA"
        }
        em.persist(temaA)

        val member1 = Member().apply {
            username = "member1"
            age = 10
            team = temaA
        }

        val member2 = Member().apply {
            username = "member2"
            age = 20
            team = temaA
        }

        em.persist(member1)
        em.persist(member2)

        em.flush()
        em.clear()

        val usernameOnlyList = memberRepository.findProjectionsByUsername("member1", NestedClosedProjections::class.java)

        for (usernameOnly in usernameOnlyList) {
            println("usernameOnly = $usernameOnly")
        }
    }

    @Test
    fun nativeQuery() {
        val temaA = Team().apply {
            name = "teamA"
        }
        em.persist(temaA)

        val member1 = Member().apply {
            username = "member1"
            age = 10
            team = temaA
        }

        val member2 = Member().apply {
            username = "member2"
            age = 20
            team = temaA
        }

        em.persist(member1)
        em.persist(member2)

        em.flush()
        em.clear()

        val member = memberRepository.findByNativeQuery("member1")

        println("member = $member")
    }

    @Test
    fun nativeQueryPaging() {
        val temaA = Team().apply {
            name = "teamA"
        }
        em.persist(temaA)

        val member1 = Member().apply {
            username = "member1"
            age = 10
            team = temaA
        }

        val member2 = Member().apply {
            username = "member2"
            age = 20
            team = temaA
        }

        em.persist(member1)
        em.persist(member2)

        em.flush()
        em.clear()

        val memberProjectionPage = memberRepository.findByNativeProjection(PageRequest.of(0, 10))
        val content = memberProjectionPage.content

        for (member in content) {
            println("member = ${member.getId()}, ${member.getUsername()}, ${member.getTeamName()}")
        }
    }
}
