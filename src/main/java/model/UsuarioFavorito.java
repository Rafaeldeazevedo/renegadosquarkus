package model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "usuario_favorito",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"usuario_id", "personagem_id"})
        }
)
public class UsuarioFavorito extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    public Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "personagem_id", nullable = false)
    public Personagem personagem;
}