package com.epam.esm.configuration;

import com.epam.esm.exception.EntityExceptionHandler;
import com.epam.esm.security.jwt.CustomJwtAuthenticationConverter;
import com.epam.esm.security.jwt.JwtAudienceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;

@Configuration
@EnableResourceServer
@RequiredArgsConstructor
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    private final EntityExceptionHandler entityExceptionHandler;
    private final CustomJwtAuthenticationConverter jwtAuthenticationConverter;
    private final KeyPair keyPair;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId("api");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(authorizeRequests -> {
                    //Allows to restrict access based on HttpServletRequest using RequestMatcher implementations (i.e. via URL patterns)
                    authorizeRequests
                            .antMatchers(HttpMethod.GET, "/api/tags/most-used", "/api/orders", "/api/orders/{\\d}", "/api/users").hasRole("ADMIN");
                    authorizeRequests
                            .antMatchers(HttpMethod.POST, "/api/certificates", "/api/tags", "/api/certificates/{\\d}/tags").hasRole("ADMIN");
                    authorizeRequests
                            .antMatchers(HttpMethod.POST, "/api/orders").hasAnyRole("ADMIN", "USER");
                    authorizeRequests
                            .antMatchers(HttpMethod.DELETE, "/api/certificates/{\\d}", "/api/tags/{\\d}").hasRole("ADMIN");
                    authorizeRequests
                            .antMatchers(HttpMethod.GET, "/api/certificates", "api/certificates/{\\d+}", "/api/tags", "api/tags/{\\d}").permitAll();
                    authorizeRequests
                            .antMatchers(HttpMethod.POST, "/api/users/signup").anonymous();
                })
                .exceptionHandling(exceptionHandling -> {
                    exceptionHandling.authenticationEntryPoint(entityExceptionHandler);
                    exceptionHandling.accessDeniedHandler(entityExceptionHandler);
                })
                .oauth2ResourceServer() //Configures OAuth 2 resource server support
                .jwt()                  //includes support for the Jwt token
                .jwtAuthenticationConverter(jwtAuthenticationConverter); //configuring conversion from Jwt to Authentication
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withPublicKey((RSAPublicKey) keyPair.getPublic()).build();
        OAuth2TokenValidator<Jwt> customAccessTokenValidator = new DelegatingOAuth2TokenValidator<>(
                JwtValidators.createDefaultWithIssuer(issuerUri),
                new JwtAudienceValidator("api")
        );
        jwtDecoder.setJwtValidator(customAccessTokenValidator);
        return jwtDecoder;
    }

}
