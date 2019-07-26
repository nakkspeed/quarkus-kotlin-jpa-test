package org.acme.rest

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/greeting")
class GreetingResource {

    @Inject
    var service: SantaClausService? = null

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello() = "hello"

    @GET
    @Path("/gift")
    @Produces(MediaType.APPLICATION_JSON)
    fun gift() = service!!.createGift("cat")
}
