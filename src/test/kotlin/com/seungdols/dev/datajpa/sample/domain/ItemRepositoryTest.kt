package com.seungdols.dev.datajpa.sample.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor


@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class ItemRepositoryTest(
    private val itemRepository: ItemRepository
) {
    @Test
    fun save() {
        // Given
        val item = Item("A")

        // When
        val savedItem = itemRepository.save(item)

        // Then
        assertThat(savedItem.id).isEqualTo(item.id)
    }
}
