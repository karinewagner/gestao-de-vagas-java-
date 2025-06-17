package br.com.karine.gestao_vagas.modules.candidate.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity(name = "candidate")
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Pattern(regexp = "\\S+", message = "O campo username não deve conter espaço.")
    @Schema(example = "maria")
    private String username;

    @Email(message = "O campo email deve conter um e-mail válido.")
    @Schema(example = "maria@email.com", description = "E-mail do candidato")
    private String email;

    @Length(min = 10, max = 100, message = "O campo senha deve conter entre 10 e 100 caracteres.")
    @Schema(example = "maria@1234", minLength = 10, maxLength = 100, requiredMode = RequiredMode.REQUIRED)
    private String password;
    
    @Schema(example = "maria de souza")
    private String name;

    @Schema(example = "desenvolvedora java junior")
    private String description;

    private String curriculum;

    @CreationTimestamp
    private LocalDateTime createdAt;
    
}
