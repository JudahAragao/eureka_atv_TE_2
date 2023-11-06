package br.com.eureka.eureka.service;

import br.com.eureka.eureka.model.ElementoDespesa;
import br.com.eureka.eureka.repository.ElementoDespesaRepository;
import br.com.eureka.eureka.rest.dto.ElementoDespesaDto;
import br.com.eureka.eureka.rest.form.ElementoDespesaForm;
import br.com.eureka.eureka.service.exceptions.DataIntegrityException;
import br.com.eureka.eureka.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElementoDespesaService {
    @Autowired
    private ElementoDespesaRepository elementoDespesaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ElementoDespesaDto ObterPorId(int id) {
        ElementoDespesa elementoDespesa = elementoDespesaRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Elemento Despesa não encontrado! Código: " + id + ", Tipo: " + ElementoDespesa.class.getName()));

        return modelMapper.map(elementoDespesa, ElementoDespesaDto.class);
    }

    public List<ElementoDespesaDto> ObterTodos() {
        List<ElementoDespesa> elementoDespesaList = elementoDespesaRepository.findAll();
        return elementoDespesaList.stream()
                .map(elementoDespesa -> modelMapper.map(elementoDespesa, ElementoDespesaDto.class))
                .collect(Collectors.toList());
    }

    public ElementoDespesaDto SalvarElementoDespesa(ElementoDespesaForm elementoDespesaForm) {
        ElementoDespesa elementoDespesaNovo = modelMapper.map(elementoDespesaForm, ElementoDespesa.class);
        elementoDespesaNovo.setDataCadastro(LocalDateTime.now());

        return modelMapper.map(elementoDespesaRepository.save(elementoDespesaNovo), ElementoDespesaDto.class);
    }

    public ElementoDespesaDto AtualizarElementoDespesa(ElementoDespesaForm elementoDespesaForm, int id) {
        if (elementoDespesaRepository.existsById(id)) {
            ElementoDespesa elementoDespesaAtualizado = elementoDespesaRepository.findById(id).get();
            elementoDespesaAtualizado.setNome(elementoDespesaForm.getNome());
            elementoDespesaAtualizado.setCodigo(elementoDespesaForm.getCodigo());
            elementoDespesaAtualizado.setDataAlteracao(LocalDateTime.now());

            return modelMapper.map(elementoDespesaRepository.save(elementoDespesaAtualizado), ElementoDespesaDto.class);
        } else {
            throw new ObjectNotFoundException("O Código do Elemento Despesa não existe na base de dados!");
        }
    }

    public void RemoverElementoDespesa(int id) {
        if (elementoDespesaRepository.existsById(id)) {
            elementoDespesaRepository.deleteById(id);
        } else {
            throw new ObjectNotFoundException("O código do Elemento Despesa não existe na base de dados!");
        }
    }
}