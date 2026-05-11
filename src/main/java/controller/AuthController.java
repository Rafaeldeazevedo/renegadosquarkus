package controller;

import dto.AtualizarPerfilRequest;
import dto.LoginRequest;
import dto.LoginResponse;
import dto.TrocarSenhaRequest;
import dto.ValidarTokenResponse;
import io.smallrye.jwt.build.Jwt;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Usuario;
import org.eclipse.microprofile.jwt.JsonWebToken;
import repository.UsuarioRepository;

import java.time.Duration;
import java.util.Set;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthController {

    @Inject
    EntityManager entityManager;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    JsonWebToken jwt;

    @POST
    @Path("/login")
    @PermitAll
    @Transactional
    public Response login(LoginRequest request) {
        if (request == null || request.email == null || request.senha == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Informe email e senha.")
                    .build();
        }

        Usuario usuario = usuarioRepository.buscarPorEmail(request.email);

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

        String token = gerarToken(usuario);

        LoginResponse response = new LoginResponse(
                usuario.id,
                usuario.nome,
                usuario.email,
                usuario.nickname,
                usuario.nivel,
                usuario.xp,
                usuario.fotoPerfil,
                token,
                Boolean.TRUE.equals(usuario.senhaTemporaria)
        );

        return Response.ok(response).build();
    }

    @GET
    @Path("/validar-token")
    @RolesAllowed("USER")
    public Response validarToken() {
        return Response.ok(new ValidarTokenResponse(true)).build();
    }

    @GET
    @Path("/usuarios/{id}")
    @RolesAllowed("USER")
    public LoginResponse buscarUsuarioPorId(@PathParam("id") Long id) {
        validarUsuarioDoToken(id);

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
                null,
                Boolean.TRUE.equals(usuario.senhaTemporaria)
        );
    }

    @PUT
    @Path("/usuarios/{id}/perfil")
    @RolesAllowed("USER")
    @Transactional
    public LoginResponse atualizarPerfil(
            @PathParam("id") Long id,
            AtualizarPerfilRequest request
    ) {
        validarUsuarioDoToken(id);

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
                gerarToken(usuario),
                Boolean.TRUE.equals(usuario.senhaTemporaria)
        );
    }

    @PUT
    @Path("/trocar-senha")
    @RolesAllowed("USER")
    @Transactional
    public Response trocarSenha(TrocarSenhaRequest request) {
        if (request == null || request.usuarioId == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Usuário inválido.")
                    .build();
        }

        validarUsuarioDoToken(request.usuarioId);

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

    private String gerarToken(Usuario usuario) {
        return Jwt
                .issuer("renegados-jwt")
                .subject(String.valueOf(usuario.id))
                .upn(usuario.email)
                .groups(Set.of("USER"))
                .claim("nome", usuario.nome)
                .claim("email", usuario.email)
                .claim("nickname", usuario.nickname)
                .expiresIn(Duration.ofHours(8))
                .sign();
    }

    private void validarUsuarioDoToken(Long usuarioId) {
        Long usuarioIdToken = Long.valueOf(jwt.getSubject());

        if (!usuarioIdToken.equals(usuarioId)) {
            throw new ForbiddenException("Você não pode acessar dados de outro usuário.");
        }
    }
}