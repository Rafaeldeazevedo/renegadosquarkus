package repository;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import model.Usuario;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {

    public Usuario buscarPorEmail(String email) {
        if (email == null) {
            return null;
        }

        String emailTratado = email.trim().toLowerCase();

        return find("LOWER(email) = ?1", emailTratado)
                .firstResult();
    }
}