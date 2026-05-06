package dto;

public class PlayerStyleResponse {
    public Long jogadorId;
    public Long personagemId;
    public String estiloJogo;

    public PlayerStyleResponse(Long jogadorId, Long personagemId, String estiloJogo) {
        this.jogadorId = jogadorId;
        this.personagemId = personagemId;
        this.estiloJogo = estiloJogo;
    }
}