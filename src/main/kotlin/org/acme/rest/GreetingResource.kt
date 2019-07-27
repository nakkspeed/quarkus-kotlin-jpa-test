package org.acme.rest

import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/greeting")
class GreetingResource {

    @Inject
    var service: SantaClausService? = null

    @Inject
    var nestedEntityService: NestedEntityService? = null

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello() = "hello"

    @GET
    @Path("/gift")
    @Produces(MediaType.APPLICATION_JSON)
    fun gift() = service!!.createGift("cat")

    @GET
    @Path("/parent/create")
    @Produces(MediaType.APPLICATION_JSON)
    fun createParent() = nestedEntityService!!.createTreeEntity()
}
