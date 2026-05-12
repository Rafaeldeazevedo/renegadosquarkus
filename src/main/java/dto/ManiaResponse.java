package dto;

public class ManiaResponse {

    public Long id;
    public String descricao;
    public String criadoPorNickname;


    public ManiaResponse(Long id, String descricao, String criadoPorNickname) {
        this.id = id;
        this.descricao = descricao;
        this.criadoPorNickname = criadoPorNickname;
    }
}
