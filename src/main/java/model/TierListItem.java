package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(name = "tier_list_item")
public class TierListItem extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "tier_list_id", nullable = false)
    public TierList tierList;

    @Column(name = "personagem_id", nullable = false)
    public Long personagemId;

    @Column(name = "tier", nullable = false, length = 1)
    public String tier;

    @Column(name = "posicao", nullable = false)
    public Integer posicao;
}