package br.com.karine.gestao_vagas.modules.candidate.useCases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.karine.gestao_vagas.exceptions.JobNotFoundException;
import br.com.karine.gestao_vagas.exceptions.UserNotFoundException;
import br.com.karine.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.karine.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.karine.gestao_vagas.modules.candidate.repositories.ApplyJobRepository;
import br.com.karine.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.karine.gestao_vagas.modules.company.entities.JobEntity;
import br.com.karine.gestao_vagas.modules.company.repositories.JobRepository;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ApplyJobRepository applyJobRepository;

    @Test
    @DisplayName("não deve ser possível se candidatar a uma vaga, com candidato não encontrado")
    public void shoudNotBeAbleToApllyJobWithCandidateNotFound() {

        try {
            applyJobCandidateUseCase.execute(null, null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(UserNotFoundException.class);
        }
    }
    
    @Test
    @DisplayName("não deve ser possível se candidatar a uma vaga, com vaga não encontrado")
    public void shoudNotBeAbleToApllyJobWithJobNotFound() {

        var candidateId = UUID.randomUUID();
        var candidate = new CandidateEntity();
        candidate.setId(candidateId);

        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));

        try {
            applyJobCandidateUseCase.execute(candidateId, null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(JobNotFoundException.class);
        }
    }

    @Test
    @DisplayName("deve ser possível se candidatar a uma vaga existente, com um candidato existente")
    public void shouldBeAbleToCreateANewApplyJob() {

        var candidateId = UUID.randomUUID();
        var jobId = UUID.randomUUID();

        var applyJob = ApplyJobEntity.builder()
            .candidateId(candidateId)
            .jobId(jobId)
            .build();

        var applyJobCreate = ApplyJobEntity.builder().id(UUID.randomUUID()).build();

        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(new CandidateEntity()));
        when(jobRepository.findById(jobId)).thenReturn(Optional.of(new JobEntity()));

        when(applyJobRepository.save(applyJob)).thenReturn(applyJobCreate);

        var result = applyJobCandidateUseCase.execute(candidateId, jobId);

        assertThat(result).hasFieldOrProperty("id");
        assertNotNull(result.getId());
    }
}
