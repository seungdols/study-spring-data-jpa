package com.seungdols.dev.datajpa.hello

import com.seungdols.dev.datajpa.sample.domain.Member
import com.seungdols.dev.datajpa.sample.domain.MemberRepository
import jakarta.annotation.PostConstruct
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController(
    private val memberRepository: MemberRepository
) {

    @PostConstruct
    fun init() {
        for (i in 0..100) {
            memberRepository.save(Member().apply {
                username = "user$i"
                age = i
            })
        }
    }

    @GetMapping("/members/{id}")
    fun findMember(@PathVariable id: Long): String = memberRepository.findById(id).get().username

    @GetMapping("/members")
    fun list(@PageableDefault(size = 10) pageable: Pageable): Page<Member> = memberRepository.findAll(pageable)
}
