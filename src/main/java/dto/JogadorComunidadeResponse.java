package dto;

import java.time.LocalDateTime;
import java.util.List;

public class JogadorComunidadeResponse {

    public Long id;
    public String nome;
    public String tekkenId;
    public String foto;
    public Integer totalPersonagens;
    public List<PersonagemComunidadeResponse> personagens;
    public String criadoPorNickname;
    public LocalDateTime criadoEm;

    public JogadorComunidadeResponse(
            Long id,
            String nome,
            String tekkenId,
            String foto,
            Integer totalPersonagens,
            List<PersonagemComunidadeResponse> personagens,
            String criadoPorNickname,
            LocalDateTime criadoEm
    ) {
        this.id = id;
        this.nome = nome;
        this.tekkenId = tekkenId;
        this.foto = foto;
        this.totalPersonagens = totalPersonagens;
        this.personagens = personagens;
        this.criadoPorNickname = criadoPorNickname;
        this.criadoEm = criadoEm;
    }
}