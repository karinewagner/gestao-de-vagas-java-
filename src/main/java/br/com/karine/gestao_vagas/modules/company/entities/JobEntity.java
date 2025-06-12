package br.com.karine.gestao_vagas.modules.company.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity(name = "job")
public class JobEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    private String description;
    private String level;
    private String benefits;

    @ManyToOne()
    @JoinColumn(name = "company_id") // Por padrão o id é vinculado como chave estrangeira, mas caso quizesse outro campo era só colocar referencedColumnName = "id"
    private CompanyEntity companyEntity;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
