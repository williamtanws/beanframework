package com.beanframework.api.config;

import java.util.HashSet;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;

/**
 * {@link Configuration} for OAuth 2.0 Authorization Server support.
 *
 * @author Joe Grandja
 * @since 0.0.1
 * @see OAuth2AuthorizationServerConfigurer
 */
@Configuration(proxyBeanMethods = false)
public class OAuth2AuthorizationServerConfiguration {

  @Bean
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
      throws Exception {
    applyDefaultSecurity(http);
    return http.build();
  }

  // @formatter:off
    public static void applyDefaultSecurity(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfigurer<HttpSecurity> authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer<>();
        RequestMatcher endpointsMatcher = authorizationServerConfigurer
                .getEndpointsMatcher();

        http
            .requestMatcher(endpointsMatcher)
            .authorizeRequests(authorizeRequests ->
                authorizeRequests.anyRequest().authenticated()
            )
            .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
            .apply(authorizationServerConfigurer);
    }
    // @formatter:on

  @Bean
  public static JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
    Set<JWSAlgorithm> jwsAlgs = new HashSet<>();
    jwsAlgs.addAll(JWSAlgorithm.Family.RSA);
    jwsAlgs.addAll(JWSAlgorithm.Family.EC);
    jwsAlgs.addAll(JWSAlgorithm.Family.HMAC_SHA);
    ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
    JWSKeySelector<SecurityContext> jwsKeySelector =
        new JWSVerificationKeySelector<>(jwsAlgs, jwkSource);
    jwtProcessor.setJWSKeySelector(jwsKeySelector);
    // Override the default Nimbus claims set verifier as NimbusJwtDecoder handles it instead
    jwtProcessor.setJWTClaimsSetVerifier((claims, context) -> {
    });
    return new NimbusJwtDecoder(jwtProcessor);
  }

}
