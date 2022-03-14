package com.dream.orderservice.config;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Override
	  protected void configure(HttpSecurity http) throws Exception {
	    // Validate tokens through configured OpenID Provider
	    http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());
	    // Require authentication for all requests
	    http.authorizeRequests().anyRequest().authenticated();
	    // Allow showing pages within a frame
	    http.headers().frameOptions().sameOrigin();
	  }

	  private JwtAuthenticationConverter jwtAuthenticationConverter() {
	    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
	    // Convert realm_access.roles claims to granted authorities, for use in access decisions
	    converter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter());
	    return converter;
	  }

	  class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
	    @Override
	    public Collection<GrantedAuthority> convert(Jwt jwt) {
	      final Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");
	      return ((List<String>) realmAccess.get("roles")).stream()
	        .map(roleName -> "ROLE_" + roleName)
	        .map(SimpleGrantedAuthority::new)
	        .collect(Collectors.toList());
	    }
	  }
	  
}
