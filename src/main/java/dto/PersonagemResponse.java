package dto;

public class PersonagemResponse {

    public Long id;
    public String nome;
    public String imagem;
    public boolean favorito;

    public PersonagemResponse(Long id, String nome, String imagem, boolean favorito) {
        this.id = id;
        this.nome = nome;
        this.imagem = imagem;
        this.favorito = favorito;
    }
}

