package jpabook.jpashop.api

import jpabook.jpashop.domain.*
import jpabook.jpashop.repository.*
import org.springframework.web.bind.annotation.*
import java.time.*

/**
 * 앞의 예제에서는 XtoOne(OneToOne, ManyToOne) 관계만 있었다.
 *
 * 주문내역에서 추가로 주문한 상품 정보를 추가로 조회해야 함
 * Order 기준으로 컬렉션인 OrderItem 와 Item 이 필요
 * 이번에는 컬렉션인 일대다 관계 (OneToMany)를 조회하고, 최적화하는 방법에 대한 내용
 */

/**
 * 간략 설명!!
 *  V1. 엔티티 직접 노출
 *     - 엔티티가 변하면 스펙이 바뀜 / 트랜잭션 안에서 지연로딩이 필요 / 양방향 연관관계 문제
 *  V2. 엔티티를 조회해서 DTO로 변환(fetch join 사용X)
 *     - 트랜잭션 안에서 지연로딩이 필요
 *  V3. 엔티티를 조회해서 DTO로 변환(fetch join 사용O)
 *     - 페이징 시에는 N 부분을 포기해야함(방법은 있다? V3.1)
 *  V4. JPA에서 DTO로 바로 조회, 컬렉션 N 조회 (1 + N Query)
 *     - 페이징 가능
 *  V5. JPA에서 DTO로 바로 조회, 컬렉션 1 조회 최적화 버전 (1 + 1 Query)
 *     - 페이징 가능
 */

@RestController
class OrderApiController(
    val orderRepository: OrderRepository,
    val orderQueryRepository: OrderQueryRepository,
) {
    /**
     * V1. 엔티티 직접 노출
     * 양방향 연관관계라면 json을 만들어낼 때 무한루프에 빠질 수 있다 -> 한쪽에 @JsonIgnore 추가 필요
     * 엔티티를 직접 노출하므로 좋은 방식이 아니다.
     */
    @GetMapping("/api/v1/orders")
    fun ordersV1(): List<Order> {
        return orderRepository.findAllByString().onEach { order ->
            order.member.name // Lazy Loading 강제 초기화
            order.delivery.address // Lazy Loading 강제 초기화

            order.orderItems.forEach { it.item.name } // Lazy Loading 강제 초기화
        }
    }

    /**
     * V2. 엔티티를 조회해서 DTO로 변환 (fetch join 사용X)
     * SQL 실행 횟수
     *   - order 1번 (result = N개)
     *   - member, delivery N번 : order.member.X, order.delivery.X Lazy 강제 초기화시 조회
     *   - orderItem N번 : order.orderItem.X 강제 초기화시 조회
     *   - item M번 : orderItem.item.X 강제 초기화시 조회
     * 너무 많은 SQL 실행문
     * > 참고: 지연 로딩은 영속성 컨텍스트에 있으면 영속성 컨텍스트에 있는 엔티티를 사용하고 없으면 SQL을 실행한다.
     *        따라서 같은 영속성 컨텍스트에서 이미 로딩한 회원 엔티티를 추가로 조회하면 SQL을 실행하지 않는다.
     */
    @GetMapping("/api/v2/orders")
    fun ordersV2(): List<OrderDto> {
        return orderRepository.findAllByString().map { order ->
            OrderDto.valueOf(order)
        }
    }

    /**
     * V3. 엔티티를 조회해서 DTO로 변환(fetch join 사용O)
     * fetch 조인으로 1번만 실행
     * 단점 :
     *    - 페이징 불가능
     *       - Order 기준 페이징 불가능, OrderItem 기준이 되어버림 (더 자세한 내용은 페치 조인 한계 참조)
     *    - 컬렉션을 Fetch join 하면 일대다 조인이 발생하므로 데이터가 엄청 증가
     *    - 1:N에서 1을 페이징 하는 것이 목적인데 데이터가 N 기준으로 row가 생성
     */
    @GetMapping("/api/v3/orders")
    fun ordersV3(): List<OrderDto> {
        return orderRepository.findAllWithItem().map { order ->
            OrderDto.valueOf(order)
        }
    }

    /**
     * V3.1. 엔티티를 조회해서 DTO로 변환 - 페이징과 한계 돌파
     *
     * 1. xToOne 관계를 모두 fetch join
     *    - 어차피 xToOne 관계는 row수를 증가시키지 않기 때문에 상관 없음
     *    - ex) Order에 해당하는 Memeber는 어차피 하나니가 로우가 안늘어남
     * 2. 컬렉션을 지연로딩으로 조회
     *    - 여기까지만 하면 성능 안좋음
     *        이유 : order N개면
     *        -> order 1개 조회 (1) +  N개에 해당하는 orderItem들 지연로딩 조회 (N) + OrderItems들에 해당하는 Itmes들 지연로딩으로 조회(N*M)
     *        -> 1 + N + N*M (미친쿼리량)
     * 3. 지연로딩 성능 최적화를 위해
     *    1. hibernate.default_batch_fetch_size: 글로벌 설정 (보통 100~1000 권장)
     *    2. @BatchSize: 개별 최적화 (컬렉션 : 컬렉션 필드에 / 엔티티 : 엔티티 클래스에)
     *    - 이러면 컬렉션/프록시 객체를 한번에 설정한 size 만큼 IN 쿼리로 조회
     *    - 성능은?
     *       - XtoOne fetchJoin으로 한번에(Order + Member + delivery) => 쿼리 1번
     *       - orderItems가 지연로딩이라 하나씩 가져올것같지만 BatchSize를 사용 => In( ) Query로 1번
     *       - items도 지연로딩이지만 BatchSize 사용 => In( ) Query로 1번
     *       - 1+1+1이 되는 개미친성능
     *
     * 장점
     *    1. 쿼리 호출 수 감소 (1+N => 1+1)
     *    2. 조인보다 DB 데이터 전송량 최적화
     *    3. 페치 조인 방식과 비교해 쿼리 호출수가 증가하지만 DB 데이터 전송량이 감소
     *    4. 페이징 가능
     *    가장 큰 장점) 영한 피셜 : 저는 이 방법을 개인적으로 굉장히 선호합니다.
     * 단점
     *    - 사실 빠른건 V3이 빠르다(쿼리 한번이니까)
     *    - 근데 페이징도 못하고 디비에서 조회하는 데이터도 뻥튀기라 사용하기도 복잡하니까 V3.1 강추
     */
    @GetMapping("/api/v3.1/orders")
    fun ordersV3Page(
        @RequestParam offset: Int = 0,
        @RequestParam limit: Int = 0,
    ): List<OrderDto> {
        return orderRepository.findAllWithMemberDelivery(offset, limit).map { order ->
            OrderDto.valueOf(order)
        }
    }

    /**
     * V4. JPA에서 DTO 직접 조회
     *
     * - Query: 루트 1번, 컬렉션 N 번 실행
     * - ToOne(N:1, 1:1) 관계들을 먼저 조회하고, ToMany(1:N) 관계는 각각 별도로 처리
     *    - ToOne 관계는 조인해도 데이터 row 수가 증가하지 않는다.
     *    - ToMany(1:N) 관계는 조인하면 row 수가 증가한다
     * - row 수가 증가하지 않는 ToOne 관계는 조인으로 최적화 하기 쉬우므로 한번에 조회
     * - ToMany 관계는 최적화 하기 어려우므로 findOrderItems() 같은 별도의 메서드로 조회
     * - 결국엔 N+1
     */
    @GetMapping("/api/v4//orders")
    fun ordersV4(): List<OrderQueryDto> = orderQueryRepository.findOrderQueryDtos()

    /**
     * V5. JPA에서 DTO 직접 조회 - 컬렉션 조회 최적화
     *
     * Query : root 1, collection 1
     * - ToOne 관계들을 먼저 조회하고, 여기서 얻은 식별자 orderId로 ToMany 관계인 OrderItem 을 한꺼번에 조회
     * - map을 사용해서 매칭 성능 향상(O(1))
     */
    @GetMapping("/api/v5/orders")
    fun orderV5(): List<OrderQueryDto> {
        return orderQueryRepository.findAllByDto_optimization()
    }
}

data class OrderDto(
    val orderId: Long?,
    val name: String?,
    val orderDate: LocalDateTime?,
    val orderStatus: OrderStatus?,
    val address: Address?,
    val orderItems: List<OrderItemDto> = emptyList(),
) {
    companion object {
        fun valueOf(order: Order) = OrderDto(
            orderId = order.id,
            name = order.member.name, // Lazy Loading 강제 초기화
            orderDate = order.orderDate,
            orderStatus = order.status,
            address = order.delivery.address, // Lazy Loading 강제 초기화
            orderItems = order.orderItems.map { OrderItemDto.valueOf(it) } // Lazy Loading 강제 초기화
        )
    }
}

data class OrderItemDto(
    val itemName: String?,
    val orderPrice: Int?,
    val count: Int?,
) {
    companion object {
        fun valueOf(orderItem: OrderItem) = OrderItemDto(
            itemName = orderItem.item.name, // Lazy Loading 강제 초기화
            orderPrice = orderItem.orderPrice,
            count = orderItem.count
        )
    }
}

/**
 * API 개발 고급 정리
 *
 * 정리
 * - 엔티티 조회
 *    - 엔티티를 조회해서 그대로 반환: V1 => 큰일남
 *    - 엔티티 조회 후 DTO로 변환: V2
 *    - 페치 조인으로 쿼리 수 최적화: V3 => 패치조인을 사용하면 한번에 가져올 수 있다.
 *    - 컬렉션 페이징과 한계 돌파: V3.1 => 패치조인을 반만 하고(xToOne) 나머지는 최적화해서 쿼리 줄이자
 *       - 컬렉션은 페치 조인시 페이징이 불가능
 *       - ToOne 관계는 페치 조인으로 쿼리 수 최적화
 *       - 컬렉션은 페치 조인 대신에 지연 로딩을 유지하고, hibernate.default_batch_fetch_size, @BatchSize 로 최적화
 * - 여기서 잠깐!!!!!!
 * - 여기까지 했는데도 성능 안나오면 DTO를 고민해야하는건 맞는데... 솔직히 이단계까지 와도 느리면 캐싱을 할 생각을 해야지...
 * - 단) 엔티티를 캐시에 넣지는 말자. DTO를 캐시하자. 엔티티에서 관리하는데 이걸 두군데서 관리하는건 위험함
 * -    jpa 2차캐시 기능도 있는데 사용하기 복잡함 보통 로컬캐시 이런거 쓰자
 *
 * - DTO 직접 조회
 *    - JPA에서 DTO를 직접 조회: V4
 *    - 컬렉션 조회 최적화 - 일대다 관계인 컬렉션은 IN 절을 활용해서 메모리에 미리 조회해서 최적화: V5
 *
 * 권장 순서
 *    1. 엔티티 조회 방식으로 우선 접근 (v2)
 *       1. 페치조인으로 쿼리 수를 최적화 (v3)
 *       2. 컬렉션 최적화
 *          1. 페이징 필요 hibernate.default_batch_fetch_size , @BatchSize 로 최적화 (v3.1)
 *          2. 페이징 필요X 페치 조인 사용 (v3)
 *   2. 엔티티 조회 방식으로 해결이 안되면 DTO 조회 방식 사용 (v4, v5)
 *   3. DTO 조회 방식으로 해결이 안되면 NativeSQL or 스프링 JdbcTemplate
 *
 *
 *   *** 영한피셜 ***
 *   - 엔티티 조회 방식에 대해
 *      - 보통 페치조인(v3)이나 default_batch_fetch_size, @BatchSize(v3.1)같이 코드 수정이 거의 없다.
 *      - 옵션만 약간 변경해서 다양한 성능 최적화 가능
 *      - JPA가 많은 부분을 최적화해주기 때문
 *   - DTO 조회 방식
 *      - 많은 코드를 변경해야 한다.
 *      - SQL을 직접 다루는것과 유사
 *
 *   - 영한띠는 은근히 v3, v3.1을 강조하는 듯한 느낌이다.
 *   - querydsl에 가면 어떨지....
 */
