package com.seungdols.dev.datajpa.sample.domain

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
open class BaseEntity {
    @CreatedDate
    @Column(updatable = false)
    var createdDate: LocalDateTime = LocalDateTime.now()
    @LastModifiedDate
    var lastModifiedDate: LocalDateTime = LocalDateTime.now()

    @CreatedBy
    @Column(updatable = false)
    var createdBy: String = ""

    @LastModifiedBy
    var lastModifiedBy: String = ""
}
