package com.study.querydsl.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author Joonhyuck Hyoung
 */
@RestController
class HelloController {
    @GetMapping("hello")
    fun hello() = "hello"
}
