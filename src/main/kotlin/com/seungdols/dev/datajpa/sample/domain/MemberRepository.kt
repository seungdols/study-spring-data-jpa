package com.seungdols.dev.datajpa.sample.domain

import com.seungdols.dev.datajpa.sample.dto.MemberDto
import com.seungdols.dev.datajpa.sample.dto.UsernameOnlyDto
import jakarta.persistence.LockModeType
import jakarta.persistence.QueryHint
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.QueryHints
import org.springframework.data.repository.query.Param

interface MemberRepository : JpaRepository<Member, Long>, MemberRepositoryCustom, JpaSpecificationExecutor<Member> {
    fun findByUsernameAndAgeGreaterThan(username: String, age: Int): List<Member>

    @Query(name = "Member.findByUsername")
    fun findByUsername(@Param("username") username: String): List<Member>

    @Query("select m from Member m where m.username = :username and m.age = :age")
    fun findUser(@Param("username") username: String, @Param("age") age: Int): List<Member>

    @Query("select m.username from Member m")
    fun findUsernames(): List<String>

    @Query("select new com.seungdols.dev.datajpa.sample.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    fun findMemberDto(): List<MemberDto>

    @Query("select m from Member m where m.username in :names")
    fun findByNames(@Param("names") names: List<String>): List<Member>

    fun findListByUsername(username: String): List<Member> // collection
    fun findMemberByUsername(username: String): Member // single
    fun findOptionalByUsername(username: String): Member? // nullable
    fun findByAge(age: Int, pageable: Pageable): Slice<Member>

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    fun bulkAgePlus(@Param("age") age: Int): Int

    @Query("select m from Member m left join fetch m.team")
    fun findMemberFetchJoin(): List<Member>

    @EntityGraph(attributePaths = ["team"])
    override fun findAll(): List<Member>

    @Query("select m from Member m")
    @EntityGraph(attributePaths = ["team"])
    fun findMemberEntityGraph(): List<Member>

    @EntityGraph(attributePaths = ["team"])
//    @EntityGraph("Member.all")
    fun findEntityGraphByUsername(@Param("username") username: String): List<Member>

    @QueryHints(value = [QueryHint(name = "org.hibernate.readOnly", value = "true")])
    fun findReadOnlyByUsername(username: String): Member

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findLockByUsername(username: String): List<Member>

    fun findProjectionsByUsername(@Param("username") username: String): List<UsernameOnlyDto>
}
