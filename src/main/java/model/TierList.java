package model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tier_list")
public class TierList extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "usuario_id", nullable = false)
    public Long usuarioId;

    @Column(name = "nome", nullable = false, length = 100)
    public String nome;

    @Column(name = "season", nullable = false, length = 50)
    public String season;

    @Column(name = "data_criacao")
    public LocalDateTime dataCriacao;

    @OneToMany(mappedBy = "tierList", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<TierListItem> itens = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
    }

    public void adicionarItem(TierListItem item) {
        item.tierList = this;
        this.itens.add(item);
    }
}