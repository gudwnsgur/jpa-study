package jpabook.jpashop.exception

/**
 * @author Joonhyuck Hyoung
 */
class NotEnoughStockException(override val message: String? = ""): RuntimeException(message)
