package sunshine.city.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

/**
 * 도시 정보를 나타내는 엔티티.
 */
@Entity
@Table(name = "city")
class City(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    @ManyToOne
    @JoinColumn(name = "country_id")
    val country: Country
)
