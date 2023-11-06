package br.com.eureka.eureka.service;

import br.com.eureka.eureka.model.FonteRecurso;
import br.com.eureka.eureka.repository.FonteRecursoRepository;
import br.com.eureka.eureka.rest.dto.FonteRecursoDto;
import br.com.eureka.eureka.rest.form.FonteRecursoForm;
import br.com.eureka.eureka.service.exceptions.DataIntegrityException;
import br.com.eureka.eureka.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FonteRecursoService {
    @Autowired
    private FonteRecursoRepository fonteRecursoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public FonteRecursoDto ObterPorId(int id) {
        FonteRecurso fonteRecurso = fonteRecursoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Fonte Recurso não encontrado! Código: " + id + ", Tipo: " + FonteRecurso.class.getName()));

        return modelMapper.map(fonteRecurso, FonteRecursoDto.class);
    }

    public List<FonteRecursoDto> ObterTodos() {
        List<FonteRecurso> fonteRecursoList = fonteRecursoRepository.findAll();
        return fonteRecursoList.stream()
                .map(fonteRecurso -> modelMapper.map(fonteRecurso, FonteRecursoDto.class))
                .collect(Collectors.toList());
    }

    public FonteRecursoDto salvarFonteRecurso(FonteRecursoForm fonteRecursoForm) {
        FonteRecurso fonteRecursoNovo = modelMapper.map(fonteRecursoForm, FonteRecurso.class);
        fonteRecursoNovo.setDataCadastro(LocalDateTime.now());

        return modelMapper.map(fonteRecursoRepository.save(fonteRecursoNovo), FonteRecursoDto.class);
    }

    public FonteRecursoDto AtualizarFonteRecurso(FonteRecursoForm fonteRecursoForm, int id) {
        FonteRecurso fonteRecurso = fonteRecursoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("O Código da Fonte de Recurso não existe na base de dados!"));

        fonteRecurso.setNome(fonteRecursoForm.getNome());
        fonteRecurso.setDataAlteracao(LocalDateTime.now());

        return modelMapper.map(fonteRecursoRepository.save(fonteRecurso), FonteRecursoDto.class);
    }

    public void RemoverFonteRecurso(int id) {
        if (fonteRecursoRepository.existsById(id)) {
            fonteRecursoRepository.deleteById(id);
        } else {
            throw new ObjectNotFoundException("O código da Fonte de Recurso não existe na base de dados!");
        }
    }
}
