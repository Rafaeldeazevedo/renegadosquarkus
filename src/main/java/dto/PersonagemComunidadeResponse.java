package dto;

public class PersonagemComunidadeResponse {

    public Long id;
    public String nome;
    public String imagem;

    public PersonagemComunidadeResponse(Long id, String nome, String imagem) {
        this.id = id;
        this.nome = nome;
        this.imagem = imagem;
    }
}