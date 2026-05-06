package dto;

import java.util.List;

public class JogadorComunidadeResponse {

    public Long id;
    public String nome;
    public String tekkenId;
    public String foto;
    public Integer quantidadePersonagens;
    public List<PersonagemComunidadeResponse> personagens;

    public JogadorComunidadeResponse(
            Long id,
            String nome,
            String tekkenId,
            String foto,
            Integer quantidadePersonagens,
            List<PersonagemComunidadeResponse> personagens
    ) {
        this.id = id;
        this.nome = nome;
        this.tekkenId = tekkenId;
        this.foto = foto;
        this.quantidadePersonagens = quantidadePersonagens;
        this.personagens = personagens;
    }
}