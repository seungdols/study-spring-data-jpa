package com.seungdols.dev.datajpa.sample.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity
class Team(
    @Id @GeneratedValue
    @Column(name = "team_id")
    val id: Long = 0
) {
    var name: String = ""

    @OneToMany(mappedBy = "team")
    var members: MutableList<Member> = mutableListOf()
}
