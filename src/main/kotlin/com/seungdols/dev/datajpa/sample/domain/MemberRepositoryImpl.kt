package com.seungdols.dev.datajpa.sample.domain

import jakarta.persistence.EntityManager

class MemberRepositoryImpl(
    val em: EntityManager
) : MemberRepositoryCustom {
    override fun findMemberCustom(): List<Member> {
        return em.createQuery("select m from Member m", Member::class.java)
            .resultList
    }
}
