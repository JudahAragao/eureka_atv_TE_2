package br.com.eureka.eureka.service;

import br.com.eureka.eureka.model.TipoTransacao;
import br.com.eureka.eureka.repository.TipoTransacaoRepository;
import br.com.eureka.eureka.rest.dto.TipoTransacaoDto;
import br.com.eureka.eureka.rest.form.TipoTransacaoForm;
import br.com.eureka.eureka.service.exceptions.DataIntegrityException;
import br.com.eureka.eureka.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoTransacaoService {

    @Autowired
    private TipoTransacaoRepository tipoTransacaoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public TipoTransacaoDto obterPorId(int id) {
        TipoTransacao tipoTransacao = tipoTransacaoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Tipo Transação não encontrado! Código: " + id));

        return modelMapper.map(tipoTransacao, TipoTransacaoDto.class);
    }

    public List<TipoTransacaoDto> obterTodos() {
        List<TipoTransacao> tipoTransacaoList = tipoTransacaoRepository.findAll();
        return tipoTransacaoList.stream()
                .map(tipoTransacao -> modelMapper.map(tipoTransacao, TipoTransacaoDto.class))
                .collect(Collectors.toList());
    }

    public TipoTransacaoDto salvarTipoTransacao(TipoTransacaoForm tipoTransacaoForm) {
        TipoTransacao tipoTransacaoNovo = modelMapper.map(tipoTransacaoForm, TipoTransacao.class);
        tipoTransacaoNovo.setDataCadastro(LocalDateTime.now());

        tipoTransacaoNovo = tipoTransacaoRepository.save(tipoTransacaoNovo);

        return modelMapper.map(tipoTransacaoNovo, TipoTransacaoDto.class);
    }

    public TipoTransacaoDto atualizarTipoTransacao(TipoTransacaoForm tipoTransacaoForm, int id) {
        TipoTransacao tipoTransacao = tipoTransacaoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("O Código do Tipo Transação não existe na base de dados."));

        tipoTransacao.setNome(tipoTransacaoForm.getNome());
        tipoTransacao.setDataAlteracao(LocalDateTime.now());
        tipoTransacao = tipoTransacaoRepository.save(tipoTransacao);

        return modelMapper.map(tipoTransacao, TipoTransacaoDto.class);
    }

    public void removerTipoTransacao(int id) {
        if (tipoTransacaoRepository.existsById(id)) {
            tipoTransacaoRepository.deleteById(id);
        } else {
            throw new ObjectNotFoundException("O Código do Tipo Transação não existe na base de dados.");
        }
    }
}
