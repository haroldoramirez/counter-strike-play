package dtos;

import ch.qos.logback.core.util.StringUtil;
import play.api.data.validation.ValidationError;
import play.data.validation.Constraints;
import play.libs.Scala;

import java.util.ArrayList;
import java.util.List;

public class JogadorDTO implements Constraints.Validatable<List<ValidationError>> {

    @Constraints.Required(message = "O nome é obrigatório")
    @Constraints.MaxLength(value = 60, message = "O nome deve ter no máximo 60 caracteres")
    public String nome = "";

    /** Necessario para instanciar o form */
    public JogadorDTO() {}

    public JogadorDTO(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public List<ValidationError> validate() {

        List<ValidationError> errors = new ArrayList<>();

        if (StringUtil.notNullNorEmpty(nome)) {
            errors.add(new ValidationError(Scala.toSeq(List.of("nome")), Scala.toSeq(List.of("Preencha um nome válido"))));
        } else if (nome.length() > 60) {
            errors.add(new ValidationError(Scala.toSeq(List.of("nome")), Scala.toSeq(List.of("O nome não pode ter mais que 60 caracteres"))));
        }

        if (nome != null && nome.trim().equalsIgnoreCase("admin")) {
            errors.add(new ValidationError(Scala.toSeq(List.of("nome")), Scala.toSeq(List.of("O nome 'admin' não é permitido."))));
        }

        return errors.isEmpty() ? null : errors;

    }

    public List<ValidationError> validate2() {

        List<ValidationError> errors = new ArrayList<>();

        if (StringUtil.notNullNorEmpty(nome)) {
            errors.add(new ValidationError(Scala.toSeq(List.of("nome")), Scala.toSeq(List.of("Preencha um nome válido"))));
        } else if (nome.length() > 60) {
            errors.add(new ValidationError(Scala.toSeq(List.of("nome")), Scala.toSeq(List.of("O nome não pode ter mais que 60 caracteres"))));
        }

        return errors.isEmpty() ? null : errors;

    }

}