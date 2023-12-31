package br.com.eureka.eureka.service;

import br.com.eureka.eureka.model.AcaoModel;
import br.com.eureka.eureka.repository.AcaoRepository;
import br.com.eureka.eureka.rest.dto.AcaoDto;
import br.com.eureka.eureka.rest.form.AcaoForm;
import br.com.eureka.eureka.service.exceptions.DataIntegrityException;
import br.com.eureka.eureka.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AcaoService {
    @Autowired
    private AcaoRepository acaoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<AcaoDto> ObterTodos(){
        try {
            List<AcaoModel> acaoModelList = acaoRepository.findAll();

            return acaoModelList.stream()
                    .map(acao -> modelMapper.map(acao, AcaoDto.class))
                    .collect(Collectors.toList());
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Não é possível consultar as Ações!");
        }
    }
    public AcaoDto ObterPorId(int id){
        try {
            AcaoModel acaoModel = acaoRepository.findById(id).get();
            return modelMapper.map(acaoModel, AcaoDto.class);
        } catch (NoSuchElementException e){
            throw new ObjectNotFoundException("Ação não encontrado! Codigo: " + id + ", Tipo: " + AcaoModel.class.getName());
        }
    }

    public AcaoDto salvarAcao(AcaoForm acaoForm) {
        try {
            AcaoModel acaolNovo = modelMapper.map(acaoForm, AcaoModel.class);
            acaolNovo.setDataCadastro(LocalDateTime.now());

            acaolNovo = acaoRepository.save(acaolNovo);

            return modelMapper.map(acaolNovo, AcaoDto.class);

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) da(s) Ação(ões) não foi(foram) preenchido(s).");
        }
    }


    public AcaoDto AtualizarAcao(AcaoForm acaoForm, int id) {
        try {
            Optional<AcaoModel> acaoExistente = acaoRepository.findById(id);

            if (acaoExistente.isPresent()) {
                AcaoModel acaoAtualizado = acaoExistente.get();
                acaoAtualizado.setNome(acaoForm.getNome());
                acaoAtualizado.setDataAlteracao(LocalDateTime.now());
                acaoAtualizado = acaoRepository.save(acaoAtualizado);

                return modelMapper.map(acaoAtualizado, AcaoDto.class);
            }else{
                throw new DataIntegrityException("O Código da Ação não existe na base de dados!");
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Campo(s) obrigatório(s) da Ação  não foi(foram) preenchido(s).");
        }
    }

    public void RemoverAcao(int id) {
        try {
            if (acaoRepository.existsById(id)) {
                acaoRepository.deleteById(id);

            }else {
                throw new DataIntegrityException("O código da Ação não existe na base de dados!");
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir uma Ação!");
        }
    }
}
