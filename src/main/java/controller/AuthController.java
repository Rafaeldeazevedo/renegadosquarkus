package controller;

import dto.AtualizarPerfilRequest;
import dto.LoginRequest;
import dto.LoginResponse;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import model.Usuario;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthController {

    @Inject
    EntityManager entityManager;

    @POST
    @Path("/login")
    public LoginResponse login(LoginRequest request) {
        Usuario usuario = Usuario.find("email", request.email).firstResult();

        if (usuario == null) {
            throw new WebApplicationException("Email ou senha inválidos.", 401);
        }

        if (!usuario.senha.equals(request.senha)) {
            throw new WebApplicationException("Email ou senha inválidos.", 401);
        }

        return new LoginResponse(
                usuario.id,
                usuario.nome,
                usuario.email,
                usuario.nickname,
                usuario.nivel,
                usuario.xp,
                usuario.fotoPerfil
        );
    }
    @GET
    @Path("/usuarios/{id}")
    public LoginResponse buscarUsuarioPorId(@PathParam("id") Long id) {
        Usuario usuario = entityManager.find(Usuario.class, id);

        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado.");
        }

        return new LoginResponse(
                usuario.id,
                usuario.nome,
                usuario.email,
                usuario.nickname,
                usuario.nivel,
                usuario.xp,
                usuario.fotoPerfil
        );
    }

    @PUT
    @Path("/usuarios/{id}/perfil")
    @Transactional
    public LoginResponse atualizarPerfil(
            @PathParam("id") Long id,
            AtualizarPerfilRequest request
    ) {
        Usuario usuario = entityManager.find(Usuario.class, id);

        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado.");
        }

        if (request.nickname != null) {
            usuario.nickname = request.nickname.trim();
        }

        if (request.fotoPerfil != null) {
            usuario.fotoPerfil = request.fotoPerfil;
        }

        return new LoginResponse(
                usuario.id,
                usuario.nome,
                usuario.email,
                usuario.nickname,
                usuario.nivel,
                usuario.xp,
                usuario.fotoPerfil
        );
    }
}
