package service;

import dto.AtualizarPerfilRequest;
import dto.LoginResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import model.Usuario;
import repository.UsuarioRepository;

@ApplicationScoped
public class UsuarioService {

    @Inject
    UsuarioRepository usuarioRepository;

    public LoginResponse buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id);

        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado.");
        }

        return toLoginResponse(usuario, null);
    }

    @Transactional
    public LoginResponse atualizarPerfil(Long id, AtualizarPerfilRequest request) {
        Usuario usuario = usuarioRepository.findById(id);

        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado.");
        }

        if (request.nickname != null && !request.nickname.trim().isEmpty()) {
            usuario.nickname = request.nickname.trim();
        }

        if (request.fotoPerfil != null && !request.fotoPerfil.trim().isEmpty()) {
            usuario.fotoPerfil = request.fotoPerfil;
        }

        return toLoginResponse(usuario, null);
    }

    private LoginResponse toLoginResponse(Usuario usuario, String token) {
        return new LoginResponse(
                usuario.id,
                usuario.nome,
                usuario.email,
                usuario.nickname,
                usuario.nivel,
                usuario.xp,
                usuario.fotoPerfil,
                token,
                usuario.senhaTemporaria,
                usuario.perfil
        );
    }
}