package model;

import jakarta.persistence.*;

@Entity
@Table(name = "personagem_frame_data")
public class PersonagemFrameData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "personagem_id", nullable = false)
    private Integer personagemId;

    @Column(name = "comando", nullable = false)
    private String comando;

    @Column(name = "hit_level")
    private String hitLevel;

    @Column(name = "dano")
    private String dano;

    @Column(name = "startup")
    private String startup;

    @Column(name = "block_frame")
    private String blockFrame;

    @Column(name = "hit_frame")
    private String hitFrame;

    @Column(name = "counter_hit_frame")
    private String counterHitFrame;

    @Column(name = "observacao")
    private String observacao;

    public Long getId() {
        return id;
    }

    public Integer getPersonagemId() {
        return personagemId;
    }

    public void setPersonagemId(Integer personagemId) {
        this.personagemId = personagemId;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public String getHitLevel() {
        return hitLevel;
    }

    public void setHitLevel(String hitLevel) {
        this.hitLevel = hitLevel;
    }

    public String getDano() {
        return dano;
    }

    public void setDano(String dano) {
        this.dano = dano;
    }

    public String getStartup() {
        return startup;
    }

    public void setStartup(String startup) {
        this.startup = startup;
    }

    public String getBlockFrame() {
        return blockFrame;
    }

    public void setBlockFrame(String blockFrame) {
        this.blockFrame = blockFrame;
    }

    public String getHitFrame() {
        return hitFrame;
    }

    public void setHitFrame(String hitFrame) {
        this.hitFrame = hitFrame;
    }

    public String getCounterHitFrame() {
        return counterHitFrame;
    }

    public void setCounterHitFrame(String counterHitFrame) {
        this.counterHitFrame = counterHitFrame;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}