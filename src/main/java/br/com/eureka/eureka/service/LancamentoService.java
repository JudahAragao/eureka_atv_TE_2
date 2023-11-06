package br.com.eureka.eureka.service;

import br.com.eureka.eureka.model.Lancamento;
import br.com.eureka.eureka.repository.LancamentoRepository;
import br.com.eureka.eureka.rest.dto.LancamentoDto;
import br.com.eureka.eureka.rest.form.LancamentoForm;
import br.com.eureka.eureka.rest.form.LancamentoUpdateForm;
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
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<LancamentoDto> obterTodos() {
        List<Lancamento> listaLancamento = lancamentoRepository.findLancamentos();
        return listaLancamento.stream()
                .map(lancamento -> modelMapper.map(lancamento, LancamentoDto.class))
                .collect(Collectors.toList());
    }

    public LancamentoDto obterPorId(int id) {
        Lancamento lancamento = lancamentoRepository.findLancamentosPorId(id);
        if (lancamento == null) {
            throw new ObjectNotFoundException("Lançamento não encontrado. Código: " + id + ", Tipo: " + Lancamento.class.getName());
        }
        return modelMapper.map(lancamento, LancamentoDto.class);
    }

    public LancamentoDto salvarLancamento(LancamentoForm lancamentoForm) {
        try {
            Lancamento novoLancamento = modelMapper.map(lancamentoForm, Lancamento.class);
            novoLancamento.setDataCadastro(LocalDateTime.now());

            novoLancamento = lancamentoRepository.save(novoLancamento);
            return modelMapper.map(novoLancamento, LancamentoDto.class);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("FK")) {
                throw new DataIntegrityException("Ocorreu uma violação de chave estrangeira.");
            }
            throw new DataIntegrityException("Falta preenchimento de campo(s) obrigatório(s) do Lançamento.");
        }
    }

    public LancamentoDto atualizarLancamento(LancamentoUpdateForm lancamentoUpdateForm, int id) {
        Optional<Lancamento> lancamentoExistente = lancamentoRepository.findById(id);

        if (lancamentoExistente.isPresent()) {
            Lancamento lancamentoAtualizado = lancamentoExistente.get();
            lancamentoAtualizado = modelMapper.map(lancamentoUpdateForm, Lancamento.class);
            lancamentoAtualizado.setDataAlteracao(LocalDateTime.now());
            lancamentoAtualizado = lancamentoRepository.save(lancamentoAtualizado);
            return modelMapper.map(lancamentoAtualizado, LancamentoDto.class);
        } else {
            throw new ObjectNotFoundException("O Código do Lançamento não existe na base de dados.");
        }
    }

    public void removerLancamento(int id) {
        if (lancamentoRepository.existsById(id)) {
            lancamentoRepository.deleteById(id);
        } else {
            throw new ObjectNotFoundException("O código do Lançamento não existe na base de dados.");
        }
    }
}
