package br.com.eureka.eureka.service;

import br.com.eureka.eureka.model.TipoLancamento;
import br.com.eureka.eureka.repository.TipoLancamentoRepository;
import br.com.eureka.eureka.rest.dto.TipoLancamentoDto;
import br.com.eureka.eureka.rest.form.TipoLancamentoForm;
import br.com.eureka.eureka.service.exceptions.DataIntegrityException;
import br.com.eureka.eureka.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoLancamentoService {

    @Autowired
    private TipoLancamentoRepository tipoLancamentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public TipoLancamentoDto obterPorId(int id) {
        TipoLancamento tipoLancamento = tipoLancamentoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Tipo lançamento não encontrado! Código: " + id + ", Tipo: " + TipoLancamento.class.getName()));
        return modelMapper.map(tipoLancamento, TipoLancamentoDto.class);
    }

    public List<TipoLancamentoDto> obterTodos() {
        List<TipoLancamento> tipoLancamentoList = tipoLancamentoRepository.findAll();
        return tipoLancamentoList.stream()
                .map(tipoLancamento -> modelMapper.map(tipoLancamento, TipoLancamentoDto.class))
                .collect(Collectors.toList());
    }

    public TipoLancamentoDto salvarTipoLancamento(TipoLancamentoForm tipoLancamentoForm) {
        TipoLancamento tipoLancamentoNovo = modelMapper.map(tipoLancamentoForm, TipoLancamento.class);
        tipoLancamentoNovo.setDataCadastro(LocalDateTime.now());
        tipoLancamentoNovo = tipoLancamentoRepository.save(tipoLancamentoNovo);
        return modelMapper.map(tipoLancamentoNovo, TipoLancamentoDto.class);
    }

    public TipoLancamentoDto atualizarTipoLancamento(TipoLancamentoForm tipoLancamentoForm, int id) {
        TipoLancamento tipoLancamento = tipoLancamentoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("O Código do Tipo lançamento não existe na base de dados."));

        tipoLancamento.setNome(tipoLancamentoForm.getNome());
        tipoLancamento.setDataAlteracao(LocalDateTime.now());
        tipoLancamento = tipoLancamentoRepository.save(tipoLancamento);

        return modelMapper.map(tipoLancamento, TipoLancamentoDto.class);
    }

    public void removerTipoLancamento(int id) {
        if (tipoLancamentoRepository.existsById(id)) {
            tipoLancamentoRepository.deleteById(id);
        } else {
            throw new ObjectNotFoundException("O código do Tipo de Lançamento não existe na base de dados.");
        }
    }
}
