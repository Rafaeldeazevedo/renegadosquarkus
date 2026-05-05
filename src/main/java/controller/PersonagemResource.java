package controller;
import dto.PersonagemFrameDataDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import service.PersonagemFrameDataService;

import java.util.List;

@Path("/personagens")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonagemResource {

    @Inject
    PersonagemFrameDataService frameDataService;

    @GET
    @Path("/{id}/frame-data")
    public List<PersonagemFrameDataDTO> buscarFrameData(@PathParam("id") Integer personagemId) {
        return frameDataService.buscarPorPersonagem(personagemId);
    }
}
