package br.com.karine.gestao_vagas.modules.company.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.karine.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.karine.gestao_vagas.modules.company.entities.JobEntity;
import br.com.karine.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/job")
public class JobController {
    
    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {
        var companyId = request.getAttribute("company_id");

        // Cria uma instância de jobEntity
        var jobEntity = JobEntity.builder()
            .benefits(createJobDTO.getBenefits())
            .description(createJobDTO.getDescription())
            .level(createJobDTO.getLevel())
            .companyId(UUID.fromString(companyId.toString()))
            .build();

        try {
            var result = this.createJobUseCase.execute(jobEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
