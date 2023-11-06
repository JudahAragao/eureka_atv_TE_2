package br.com.eureka.eureka.service;

import br.com.eureka.eureka.model.Solicitante;
import br.com.eureka.eureka.repository.SolicitanteRepository;
import br.com.eureka.eureka.rest.dto.SolicitanteDto;
import br.com.eureka.eureka.rest.form.SolicitanteForm;
import br.com.eureka.eureka.service.exceptions.DataIntegrityException;
import br.com.eureka.eureka.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SolicitanteService {

    @Autowired
    private SolicitanteRepository solicitanteRepository;

    @Autowired
    private ModelMapper modelMapper;

    public SolicitanteDto obterPorId(int id) {
        Optional<Solicitante> solicitanteOptional = solicitanteRepository.findById(id);
        Solicitante solicitante = solicitanteOptional.orElseThrow(() -> new ObjectNotFoundException("Solicitante não encontrado! Código: " + id + ", Tipo: " + Solicitante.class.getName()));
        return modelMapper.map(solicitante, SolicitanteDto.class);
    }

    public List<SolicitanteDto> obterTodos() {
        List<Solicitante> solicitanteList = solicitanteRepository.findAll();
        return solicitanteList.stream()
                .map(solicitante -> modelMapper.map(solicitante, SolicitanteDto.class))
                .collect(Collectors.toList());
    }

    public SolicitanteDto salvarSolicitante(SolicitanteForm solicitanteForm) {
        try {
            Solicitante novoSolicitante = modelMapper.map(solicitanteForm, Solicitante.class);
            novoSolicitante.setDataCadastro(LocalDateTime.now());

            novoSolicitante = solicitanteRepository.save(novoSolicitante);

            return modelMapper.map(novoSolicitante, SolicitanteDto.class);
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do Solicitante não foi(foram) preenchido(s).");
        }
    }

    public SolicitanteDto atualizarSolicitante(SolicitanteForm solicitanteForm, int id) {
        Optional<Solicitante> solicitanteExistente = solicitanteRepository.findById(id);

        if (solicitanteExistente.isPresent()) {
            Solicitante solicitanteAtualizado = solicitanteExistente.get();
            solicitanteAtualizado.setNome(solicitanteForm.getNome());
            solicitanteAtualizado.setDataAlteracao(LocalDateTime.now());
            solicitanteAtualizado = solicitanteRepository.save(solicitanteAtualizado);

            return modelMapper.map(solicitanteAtualizado, SolicitanteDto.class);
        } else {
            throw new ObjectNotFoundException("O Código do Solicitante não existe na base de dados.");
        }
    }

    public void removerSolicitante(int id) {
        if (solicitanteRepository.existsById(id)) {
            solicitanteRepository.deleteById(id);
        } else {
            throw new ObjectNotFoundException("O código do Solicitante não existe na base de dados.");
        }
    }
}
