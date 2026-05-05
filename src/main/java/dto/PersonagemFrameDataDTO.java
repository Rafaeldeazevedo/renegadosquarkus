package dto;

public class PersonagemFrameDataDTO {

    public Long id;
    public Integer personagemId;
    public String comando;
    public String hitLevel;
    public String dano;
    public String startup;
    public String blockFrame;
    public String hitFrame;
    public String counterHitFrame;
    public String observacao;

    public PersonagemFrameDataDTO(
            Long id,
            Integer personagemId,
            String comando,
            String hitLevel,
            String dano,
            String startup,
            String blockFrame,
            String hitFrame,
            String counterHitFrame,
            String observacao
    ) {
        this.id = id;
        this.personagemId = personagemId;
        this.comando = comando;
        this.hitLevel = hitLevel;
        this.dano = dano;
        this.startup = startup;
        this.blockFrame = blockFrame;
        this.hitFrame = hitFrame;
        this.counterHitFrame = counterHitFrame;
        this.observacao = observacao;
    }
}