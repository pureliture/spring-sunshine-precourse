package sunshine.city.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

/**
 * 국가 정보를 나타내는 엔티티.
 */
@Entity
@Table(name = "country")
class Country(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val name: String
) {
    protected constructor() : this(name = "")
}
