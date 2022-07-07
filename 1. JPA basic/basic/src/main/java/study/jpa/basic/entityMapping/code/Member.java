package study.jpa.basic.entityMapping.code;

import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.*;

/**
 * @author Joonhyuck Hyoung
 */
@Entity(name = "entity_mapping_member")
@Table(name = "entity_mapping_member")
public class Member {
    // pk 매핑
    @Id
    // 관계형 DB를 쓰면 보통 auto_increment 를 쓴다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // DB에 저장하고 싶은 이름
    @Column(name = "name")
    private String userName;

    // Integer에 가장 적절한 db 타입으로 만들어진다(number)
    // insertable : 이 컬럼을 등록 가능하게 할것인가? (default = true)
    // updatable : 이 컬럼을 변경 가능하게 할것인가? (default = true)
    @Column(insertable = true, updatable = false)
    // null 값을 허용할것이다!
    @Nullable
    private Integer age;

    // 해당 타입(USER or ADMIN)이 스트링으로 db에 저장
    // EnumType.ORDINAL 절대 사용하지 마라 : enum 순서를 db에 저장
    // EnumType.STRING : enum 이름을 디비에 저장
    @Enumerated(EnumType.STRING)
    @Nullable
    // 유니크 키를 만들어준다.
    // 근데 이름이 이상하게 나와서 운영에서는 유니크 키를 그냥 디비 만들때 만든다. 복합키도 불가능하다.
    @Column(unique = true)
    private RoleType roleType;

    // 날짜 매핑
    private LocalDate createdAt;

    private LocalDateTime modifiedAt;

    // DB에 varchar를 넘는 큰 데이터를 넣고 싶으면 사용하라
    // 문자면 clob, byteArray면 blob으로 매핑된다.
    @Lob
    // 컬럼의 definition을 내가 설정하면 ddl에 그대로 들어간다
    @Column(columnDefinition = "varchar(100) default 'empty'")
    private String description;

    // 나는 이거 디비에서는 안쓸거야~
    @Transient
    private int temp;

    public Member(){}

    public Member(Long id, String userName, Integer age, RoleType roleType, LocalDate createdAt, LocalDateTime modifiedAt,
                  String description) {
        this.id = id;
        this.userName = userName;
        this.age = age;
        this.roleType = roleType;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Member create(
        String userName, Integer age, RoleType roleType, LocalDate createdAt, LocalDateTime modifiedAt, String description
    ) {
        Member member = new Member();
        member.userName = userName;
        member.age = age;
        member.roleType = roleType;
        member.createdAt = createdAt;
        member.modifiedAt = modifiedAt;
        member.description = description;
        return member;
    }
}

