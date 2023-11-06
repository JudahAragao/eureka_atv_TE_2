package br.com.eureka.eureka.service;

import br.com.eureka.eureka.model.GrupoDespesa;
import br.com.eureka.eureka.repository.GrupoDespesaRepository;
import br.com.eureka.eureka.rest.dto.GrupoDespesaDto;
import br.com.eureka.eureka.rest.form.GrupoDespesaForm;
import br.com.eureka.eureka.service.exceptions.DataIntegrityException;
import br.com.eureka.eureka.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GrupoDespesaService {

    @Autowired
    private GrupoDespesaRepository grupoDespesaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public GrupoDespesaDto obterPorId(int id) {
        GrupoDespesa grupoDespesa = grupoDespesaRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Grupo de Despesa não encontrado! Código: " + id));

        return modelMapper.map(grupoDespesa, GrupoDespesaDto.class);
    }

    public List<GrupoDespesaDto> obterTodos() {
        List<GrupoDespesa> grupoDespesaList = grupoDespesaRepository.findAll();
        return grupoDespesaList.stream()
                .map(grupoDespesa -> modelMapper.map(grupoDespesa, GrupoDespesaDto.class))
                .collect(Collectors.toList());
    }

    public GrupoDespesaDto salvarGrupoDespesa(GrupoDespesaForm grupoDespesaForm) {
        GrupoDespesa novoGrupoDespesa = modelMapper.map(grupoDespesaForm, GrupoDespesa.class);
        novoGrupoDespesa.setDataCadastro(LocalDateTime.now());

        return modelMapper.map(grupoDespesaRepository.save(novoGrupoDespesa), GrupoDespesaDto.class);
    }

    public GrupoDespesaDto atualizarGrupoDespesa(GrupoDespesaForm grupoDespesaForm, int id) {
        GrupoDespesa grupoDespesa = grupoDespesaRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("O Código do Grupo de Despesa não existe na base de dados."));

        grupoDespesa.setNome(grupoDespesaForm.getNome());
        grupoDespesa.setCodigo(grupoDespesaForm.getCodigo());
        grupoDespesa.setDataAlteracao(LocalDateTime.now());

        return modelMapper.map(grupoDespesaRepository.save(grupoDespesa), GrupoDespesaDto.class);
    }

    public void removerGrupoDespesa(int id) {
        if (grupoDespesaRepository.existsById(id)) {
            grupoDespesaRepository.deleteById(id);
        } else {
            throw new ObjectNotFoundException("O Código do Grupo de Despesa não existe na base de dados.");
        }
    }
}
