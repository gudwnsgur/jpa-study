package jpabook.jpashop.domain

import jpabook.jpashop.domain.item.*
import javax.persistence.*

@Entity
class Category(
    @Id @GeneratedValue
    @Column(name = "category_id")
    val id: Long,

    val name: String,

    /**
     * @ManyToMany
     * category_item에 컬럼을 추가할 수 없고, 세밀한 쿼리를 실행하기 어려우니까 실무에서 사용하지 말자
     * 중간 엔티티(CategoryItem)를 만들고 @ManyToOne, @OneToMany로 매핑해서 사용하자
     */
    @ManyToMany
    @JoinTable(
        name = "category_item",
        joinColumns = [JoinColumn(name = "category_id")],
        inverseJoinColumns = [JoinColumn(name = "item_id")]
    )
    val items: MutableList<Item> = arrayListOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    var parent: Category,

    @OneToMany(mappedBy = "parent")
    val child: MutableList<Category> = arrayListOf(),
) {
    //==연관관계 메서드==//
    fun addChildCategory(child: Category) {
        this.child.add(child)
        child.parent = this
    }
}
