package br.com.karine.gestao_vagas.modules.company.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.karine.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.karine.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.karine.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret.company}")
    private String secretKey;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCompanyResponseDTO excecute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername())
            .orElseThrow(() -> {
                throw new UsernameNotFoundException("Usuário e/ou senha incorreta.");
            });

        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword()); 
        
        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresIn = Instant.now().plus(Duration.ofHours(2));

        var roles = Arrays.asList("COMPANY");

        var token = JWT.create()
            .withIssuer("gestao_vagas")
            .withExpiresAt(expiresIn)
            .withSubject(company.getId().toString())
            .withClaim("roles", roles)
            .sign(algorithm);

        var authCompanyResponse = AuthCompanyResponseDTO
            .builder()
            .access_token(token)
            .expires_in(expiresIn.toEpochMilli())
            .roles(roles)
            .build();

        return authCompanyResponse;
    }

}
