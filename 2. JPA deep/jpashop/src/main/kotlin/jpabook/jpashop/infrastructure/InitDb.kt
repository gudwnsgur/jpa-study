package jpabook.jpashop.infrastructure

import org.springframework.stereotype.*
import javax.annotation.*

/**
 * @author Joonhyuck Hyoung
 */
@Component
class InitDb(
    private val initService: InitService,
) {
    @PostConstruct
    fun init() {
        initService.dbInit1()
        initService.dbInit2()
    }
}
