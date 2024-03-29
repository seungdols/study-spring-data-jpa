package com.seungdols.dev.datajpa.sample.domain

interface MemberProjection {

    fun getId(): Long
    fun getUsername(): String

    fun getTeamName(): String
}
