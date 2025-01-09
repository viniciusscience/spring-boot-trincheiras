package academy.devdojo.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.NullRememberMeServices;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, PersistentTokenBasedRememberMeServices rememberMeServices) throws Exception {

        return http.cors(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .maximumSessions(1) .expiredUrl("/session-expired"))
                .authorizeHttpRequests(it -> it.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .rememberMe((remember) -> remember
                        .rememberMeServices(rememberMeServices)
                )
                .build(); //
    }


    @Bean
    public PersistentTokenRepository persistentTokenRepository(DataSource dataSource) {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource); // Configuração do banco de dados
        return tokenRepository;
    }

    @Bean
    public PersistentTokenBasedRememberMeServices rememberMeServices(UserDetailsService userDetailsService, PersistentTokenRepository tokenRepository) {
        PersistentTokenBasedRememberMeServices rememberMeServices = new PersistentTokenBasedRememberMeServices(
                "myKey", // Chave secreta para geração do token
                userDetailsService, // Serviço de autenticação
                tokenRepository // Repositório para persistir tokens
        );
        rememberMeServices.setTokenValiditySeconds(86400); // Validade do token (1 dia)
        return rememberMeServices;
    }

    @Component
    public static class CustomRememberMe extends NullRememberMeServices {

        @Override
        public Authentication autoLogin(HttpServletRequest request, HttpServletResponse response) {
            return null;
        }

        @Override
        public void loginFail(HttpServletRequest request, HttpServletResponse response) {

        }

        @Override
        public void loginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
            System.out.println("DEU BOA");
        }
    }
}
