package jpabook.jpashop.api

import jpabook.jpashop.domain.*
import jpabook.jpashop.repository.*
import org.springframework.web.bind.annotation.*
import java.time.*

/**
 * 이번 장에서는 xToOne 관계의 최적화만을 설명!!
 *
 * xToOne(ManyToOne, OneToOne) 관계를 최적화
 * Order
 * Order -> Member (N:1)
 * Order -> Delivery (1:1)
 *
 */
@RestController
class OrderSimpleApiController(
    private val orderRepository: OrderRepository,
    private val orderSimpleQueryRepository: OrderSimpleQueryRepository,
) {
    /**
     * V1. 엔티티 직접 노출
     * - Hibernate5Module 모듈 등록 or LAZY=null 처리
     * - 양방향 관계 문제 발생 -> @JsonIgnore
     * - order -> member, order -> address는 지연로딩이기 때문에 실제 엔티티 대신 프록시가 존재한다.
     * - jackson이 이 프록시 객체를 json으로 생성하는 법을 몰라서 예외가 발생할 수 있다.
     */
    @GetMapping("/api/v1/simple-orders")
    fun ordersV1(): List<Order> {
        //        return orderRepository.findAllByString()
        /**
         * 첫 번째 문제 발생!! 무한루프
         * order -> member -> order -> member -> order -> member -> ....
         * 객체를 json으로 만드는 jackson 입장에서는 잘 몰라서 계속 만든다.
         * 따라서 둘 중 한곳은 @jsonIgnore로 끊어줘야 한다.
         */

        /**
         * 두번째 문제에 발생!! Type definition Error
         * order -> member, order -> address는 지연로딩이기 때문에 실제 엔티티 대신 `프록시`로 존재한다.
         * 해결방법1) jackson아 그냥 이거 뿌리지마 : Hibernate5Module
         *    - 프록시인 객체들은 다 null로 뿌려준다.
         *    - 물론 이 모듈에서 프록시들의 객체를 다 조회해서 뿌려주도록 설정할 수 있다.(대신 전부다 가져옴...)
         * 해결방법2) LAZY 강제 초기화 (프록시 to 찐객체)
         *    - order.member 까지만 선언하면 프록시를 가져온다.
         *    - order.member.name 까지 작성하면 진짜 객체를 가져올 수 밖에 없으니까 이렇게 강제 초기화해버린다.
         */
        return orderRepository.findAllByString().onEach { order ->
            // LAZY 강제 초기화
            order.member.name
            order.delivery.address
        }
    }

    /**
     * V2. 엔티티를 조회해서 DTO로 변환 (fetch join 사용 안하는 경우)
     * - 엔티티를 DTO로 변환하는 일반적인 방법
     * - 단점: 지연로딩으로 쿼리 O(1+N+N)번 호출
     * - 1 : order 조회
     * - N : order -> member 지연로딩 조회
     * - N : order -> delivery 지연로딩 조회
     * BUT!!! 지연로딩은 영속성 컨텍스트를 먼저 조회하므로 이미 조회된 경우 쿼리가 생략된다!
     */
    @GetMapping("/api/v2/simple-orders")
    fun ordersV2(): List<SimpleOrderDto> {
        /**
         * order 2개라고 가정
         *
         * a - order 조회(1) => N == 2
         * b - member 조회(1/2) => 위의 결과중 1번을 조회
         * c - delivery 조회(1/2) => 위의 결과중 1번을 조회
         * d - member 조회(2/2) => 위의 결과중 2번을 조회
         * e - delivery 조회(2/2) => 위의 결과중 2번을 조회
         *
         * 총 5번을 조회한다. (1+2+2)
         * 만약 1번과 2번의 멤버가 같으면 4번 실행됨 (b에서 멤버가 영속성 컨텍스트에 들어갔기 때문)
         */
        return orderRepository.findAllByString().map { order ->
            SimpleOrderDto.valueOf(order)
        }
    }

    /**
     * V3. 엔티티를 DTO로 변환 + Fetch join
     */
    @GetMapping("/api/v3/simple-orders")
    fun ordersV3() = orderRepository.findAllWithItem().map {
        SimpleOrderDto.valueOf(it)
    }

    /**
     * V4. JPA에서 DTO로 바로 조회
     * - 쿼리 한번 호출
     * - select 절에서 원하는 데이터만 선택해서 조회하도록 V3를 좀 더 최적화해보자
     * - JPQL 결과를 DTO로 즉시 변환
     * - 장점 : SELECT 절에서 원하는 데이터를 직접 선택하므로 DB 애플리케이션 네트웍 용량 최적화 (근데 이건 생각보다 큰 장점 아님)
     * - 단점 : repository 재사용성 떨어짐, API 스펙 코드가 repository 에서 사용되고 있음
     */
    @GetMapping("/api/v4/simple-orders")
    fun ordersV4() = orderSimpleQueryRepository.findOrders()
}

data class SimpleOrderDto(
    val orderId: Long,
    val name: String,
    val orderDate: LocalDateTime,
    val orderStatus: OrderStatus,
    val address: Address?,
) {
    companion object {
        fun valueOf(order: Order) = SimpleOrderDto(
            orderId = order.id,
            name = order.member.name, // LAZY 초기화
            orderDate = order.orderDate,
            orderStatus = order.status,
            address = order.delivery.address // LAZY 초기화
        )
    }
}

/**
 * 정리
 * 엔티티를 DTO로 변환하거나, DTO로 바로 조회하는 두가지 방법은 각각 장단점이 있다. 둘중 상황에
 * 따라서 더 나은 방법을 선택하면 된다. 엔티티로 조회하면 리포지토리 재사용성도 좋고, 개발도 단순해진다.
 * 따라서 권장하는 방법은 다음과 같다.
 *
 * 1. 우선 엔티티를 DTO로 변환하는 방법 선택 (V2)
 * 2. 필요하면 페치 조인으로 성능 최적화. 대부분의 성능 이슈가 해결된다. (V3)
 * 3. 그래도 성능이 잘 안나면 DTO로 직접 조회하는 방법을 사용 (V4)
 * 4. 최후의 방법 : [JPA native SQL] or [스프링 JDBC Template으로 SQL문 작성] (거의 없음)
 */
