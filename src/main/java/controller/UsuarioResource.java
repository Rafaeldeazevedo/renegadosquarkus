package controller;

import dto.AtualizarPerfilRequest;
import dto.LoginResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import service.UsuarioService;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioService usuarioService;

    @GET
    @Path("/{id}")
    public LoginResponse buscarPorId(@PathParam("id") Long id) {
        return usuarioService.buscarPorId(id);
    }

    @PUT
    @Path("/{id}/perfil")
    public LoginResponse atualizarPerfil(
            @PathParam("id") Long id,
            AtualizarPerfilRequest request
    ) {
        return usuarioService.atualizarPerfil(id, request);
    }
}