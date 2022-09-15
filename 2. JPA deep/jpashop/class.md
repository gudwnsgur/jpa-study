## Member
```java
@Entity
@Getter @Setter
public class Member {
 
     @Id @GeneratedValue
     @Column(name = "member_id")
     private Long id;
     
     private String name;
     @Embedded
     
     private Address address;
     
     @OneToMany(mappedBy = "member")
     private List<Order> orders = new ArrayList<>();
}
```

## Order
```java
@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {
     @Id @GeneratedValue
     @Column(name = "order_id")
     private Long id;
     
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "member_id")
     private Member member; //주문 회원
    
     @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
     private List<OrderItem> orderItems = new ArrayList<>();
    
     @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
     @JoinColumn(name = "delivery_id")
     private Delivery delivery; //배송정보
    
     private LocalDateTime orderDate; //주문시간
    
     @Enumerated(EnumType.STRING)
     private OrderStatus status; //주문상태 [ORDER, CANCEL]
    
    
     //==연관관계 메서드==//
     public void setMember(Member member) {
         this.member = member;
         member.getOrders().add(this);
     }
     
     public void addOrderItem(OrderItem orderItem) {
         orderItems.add(orderItem);
         orderItem.setOrder(this);
     }
    
     public void setDelivery(Delivery delivery) {
         this.delivery = delivery;
         delivery.setOrder(this);
     }
}
```

## Delivery
```java
@Entity
@Getter @Setter
public class Delivery {
     @Id @GeneratedValue
     @Column(name = "delivery_id")
     private Long id;
    
     @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
     private Order order;
    
     @Embedded
     private Address address;
    
     @Enumerated(EnumType.STRING)
     private DeliveryStatus status; //ENUM [READY(준비), COMP(배송)]
}
```

## Address
```java
@Embeddable
@Getter
public class Address {
     private String city;
     private String street;
     private String zipcode;
     
     protected Address() {
     }
     
     public Address(String city, String street, String zipcode) {
         this.city = city;
         this.street = street;
         this.zipcode = zipcode;
    }  
}
```

## OrderItem
```java
@Entity
@Table(name = "order_item")
@Getter @Setter
public class OrderItem {
     @Id @GeneratedValue
     @Column(name = "order_item_id")
     private Long id;
  
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "item_id")
     private Item item; //주문 상품
   
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "order_id")
     private Order order; //주문
   
     private int orderPrice; //주문 가격
   
     private int count; //주문 수량
}
```

## Item
```java
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {
     @Id @GeneratedValue
     @Column(name = "item_id")
     private Long id;
    
     private String name;
    
     private int price;
    
     private int stockQuantity;
     
     @ManyToMany(mappedBy = "items")
     private List<Category> categories = new ArrayList<Category>();
}
```

## Item - Book, Movie, Album
```java
@Entity
@DiscriminatorValue("B")
@Getter @Setter
public class Book extends Item {
     private String author;
     private String isbn;
}

@Entity
@DiscriminatorValue("A")
@Getter @Setter
public class Album extends Item {
    private String artist;
    private String etc;
}

@Entity
@DiscriminatorValue("M")
@Getter @Setter
public class Movie extends Item {
    private String director;
    private String actor;
}
```

## Category
```java
@Entity
@Getter @Setter
public class Category {
     @Id @GeneratedValue
     @Column(name = "category_id")
     private Long id;
     
     private String name;
     
     @ManyToMany
     @JoinTable(name = "category_item",
     joinColumns = @JoinColumn(name = "category_id"),
     inverseJoinColumns = @JoinColumn(name = "item_id"))
     private List<Item> items = new ArrayList<>();
    
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "parent_id")
     private Category parent;
    
     @OneToMany(mappedBy = "parent")
     private List<Category> child = new ArrayList<>();
     
     
     //==연관관계 메서드==//
     public void addChildCategory(Category child) {
         this.child.add(child);
         child.setParent(this);
     }
}
```
