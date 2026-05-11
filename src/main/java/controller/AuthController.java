package controller;

import dto.AtualizarPerfilRequest;
import dto.LoginRequest;
import dto.LoginResponse;
import dto.TrocarSenhaRequest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Usuario;
import repository.UsuarioRepository;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthController {

    @Inject
    EntityManager entityManager;

    @Inject
    UsuarioRepository usuarioRepository;

    @POST
    @Path("/login")
    public Response login(LoginRequest request) {
        Usuario usuario = usuarioRepository.find("email", request.email).firstResult();

        if (usuario == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Email ou senha inválidos.")
                    .build();
        }

        if (!usuario.senha.equals(request.senha)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Email ou senha inválidos.")
                    .build();
        }


        LoginResponse response = new LoginResponse(
                usuario.id,
                usuario.nome,
                usuario.email,
                usuario.nickname,
                usuario.nivel,
                usuario.xp,
                usuario.fotoPerfil,
                Boolean.TRUE.equals(usuario.senhaTemporaria)
        );

        return Response.ok(response).build();

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
                usuario.fotoPerfil,
                usuario.senhaTemporaria
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
                usuario.fotoPerfil,
                usuario.senhaTemporaria
        );
    }
    @PUT
    @Path("/trocar-senha")
    @Transactional
    public Response trocarSenha(TrocarSenhaRequest request) {
        if (request == null || request.usuarioId == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Usuário inválido.")
                    .build();
        }

        Usuario usuario = usuarioRepository.findById(request.usuarioId);

        if (usuario == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Usuário não encontrado.")
                    .build();
        }

        if (request.senhaAtual == null || !usuario.senha.equals(request.senhaAtual)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Senha atual inválida.")
                    .build();
        }

        if (request.novaSenha == null || request.novaSenha.trim().length() < 6) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("A nova senha deve ter pelo menos 6 caracteres.")
                    .build();
        }

        if (!request.novaSenha.equals(request.confirmarSenha)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("A confirmação da senha não confere.")
                    .build();
        }

        usuario.senha = request.novaSenha;
        usuario.senhaTemporaria = false;

        return Response.ok("Senha alterada com sucesso.").build();
    }
}
