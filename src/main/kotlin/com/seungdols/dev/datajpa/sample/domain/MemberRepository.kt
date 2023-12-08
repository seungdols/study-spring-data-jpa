package com.seungdols.dev.datajpa.sample.domain

import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
}
