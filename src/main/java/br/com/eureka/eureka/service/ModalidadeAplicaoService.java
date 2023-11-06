package br.com.eureka.eureka.service;

import br.com.eureka.eureka.model.ModalidadeAplicacao;
import br.com.eureka.eureka.repository.ModalidadeAplicacaoRepository;
import br.com.eureka.eureka.rest.dto.ModalidadeAplicaoDto;
import br.com.eureka.eureka.rest.form.ModalidadeAplicaoForm;
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
public class ModalidadeAplicaoService {

    @Autowired
    private ModalidadeAplicacaoRepository modalidadeAplicacaoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ModalidadeAplicaoDto obterPorId(int id) {
        Optional<ModalidadeAplicacao> modalidadeAplicacaoOptional = modalidadeAplicacaoRepository.findById(id);
        ModalidadeAplicacao modalidadeAplicacao = modalidadeAplicacaoOptional.orElseThrow(() -> new ObjectNotFoundException("Modalidade Aplicacao não encontrado! Código: " + id + ", Tipo: " + ModalidadeAplicacao.class.getName()));
        return modelMapper.map(modalidadeAplicacao, ModalidadeAplicaoDto.class);
    }

    public List<ModalidadeAplicaoDto> obterTodos() {
        List<ModalidadeAplicacao> modalidadeAplicacaoList = modalidadeAplicacaoRepository.findAll();
        return modalidadeAplicacaoList.stream()
                .map(modalidadeAplicao -> modelMapper.map(modalidadeAplicao, ModalidadeAplicaoDto.class))
                .collect(Collectors.toList());
    }

    public ModalidadeAplicaoDto salvarModalidadeAplicao(ModalidadeAplicaoForm modalidadeAplicaoForm) {
        try {
            ModalidadeAplicacao modalidadeAplicacaoNovo = modelMapper.map(modalidadeAplicaoForm, ModalidadeAplicacao.class);
            modalidadeAplicacaoNovo.setDataCadastro(LocalDateTime.now());

            modalidadeAplicacaoNovo = modalidadeAplicacaoRepository.save(modalidadeAplicacaoNovo);

            return modelMapper.map(modalidadeAplicacaoNovo, ModalidadeAplicaoDto.class);
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) da Modalidade Aplicação não foi(foram) preenchido(s).");
        }
    }

    public ModalidadeAplicaoDto atualizarModalidadeAplicao(ModalidadeAplicaoForm modalidadeAplicaoForm, int id) {
        Optional<ModalidadeAplicacao> modalidadeAplicacaoExistente = modalidadeAplicacaoRepository.findById(id);

        if (modalidadeAplicacaoExistente.isPresent()) {
            ModalidadeAplicacao modalidadeAplicacaoAtualizado = modalidadeAplicacaoExistente.get();
            modalidadeAplicacaoAtualizado.setNome(modalidadeAplicaoForm.getNome());
            modalidadeAplicacaoAtualizado.setDataAlteracao(LocalDateTime.now());
            modalidadeAplicacaoAtualizado = modalidadeAplicacaoRepository.save(modalidadeAplicacaoAtualizado);

            return modelMapper.map(modalidadeAplicacaoAtualizado, ModalidadeAplicaoDto.class);
        } else {
            throw new ObjectNotFoundException("O Código da Modalidade Aplicação não existe na base de dados!");
        }
    }

    public void removerModalidadeAplicao(int id) {
        if (modalidadeAplicacaoRepository.existsById(id)) {
            modalidadeAplicacaoRepository.deleteById(id);
        } else {
            throw new ObjectNotFoundException("O código do Tipo da Modalidade Aplicação não existe na base de dados!");
        }
    }
}
