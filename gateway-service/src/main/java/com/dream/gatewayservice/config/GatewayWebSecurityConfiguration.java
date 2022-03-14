package com.dream.gatewayservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter.Mode;

@Order(99)
@Configuration
@EnableWebFluxSecurity
public class GatewayWebSecurityConfiguration {
	
	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, ReactiveClientRegistrationRepository clientRegistrationRepository) {
	// Authenticate through configured keycloak SSO Provider
	http.oauth2Login();
	// Also logout at the keycloak SSO Connect Provider
	http.logout(logout -> logout.logoutSuccessHandler(new OidcClientInitiatedServerLogoutSuccessHandler(clientRegistrationRepository)));
	// Require authentication for all request
	http.authorizeExchange().anyExchange().authenticated();
	// Allow showing /home within a frame
	http.headers().frameOptions().mode(Mode.SAMEORIGIN);
	// Disable CSRF in the gateway to prevent conflicts with proxied server CSRF
	http.csrf().disable();
	
	return http.build();
	}
}
