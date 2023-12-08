package com.seungdols.dev.datajpa.sample.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity
class Member(
    @Id @GeneratedValue
    @Column(name = "member_id")
    val id: Long = 0
) {
    var username: String = ""
    var age: Int = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    var team: Team? = null

    fun changeTeam(team: Team) {
        this.team = team
        team.members.add(this)
    }
}
