package com.seungdols.dev.datajpa.sample.domain

import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.domain.Persistable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@Entity
class Item(
    @Id
    private var id: String? = null
) : Persistable<String> {
    @CreatedDate
    var createdDate: LocalDateTime? = null
    override fun getId(): String? {
       return this.id
    }

    override fun isNew(): Boolean {
        return this.createdDate == null
    }
}
