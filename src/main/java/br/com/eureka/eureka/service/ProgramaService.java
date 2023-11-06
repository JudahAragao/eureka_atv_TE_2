package br.com.eureka.eureka.service;

import br.com.eureka.eureka.model.Programa;
import br.com.eureka.eureka.repository.ProgramaRepository;
import br.com.eureka.eureka.rest.dto.ProgramaDto;
import br.com.eureka.eureka.rest.form.ProgramaForm;
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
public class ProgramaService {

    @Autowired
    private ProgramaRepository programaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ProgramaDto obterPorId(int id) {
        Optional<Programa> programaOptional = programaRepository.findById(id);
        Programa programa = programaOptional.orElseThrow(() -> new ObjectNotFoundException("Programa não encontrado! Código: " + id + ", Tipo: " + Programa.class.getName()));
        return modelMapper.map(programa, ProgramaDto.class);
    }

    public List<ProgramaDto> obterTodos() {
        List<Programa> programaList = programaRepository.findAll();
        return programaList.stream()
                .map(programa -> modelMapper.map(programa, ProgramaDto.class))
                .collect(Collectors.toList());
    }

    public ProgramaDto salvarPrograma(ProgramaForm programaForm) {
        try {
            Programa novoPrograma = modelMapper.map(programaForm, Programa.class);
            novoPrograma.setDataCadastro(LocalDateTime.now());

            novoPrograma = programaRepository.save(novoPrograma);

            return modelMapper.map(novoPrograma, ProgramaDto.class);
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do Programa não foi(foram) preenchido(s).");
        }
    }

    public ProgramaDto atualizarPrograma(ProgramaForm programaForm, int id) {
        Optional<Programa> programaExistente = programaRepository.findById(id);

        if (programaExistente.isPresent()) {
            Programa programaAtualizado = programaExistente.get();
            programaAtualizado.setNome(programaForm.getNome());
            programaAtualizado.setCodigo(programaForm.getCodigo());
            programaAtualizado.setDataAlteracao(LocalDateTime.now());
            programaAtualizado = programaRepository.save(programaAtualizado);

            return modelMapper.map(programaAtualizado, ProgramaDto.class);
        } else {
            throw new ObjectNotFoundException("O Código do Programa não existe na base de dados.");
        }
    }

    public void removerPrograma(int id) {
        if (programaRepository.existsById(id)) {
            programaRepository.deleteById(id);
        } else {
            throw new ObjectNotFoundException("O código do Programa não existe na base de dados.");
        }
    }
}
