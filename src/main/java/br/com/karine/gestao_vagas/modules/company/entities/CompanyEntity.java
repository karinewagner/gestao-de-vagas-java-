package br.com.karine.gestao_vagas.modules.company.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "company")
public class CompanyEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Pattern(regexp = "\\S+", message = "O campo username não deve conter espaço.")
    private String username;

    @Email(message = "O campo email deve conter um e-mail válido.")
    private String email;

    @Length(min = 10, max = 100, message = "O campo senha deve conter entre 10 e 100 caracteres.")
    private String password;
    
    private String name;
    private String description;
    private String website;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
