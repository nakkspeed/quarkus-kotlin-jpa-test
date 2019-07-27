package org.acme.rest

import io.quarkus.hibernate.orm.panache.PanacheEntity
import java.time.LocalDate
import javax.enterprise.context.ApplicationScoped
import javax.persistence.*
import javax.transaction.Transactional


@ApplicationScoped
class SantaClausService {

    @Transactional
    fun createGift(giftDescription: String): Gift {
        val gift = Gift(giftDescription, BusinessDate(LocalDate.now()))
        gift.persist()
        return gift
    }
}


@Entity
@Access(AccessType.FIELD)
class Gift(val name: String,
           @Embedded
           @AttributeOverride(name = "date", column = Column(name = "delivery_date"))
           val deliveryDate: BusinessDate) : PanacheEntity()

@Embeddable
@Access(AccessType.FIELD)
class BusinessDate(val date: LocalDate) {

    // Hibernate の AccessType.FIELD のスキャン対象外
    // JAX-RS は算出プロパティもスキャンする
    val year: Int
        get() = date.year

    val month: Int
        get() = date.monthValue
}
