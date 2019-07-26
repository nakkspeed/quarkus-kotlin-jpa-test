package org.acme.rest

import io.quarkus.hibernate.orm.panache.PanacheEntity
import javax.enterprise.context.ApplicationScoped
import javax.persistence.Access
import javax.persistence.AccessType
import javax.persistence.Entity
import javax.transaction.Transactional


@ApplicationScoped
class SantaClausService {

    @Transactional
    fun createGift(giftDescription: String): Gift {
        val gift = Gift(giftDescription)
        gift.persist()
        return gift
    }
}


@Entity
@Access(AccessType.FIELD)
class Gift(val name: String) : PanacheEntity()