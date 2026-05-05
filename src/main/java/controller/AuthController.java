package controller;

import dto.LoginRequest;
import dto.LoginResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import model.Usuario;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthController {

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
                usuario.xp
        );
    }
}
