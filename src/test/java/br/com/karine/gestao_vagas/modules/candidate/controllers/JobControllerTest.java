package br.com.karine.gestao_vagas.modules.candidate.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.karine.gestao_vagas.exceptions.CompanyNotFoundException;
import br.com.karine.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.karine.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.karine.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.karine.gestao_vagas.utils.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class JobControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CompanyRepository companyRepository;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(SecurityMockMvcConfigurers.springSecurity())
            .build();
    }
    
    @Test
    @DisplayName("deve ser possível criar um novo emprego")
    public void shouldBeAbleToCreateANewJob() throws Exception {

        var company = CompanyEntity
            .builder()
            .description("description_test")
            .email("email@test.com")
            .password("passord_test")
            .username("username_test")
            .name("name_test")
            .build();
        
        company = companyRepository.saveAndFlush(company);

        var createdJobDTO = CreateJobDTO
            .builder()
            .benefits("benefits_test")
            .description("description_test")
            .level("level_test")
            .build();

        mvc.perform(
            MockMvcRequestBuilders
                .post("/company/job/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(createdJobDTO))
                .header("Authorization", TestUtils.generateToken(company.getId(), "GESTAO_VAGAS@2025#COMPANY"))
            ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("não deve ser possível criar uma vaga, se a empresa não for encontrada")
    public void shouldNotBeAbleToCreateANewJobIfCompanyNotFound() throws Exception {
        
        var createdJobDTO = CreateJobDTO
            .builder()
            .benefits("benefits_test")
            .description("description_test")
            .level("level_test")
            .build();


        mvc.perform(
            MockMvcRequestBuilders
                .post("/company/job/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(createdJobDTO))
                .header("Authorization", TestUtils.generateToken(UUID.randomUUID(), "GESTAO_VAGAS@2025#COMPANY"))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());

    }
}
