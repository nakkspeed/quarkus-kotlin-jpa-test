package org.acme.rest

import io.quarkus.hibernate.orm.panache.PanacheEntity
import javax.enterprise.context.ApplicationScoped
import javax.persistence.*
import javax.transaction.Transactional

@ApplicationScoped
class NestedEntityService {


    @Transactional
    fun createTreeEntity(): Parent {
        val parent = Parent("joe")
        parent.addChild(Child("boo"))
        parent.addChild(Child("foo"))
        parent.addChild(Child("noo"))
        parent.persist()
        return parent
    }
}


/**
 * CascadeType.ALL で子エンティティもCRUDライフサイクルに巻き込む.
 * 集約内のエンティティは一律これでOK.
 */
@Entity
@Access(AccessType.FIELD)
class Parent(val name: String) : PanacheEntity() {
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    private val children: MutableSet<Child> = mutableSetOf()

    fun addChild(child: Child) {
        children.add(child)
    }

    val allChildren: Set<Child>
        get() = children.toSet()
}

@Entity
@Access(AccessType.FIELD)
class Child(val name: String) : PanacheEntity()
