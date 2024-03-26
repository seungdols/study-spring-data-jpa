package com.seungdols.dev.datajpa.sample.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.NamedEntityGraph
import jakarta.persistence.NamedQuery
import jakarta.persistence.OneToMany

@Entity
@NamedQuery(
    name = "Member.findByUsername",
    query = "select m from Member m where m.username = :username"
)
@NamedEntityGraph(name = "Member.all", attributeNodes = [jakarta.persistence.NamedAttributeNode("team")])
class Member(
    @Id @GeneratedValue
    @Column(name = "member_id")
    val id: Long = 0
) : BaseEntity() {
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
