package br.com.eureka.eureka.rest.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class TipoTransacaoForm {
    @NotEmpty
    @NotBlank(message = "O campo Nome não pode ficar vazio.")
    @Size(max = 255)
    private String nome;
}
