package org.acme.rest

import io.quarkus.hibernate.orm.panache.PanacheEntity
import io.quarkus.hibernate.orm.panache.PanacheRepository
import io.quarkus.panache.common.Parameters
import io.quarkus.panache.common.Sort
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.context.Dependent
import javax.inject.Inject
import javax.persistence.*
import javax.transaction.Transactional

@ApplicationScoped
class NestedEntityService {

    @Inject
    var repository: ParentRepository? = null

    @Transactional
    fun createTreeEntity(): Parent {
        val parent = Parent("joe", (1..10).shuffled().first())

        parent.addChild(Child("boo"))
        parent.addChild(Child("foo"))
        parent.addChild(Child("noo"))
        repository!!.persist(parent)
        return parent
    }

    @Transactional
    fun getEntity(): List<Parent> {
        return repository!!.find("age > :age and name = :name",
                Sort.ascending("age"),
                Parameters.with("age", 5)
                        .and("name", "joe").map()).list()
    }
}

/**
 * CascadeType.ALL で子エンティティもCRUDライフサイクルに巻き込む.
 * 集約内のエンティティは一律これでOK.
 */
@Entity
@Access(AccessType.FIELD)
class Parent(val name: String, val age: Int) : PanacheEntity() {
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

@Dependent
class ParentRepository : PanacheRepository<Parent>
