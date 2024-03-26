package com.seungdols.dev.datajpa.sample.domain

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import java.time.LocalDateTime

@MappedSuperclass
open class JpaBaseEntity {
    @Column(updatable = false)
    var createdDate: LocalDateTime = LocalDateTime.now()
    var updatedDate: LocalDateTime = LocalDateTime.now()

    @PrePersist
    fun prePersist() {
        val now = LocalDateTime.now()
        createdDate = now
        updatedDate = now
    }

    @PreUpdate
    fun preUpdate() {
        updatedDate = LocalDateTime.now()
    }
}
