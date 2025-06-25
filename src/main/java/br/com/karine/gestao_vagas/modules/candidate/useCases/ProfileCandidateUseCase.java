package br.com.karine.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.karine.gestao_vagas.exceptions.UserNotFoundException;
import br.com.karine.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.karine.gestao_vagas.modules.candidate.repositories.CandidateRepository;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute(UUID candidateId) {
        var candidate = this.candidateRepository.findById(candidateId)
            .orElseThrow(() -> {
                throw new UserNotFoundException();
            });
        
        var candidateDTO = ProfileCandidateResponseDTO
            .builder()
            .id(candidate.getId())
            .username(candidate.getUsername())
            .name(candidate.getName())
            .email(candidate.getEmail())
            .description(candidate.getDescription())
            .build();

        return candidateDTO;
    }
    
}
