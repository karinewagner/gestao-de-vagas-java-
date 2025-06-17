package br.com.karine.gestao_vagas.modules.candidate.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCandidateResponseDTO {

    private UUID id;

    @Schema(example = "maria")
    private String username;

    @Schema(example = "maria de souza")
    private String name;

    @Schema(example = "maria@email.com")
    private String email;

    @Schema(example = "desenvolvedora java")
    private String description;
    
}
