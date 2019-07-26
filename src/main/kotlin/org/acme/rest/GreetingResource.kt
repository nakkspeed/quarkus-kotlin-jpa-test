package org.acme.rest

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/greeting")
@Produces(MediaType.APPLICATION_JSON)
class GreetingResource {

    @Inject
    var service: SantaClausService? = null

    @GET
    fun hello() = service!!.createGift("cat")
}
