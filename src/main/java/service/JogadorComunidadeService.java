package service;

import dto.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import model.*;
import repository.*;

import java.util.List;

@ApplicationScoped
public class JogadorComunidadeService {

    @Inject
    JogadorComunidadePlayerStyleRepository playerStyleRepository;

    @Inject
    JogadorComunidadeRepository jogadorRepository;

    @Inject
    JogadorComunidadeManiaRepository maniaRepository;

    @Inject
    JogadorComunidadePersonagemRepository jogadorPersonagemRepository;

    @Inject
    PersonagemRepository personagemRepository;

    public List<JogadorComunidadeResponse> listar() {
        return jogadorRepository.listAll()
                .stream()
                .map(jogador -> {
                    List<PersonagemComunidadeResponse> personagens = jogadorPersonagemRepository
                            .buscarPorJogadorId(jogador.id)
                            .stream()
                            .map(vinculo -> new PersonagemComunidadeResponse(
                                    vinculo.getPersonagem().id,
                                    vinculo.getPersonagem().nome,
                                    vinculo.getPersonagem().imagem
                            ))
                            .toList();

                    return new JogadorComunidadeResponse(
                            jogador.id,
                            jogador.nome,
                            jogador.tekkenId,
                            jogador.foto,
                            personagens.size(),
                            personagens,
                            jogador.criadoPorNickname,
                            jogador.criadoEm
                    );
                })
                .toList();
    }

    public TotalResponse contarPlayerStyles() {
        Long total = maniaRepository.count();
        return new TotalResponse(total);
    }

    public List<ManiaResponse> listarManias(Long jogadorId, Long personagemId) {
        return maniaRepository.buscarPorJogadorEPersonagem(jogadorId, personagemId)
                .stream()
                .map(mania -> new ManiaResponse(
                        mania.id,
                        mania.descricao,
                        mania.criadoPorNickname
                ))
                .toList();
    }

    public JogadorComunidadeResponse buscarPorId(Long id) {
        JogadorComunidade jogador = jogadorRepository.findById(id);

        if (jogador == null) {
            throw new NotFoundException("Jogador não encontrado.");
        }

        List<PersonagemComunidadeResponse> personagens = jogadorPersonagemRepository
                .buscarPorJogadorId(jogador.id)
                .stream()
                .map(vinculo -> new PersonagemComunidadeResponse(
                        vinculo.getPersonagem().id,
                        vinculo.getPersonagem().nome,
                        vinculo.getPersonagem().imagem
                ))
                .toList();

        return new JogadorComunidadeResponse(
                jogador.id,
                jogador.nome,
                jogador.tekkenId,
                jogador.foto,
                personagens.size(),
                personagens,
                jogador.criadoPorNickname,
                jogador.criadoEm
        );
    }

    public PlayerStyleResponse buscarPlayerStyle(Long jogadorId, Long personagemId) {
        JogadorComunidadePlayerStyle style =
                playerStyleRepository.buscarPorJogadorEPersonagem(jogadorId, personagemId);

        if (style == null) {
            return new PlayerStyleResponse(jogadorId, personagemId, "");
        }

        return new PlayerStyleResponse(
                jogadorId,
                personagemId,
                style.estiloJogo
        );
    }

    @Transactional
    public JogadorComunidadeResponse cadastrar(JogadorComunidadeRequest request) {
        if (request.nome == null || request.nome.trim().isEmpty()) {
            throw new BadRequestException("Nome do jogador é obrigatório.");
        }

        if (request.tekkenId == null || request.tekkenId.trim().isEmpty()) {
            throw new BadRequestException("Tekken ID é obrigatório.");
        }

        if (request.personagensIds == null || request.personagensIds.isEmpty()) {
            throw new BadRequestException("Selecione pelo menos um personagem.");
        }

        JogadorComunidade jogador = new JogadorComunidade();
        jogador.nome = request.nome.trim();
        jogador.tekkenId = request.tekkenId.trim();
        jogador.foto = request.foto;
        jogador.criadoPorNickname = request.criadoPorNickname;

        jogadorRepository.persist(jogador);

        for (Long personagemId : request.personagensIds) {
            Personagem personagem = personagemRepository.findById(personagemId);

            if (personagem == null) {
                throw new NotFoundException("Personagem não encontrado: " + personagemId);
            }

            JogadorComunidadePersonagem vinculo = new JogadorComunidadePersonagem();
            vinculo.setJogador(jogador);
            vinculo.setPersonagem(personagem);

            jogadorPersonagemRepository.persist(vinculo);
        }

        List<PersonagemComunidadeResponse> personagensResponse = jogadorPersonagemRepository
                .buscarPorJogadorId(jogador.id)
                .stream()
                .map(vinculo -> new PersonagemComunidadeResponse(
                        vinculo.getPersonagem().id,
                        vinculo.getPersonagem().nome,
                        vinculo.getPersonagem().imagem
                ))
                .toList();

        return new JogadorComunidadeResponse(
                jogador.id,
                jogador.nome,
                jogador.tekkenId,
                jogador.foto,
                personagensResponse.size(),
                personagensResponse,
                jogador.criadoPorNickname,
                jogador.criadoEm
        );
    }

    @Transactional
    public void excluirJogador(Long id) {
        JogadorComunidade jogador = jogadorRepository.findById(id);

        if (jogador == null) {
            throw new NotFoundException("Jogador não encontrado.");
        }

        List<JogadorComunidadeMania> manias =
                maniaRepository.buscarPorJogadorId(id);

        for (JogadorComunidadeMania mania : manias) {
            maniaRepository.delete(mania);
        }

        List<JogadorComunidadePlayerStyle> styles =
                playerStyleRepository.buscarPorJogadorId(id);

        for (JogadorComunidadePlayerStyle style : styles) {
            playerStyleRepository.delete(style);
        }

        List<JogadorComunidadePersonagem> vinculos =
                jogadorPersonagemRepository.buscarPorJogadorId(id);

        for (JogadorComunidadePersonagem vinculo : vinculos) {
            jogadorPersonagemRepository.delete(vinculo);
        }

        jogadorRepository.delete(jogador);
    }

    @Transactional
    public JogadorComunidadeResponse atualizar(Long id, JogadorComunidadeRequest request) {
        if (request.nome == null || request.nome.trim().isEmpty()) {
            throw new BadRequestException("Nome do jogador é obrigatório.");
        }

        if (request.tekkenId == null || request.tekkenId.trim().isEmpty()) {
            throw new BadRequestException("Tekken ID é obrigatório.");
        }

        if (request.personagensIds == null || request.personagensIds.isEmpty()) {
            throw new BadRequestException("Selecione pelo menos um personagem.");
        }

        JogadorComunidade jogador = jogadorRepository.findById(id);

        if (jogador == null) {
            throw new NotFoundException("Jogador não encontrado.");
        }

        jogador.nome = request.nome.trim();
        jogador.tekkenId = request.tekkenId.trim();
        jogador.foto = request.foto;

        if (request.criadoPorNickname != null && !request.criadoPorNickname.trim().isEmpty()) {
            jogador.criadoPorNickname = request.criadoPorNickname.trim();
        }

        List<JogadorComunidadePersonagem> vinculosAntigos =
                jogadorPersonagemRepository.buscarPorJogadorId(jogador.id);

        for (JogadorComunidadePersonagem vinculo : vinculosAntigos) {
            jogadorPersonagemRepository.delete(vinculo);
        }

        for (Long personagemId : request.personagensIds) {
            Personagem personagem = personagemRepository.findById(personagemId);

            if (personagem == null) {
                throw new NotFoundException("Personagem não encontrado: " + personagemId);
            }

            JogadorComunidadePersonagem novoVinculo = new JogadorComunidadePersonagem();
            novoVinculo.setJogador(jogador);
            novoVinculo.setPersonagem(personagem);

            jogadorPersonagemRepository.persist(novoVinculo);
        }

        List<PersonagemComunidadeResponse> personagensResponse = jogadorPersonagemRepository
                .buscarPorJogadorId(jogador.id)
                .stream()
                .map(vinculo -> new PersonagemComunidadeResponse(
                        vinculo.getPersonagem().id,
                        vinculo.getPersonagem().nome,
                        vinculo.getPersonagem().imagem
                ))
                .toList();

        return new JogadorComunidadeResponse(
                jogador.id,
                jogador.nome,
                jogador.tekkenId,
                jogador.foto,
                personagensResponse.size(),
                personagensResponse,
                jogador.criadoPorNickname,
                jogador.criadoEm
        );
    }

    @Transactional
    public PlayerStyleResponse salvarPlayerStyle(
            Long jogadorId,
            Long personagemId,
            PlayerStyleRequest request
    ) {
        JogadorComunidade jogador = jogadorRepository.findById(jogadorId);

        if (jogador == null) {
            throw new NotFoundException("Jogador não encontrado.");
        }

        Personagem personagem = personagemRepository.findById(personagemId);

        if (personagem == null) {
            throw new NotFoundException("Personagem não encontrado.");
        }

        JogadorComunidadePlayerStyle style =
                playerStyleRepository.buscarPorJogadorEPersonagem(jogadorId, personagemId);

        if (style == null) {
            style = new JogadorComunidadePlayerStyle();
            style.jogador = jogador;
            style.personagem = personagem;
            playerStyleRepository.persist(style);
        }

        style.estiloJogo = request.estiloJogo != null
                ? request.estiloJogo.trim()
                : "";

        return new PlayerStyleResponse(
                jogadorId,
                personagemId,
                style.estiloJogo
        );
    }

    public List<PersonagemComunidadeResponse> listarPersonagensDoJogador(Long jogadorId) {
        JogadorComunidade jogador = jogadorRepository.findById(jogadorId);

        if (jogador == null) {
            throw new NotFoundException("Jogador não encontrado.");
        }

        return jogadorPersonagemRepository.buscarPorJogadorId(jogadorId)
                .stream()
                .map(vinculo -> new PersonagemComunidadeResponse(
                        vinculo.getPersonagem().id,
                        vinculo.getPersonagem().nome,
                        vinculo.getPersonagem().imagem
                ))
                .toList();
    }

    @Transactional
    public ManiaResponse criarMania(Long jogadorId, Long personagemId, ManiaRequest request) {
        if (request.descricao == null || request.descricao.trim().isEmpty()) {
            throw new BadRequestException("Descrição é obrigatória.");
        }

        JogadorComunidade jogador = jogadorRepository.findById(jogadorId);

        if (jogador == null) {
            throw new NotFoundException("Jogador não encontrado.");
        }

        Personagem personagem = personagemRepository.findById(personagemId);

        if (personagem == null) {
            throw new NotFoundException("Personagem não encontrado.");
        }

        JogadorComunidadeMania mania = new JogadorComunidadeMania();
        mania.jogador = jogador;
        mania.personagem = personagem;
        mania.descricao = request.descricao.trim();
        mania.criadoPorNickname = request.criadoPorNickname != null && !request.criadoPorNickname.trim().isEmpty()
                ? request.criadoPorNickname.trim()
                : "Jogador Renegado";

        maniaRepository.persist(mania);

        return new ManiaResponse(
                mania.id,
                mania.descricao,
                mania.criadoPorNickname
        );
    }

    @Transactional
    public void excluirMania(Long maniaId) {
        boolean excluiu = maniaRepository.deleteById(maniaId);

        if (!excluiu) {
            throw new NotFoundException("Mania não encontrada.");
        }
    }

    @Transactional
    public ManiaResponse alterarMania(Long maniaId, ManiaRequest request) {
        if (request.descricao == null || request.descricao.trim().isEmpty()) {
            throw new BadRequestException("Descrição é obrigatória.");
        }

        JogadorComunidadeMania mania = maniaRepository.findById(maniaId);

        if (mania == null) {
            throw new NotFoundException("Mania não encontrada.");
        }

        mania.descricao = request.descricao.trim();

        return new ManiaResponse(
                mania.id,
                mania.descricao,
                mania.criadoPorNickname
        );
    }
}