package br.com.eureka.eureka.service;

import br.com.eureka.eureka.model.UnidadeModel;
import br.com.eureka.eureka.repository.UnidadeOrcamentariaRepository;
import br.com.eureka.eureka.rest.dto.UnidadeOrcamentariaDto;
import br.com.eureka.eureka.rest.form.UnidadeOrcamentariaForm;
import br.com.eureka.eureka.service.exceptions.DataIntegrityException;
import br.com.eureka.eureka.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnidadeOrcamentariaService {

    @Autowired
    private UnidadeOrcamentariaRepository unidadeOrcamentariaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UnidadeOrcamentariaDto obterPorId(int id) {
        UnidadeModel unidadeModel = unidadeOrcamentariaRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Unidade não encontrada. Código: " + id));

        return modelMapper.map(unidadeModel, UnidadeOrcamentariaDto.class);
    }

    public List<UnidadeOrcamentariaDto> obterTodos() {
        List<UnidadeModel> unidadeModelList = unidadeOrcamentariaRepository.findAll();
        return unidadeModelList.stream()
                .map(unidadeModel -> modelMapper.map(unidadeModel, UnidadeOrcamentariaDto.class))
                .collect(Collectors.toList());
    }

    public UnidadeOrcamentariaDto salvarUnidadeModel(UnidadeOrcamentariaForm unidadeOrcamentariaForm) {
        UnidadeModel unidadeOrcamentariaNova = modelMapper.map(unidadeOrcamentariaForm, UnidadeModel.class);
        unidadeOrcamentariaNova.setDataCadastro(LocalDateTime.now());

        unidadeOrcamentariaNova = unidadeOrcamentariaRepository.save(unidadeOrcamentariaNova);

        return modelMapper.map(unidadeOrcamentariaNova, UnidadeOrcamentariaDto.class);
    }

    public UnidadeOrcamentariaDto atualizarUnidadeModel(UnidadeOrcamentariaForm unidadeOrcamentariaForm, int id) {
        UnidadeModel unidadeModel = unidadeOrcamentariaRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("O Código da Unidade Orcamentaria não existe na base de dados."));

        unidadeModel.setNome(unidadeOrcamentariaForm.getNome());
        unidadeModel.setCodigo(unidadeOrcamentariaForm.getCodigo()); // Adicionando atualização do código
        unidadeModel.setDataAlteracao(LocalDateTime.now());
        unidadeModel = unidadeOrcamentariaRepository.save(unidadeModel);

        return modelMapper.map(unidadeModel, UnidadeOrcamentariaDto.class);
    }

    public void removerUnidadeModel(int id) {
        if (unidadeOrcamentariaRepository.existsById(id)) {
            unidadeOrcamentariaRepository.deleteById(id);
        } else {
            throw new ObjectNotFoundException("O Código da Unidade Orcamentaria não existe na base de dados.");
        }
    }
}
