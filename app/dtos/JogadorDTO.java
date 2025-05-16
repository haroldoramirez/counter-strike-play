package dtos;

import play.api.data.validation.ValidationError;
import play.data.validation.Constraints;
import play.libs.Scala;

import java.util.ArrayList;
import java.util.List;

public class JogadorDTO implements Constraints.Validatable<List<ValidationError>> {

    @Constraints.Required(message = "O nome é obrigatório.")
    @Constraints.MaxLength(value = 20, message = "O nome deve ter no máximo 20 caracteres.")
    private String nome;

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

        if (nome != null && nome.trim().equalsIgnoreCase("admin")) {
            errors.add(new ValidationError(Scala.toSeq(List.of("nome")), Scala.toSeq(List.of("O nome 'admin' não é permitido."))));
        }

        return errors.isEmpty() ? null : errors;
    }

}