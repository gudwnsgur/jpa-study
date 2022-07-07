package study.jpa.basic.entityMapping.code

import java.time.*

/**
 * @author Joonhyuck Hyoung
 */

data class KotlinMember(
    val id: Long,
    val name: String,
    val age: Int,
    val roleType: RoleType,
    val createdAt: LocalDate,
    val updateAt: LocalDateTime,
    val description: String,
) {
    // no need getter, setter
    companion object {
        fun create(
            id: Long, name: String, age: Int, roleType: RoleType, createdAt: LocalDate, updateAt: LocalDateTime, description: String
        ): KotlinMember {
            return KotlinMember(
                id = id,
                name = name,
                age = age,
                roleType = roleType,
                createdAt = createdAt,
                updateAt = updateAt,
                description = description,
            )
        }
    }
}
