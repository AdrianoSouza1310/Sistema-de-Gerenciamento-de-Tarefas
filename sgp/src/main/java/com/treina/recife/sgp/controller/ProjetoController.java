package com.treina.recife.sgp.controller;

import java.util.Optional;

import org.slf4j.Logger; // Importação correta para SLF4J
import org.slf4j.LoggerFactory; // Importação correta para SLF4J
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treina.recife.sgp.constants.StatusProjeto;
import com.treina.recife.sgp.dto.ProjetoDto;
import com.treina.recife.sgp.dto.UsuarioDto;
import com.treina.recife.sgp.model.Projeto;
import com.treina.recife.sgp.model.Usuario;
import com.treina.recife.sgp.service.ProjetoService;
import com.treina.recife.sgp.service.UsuarioService;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {

    private final ProjetoService projetoService;
    private final UsuarioService usuarioService;
    private static final Logger logger = LoggerFactory.getLogger(ProjetoController.class); // Inicialização correta do logger

    // Injeção de dependência via construtor é a melhor prática
    public ProjetoController(ProjetoService projetoService, UsuarioService usuarioService) {
        this.projetoService = projetoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<Page<Projeto>> getProjetos(
            @PageableDefault(sort = "projectId", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<Projeto> projetos = projetoService.getProjetos(pageable);

        if (projetos.isEmpty()) {
            logger.info("Ainda não há projeto cadastrado."); // Adicionado ponto final para consistência
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Page.empty());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(projetos);
        }
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Object> getProjetoById(@PathVariable(value = "projectId") long projectId) { // Renomeado para getProjetoById
        Optional<Projeto> projeto = projetoService.getProjetoById(projectId);

        if (projeto.isEmpty()) {
            logger.error("Projeto não encontrado com ID: {}", projectId); // Mensagem de erro mais descritiva
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado."); // Consistência na mensagem
        } else {
            logger.info("Projeto encontrado: {}", projeto.get().getNome()); // Log mais específico
            return ResponseEntity.status(HttpStatus.OK).body(projeto.get());
        }
    }

    @PostMapping
    public ResponseEntity<Object> createProjeto(@RequestBody ProjetoDto projetoDto) {
        Projeto novoProjeto = new Projeto();
        novoProjeto.setNome(projetoDto.getNome());
        novoProjeto.setDescricao(projetoDto.getDescricao());
        novoProjeto.setDataInicio(projetoDto.getDataInicio());
        novoProjeto.setStatus(StatusProjeto.ATIVO); // Assumindo que novos projetos começam como ATIVO
        novoProjeto.setDataConclusao(projetoDto.getDataConclusao());

        Projeto projetoCriado = projetoService.createProjeto(novoProjeto); // Renomeado para clareza
        logger.info("Projeto '{}' (ID: {}) criado com sucesso.", projetoCriado.getNome(), projetoCriado.getProjectId());
        return ResponseEntity.status(HttpStatus.CREATED).body(projetoCriado);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<Object> updateProjeto(@PathVariable(value = "projectId") long projectId,
                                                @RequestBody ProjetoDto projetoDto) {

        Optional<Projeto> projetoExistente = projetoService.getProjetoById(projectId);

        if (projetoExistente.isEmpty()) {
            logger.error("Projeto com ID {} não encontrado para atualização.", projectId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado para atualização.");
        } else {
            Projeto projetoParaAtualizar = projetoExistente.get();
            projetoParaAtualizar.setNome(projetoDto.getNome());
            projetoParaAtualizar.setDescricao(projetoDto.getDescricao());
            projetoParaAtualizar.setDataInicio(projetoDto.getDataInicio());
            projetoParaAtualizar.setDataConclusao(projetoDto.getDataConclusao());
            projetoParaAtualizar.setStatus(projetoDto.getStatus()); // Permitindo a atualização do status

            Projeto projetoAtualizado = projetoService.updateProjeto(projetoParaAtualizar);
            logger.info("Projeto de ID {} atualizado com sucesso.", projetoAtualizado.getProjectId());
            return ResponseEntity.status(HttpStatus.OK).body(projetoAtualizado);
        }
    }

    @DeleteMapping("/{projectId}") // Correção do @DeleteMapping path
    public ResponseEntity<Object> deleteProjeto(@PathVariable(value = "projectId") long projectId) {

        Optional<Projeto> projeto = projetoService.getProjetoById(projectId);

        if (projeto.isEmpty()) {
            logger.error("Projeto com ID {} não encontrado para exclusão.", projectId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado para exclusão.");
        } else {
            projetoService.deleteProjeto(projectId);
            logger.info("Projeto com ID {} deletado com sucesso.", projectId);
            return ResponseEntity.status(HttpStatus.OK).body("Projeto deletado com sucesso.");
        }
    }

    @PatchMapping("/{projectId}/responsavel") // Correção do @PatchMapping path: '{ProjectId}' para '{projectId}'
    public ResponseEntity<Object> atribuirResponsavel(@PathVariable(value = "projectId") long projectId,
                                                      @RequestBody UsuarioDto usuarioDto) {

        Optional<Projeto> projetoOptional = projetoService.getProjetoById(projectId);

        if (projetoOptional.isEmpty()) {
            logger.error("Projeto com ID {} não encontrado para atribuir responsável.", projectId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projeto não encontrado.");
        }

        Optional<Usuario> usuarioOptional = usuarioService.getUsuarioById(usuarioDto.getUserId());

        if (usuarioOptional.isEmpty()) {
            logger.error("Usuário com ID {} não encontrado para atribuição de responsável.", usuarioDto.getUserId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }

        Projeto projeto = projetoOptional.get();
        Usuario responsavel = usuarioOptional.get();

        projeto.setResponsavel(responsavel);

        Projeto projetoAtualizado = projetoService.updateProjeto(projeto);
        logger.info("Responsável '{}' (ID: {}) designado ao projeto '{}' (ID: {}) com sucesso.", 
                    responsavel.getNome(), responsavel.getUserId(), projetoAtualizado.getNome(), projetoAtualizado.getProjectId());
        return ResponseEntity.status(HttpStatus.OK).body(projetoAtualizado);
    }
}