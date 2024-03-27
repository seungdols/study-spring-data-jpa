package com.seungdols.dev.datajpa.sample.domain

import jakarta.persistence.criteria.Join
import jakarta.persistence.criteria.JoinType
import org.springframework.data.jpa.domain.Specification

class MemberSpec {
    companion object {
        fun teamName(teamName: String): Specification<Member> {
            return Specification<Member> { root, query, criteriaBuilder ->

                if (teamName.isEmpty()){
                    return@Specification null
                }

                val teamJoin = root.join<Member, Team>("team", JoinType.INNER) as Join<Member, Team>
                criteriaBuilder.equal(teamJoin.get<String>("name"), teamName)
            }
        }

        fun username(username: String): Specification<Member> {
            return Specification<Member> { root, query, criteriaBuilder ->
                criteriaBuilder.equal(root.get<String>("username"), username)
            }
        }
    }
}
