package br.com.eureka.eureka.service;

import br.com.eureka.eureka.model.ObjetivoEstrategico;
import br.com.eureka.eureka.repository.ObjetivoEstrategicoRepository;
import br.com.eureka.eureka.rest.dto.ObjetivoEstrategicoDto;
import br.com.eureka.eureka.rest.form.ObjetivoEstrategicoForm;
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
public class ObjetivoEstrategicoService {

    @Autowired
    private ObjetivoEstrategicoRepository objetivoEstrategicoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ObjetivoEstrategicoDto obterPorId(int id) {
        Optional<ObjetivoEstrategico> objetivoEstrategicoOptional = objetivoEstrategicoRepository.findById(id);
        ObjetivoEstrategico objetivoEstrategico = objetivoEstrategicoOptional.orElseThrow(() -> new ObjectNotFoundException("Objetivo Estrategico não encontrado! Código: " + id + ", Tipo: " + ObjetivoEstrategico.class.getName()));
        return modelMapper.map(objetivoEstrategico, ObjetivoEstrategicoDto.class);
    }

    public List<ObjetivoEstrategicoDto> obterTodos() {
        List<ObjetivoEstrategico> objetivoEstrategicoList = objetivoEstrategicoRepository.findAll();
        return objetivoEstrategicoList.stream()
                .map(objetivoEstrategico -> modelMapper.map(objetivoEstrategico, ObjetivoEstrategicoDto.class))
                .collect(Collectors.toList());
    }

    public ObjetivoEstrategicoDto salvarObjetivoEstrategico(ObjetivoEstrategicoForm objetivoEstrategicoForm) {
        try {
            ObjetivoEstrategico objetivoEstrategicoNovo = modelMapper.map(objetivoEstrategicoForm, ObjetivoEstrategico.class);
            objetivoEstrategicoNovo.setDataCadastro(LocalDateTime.now());

            objetivoEstrategicoNovo = objetivoEstrategicoRepository.save(objetivoEstrategicoNovo);

            return modelMapper.map(objetivoEstrategicoNovo, ObjetivoEstrategicoDto.class);
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) do Objetivo Estrategico não foi(foram) preenchido(s).");
        }
    }

    public ObjetivoEstrategicoDto atualizarObjetivoEstrategico(ObjetivoEstrategicoForm objetivoEstrategicoForm, int id) {
        Optional<ObjetivoEstrategico> objetivoEstrategicoExistente = objetivoEstrategicoRepository.findById(id);

        if (objetivoEstrategicoExistente.isPresent()) {
            ObjetivoEstrategico objetivoEstrategicoAtualizado = objetivoEstrategicoExistente.get();
            objetivoEstrategicoAtualizado.setNome(objetivoEstrategicoForm.getNome());
            objetivoEstrategicoAtualizado.setDataAlteracao(LocalDateTime.now());
            objetivoEstrategicoAtualizado = objetivoEstrategicoRepository.save(objetivoEstrategicoAtualizado);

            return modelMapper.map(objetivoEstrategicoAtualizado, ObjetivoEstrategicoDto.class);
        } else {
            throw new ObjectNotFoundException("O Código do Objetivo Estrategico não existe na base de dados!");
        }
    }

    public void removerObjetivoEstrategico(int id) {
        if (objetivoEstrategicoRepository.existsById(id)) {
            objetivoEstrategicoRepository.deleteById(id);
        } else {
            throw new ObjectNotFoundException("O código do Objetivo Estrategico não existe na base de dados!");
        }
    }
}
