package jpabook.jpashop.service

import jpabook.jpashop.domain.item.*
import jpabook.jpashop.repository.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author Joonhyuck Hyoung
 */
@Service
@Transactional(readOnly = true)
class ItemService(
    private val itemRepository: ItemRepository,
) {
    @Transactional
    fun saveItem(item: Item) {
        itemRepository.save(item)
    }

    fun findItems() = itemRepository.findAll()

    fun findOne(itemId: Long) = itemRepository.findOne(itemId)
}
