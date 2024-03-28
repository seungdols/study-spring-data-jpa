package com.seungdols.dev.datajpa.sample.domain

interface NestedClosedProjections {
    fun getUsername(): String // 최적화 O
    fun getTeam(): TeamInfo // 중첩 구조에서는 하나 이상부터는 최적화 X

    interface TeamInfo {
        fun getName(): String
    }
}
