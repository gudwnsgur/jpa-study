package study.jpa.basic.jpashop.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * @author Joonhyuck Hyoung
 */
@Entity(name = "jpashop_category")
@Table(name = "jpashop_category")
public class Category {
    @Id
    @GeneratedValue
    private Long no;

    private String name;

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    // item과 다대다
    @ManyToMany
    @JoinTable(name = "CATEGORY_ITEM",
            joinColumns = @JoinColumn(name = "CATEGORY_ID"),
            inverseJoinColumns = @JoinColumn(name = "ITEM_ID")
    )
    private List<Item> items = new ArrayList<>();
}
