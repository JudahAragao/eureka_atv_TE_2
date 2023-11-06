package br.com.eureka.eureka.rest.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UnidadeForm {
    @NotEmpty
    @NotBlank(message = "O campo Nome não pode ficar vazio.")
    @Size(max = 255, message = "O Nome deve ter no máximo 255 caracteres.")
    private String nome;
}
