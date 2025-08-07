package com.storesecurity.spring_auth.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

	@Configuration
	@EnableWebSecurity
	public class SecurityConfig {

		@Bean
		@Order(1)
		public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
				throws Exception {
			OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
					OAuth2AuthorizationServerConfigurer.authorizationServer();

			http
					.securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
					.with(authorizationServerConfigurer, (authorizationServer) ->
							authorizationServer
									.oidc(Customizer.withDefaults())	// Enable OpenID Connect 1.0
					)
					.authorizeHttpRequests((authorize) ->
							authorize
									.anyRequest().authenticated()
					).cors(cors->cors.configurationSource(corsConfigurationSource()))
					// Redirect to the login page when not authenticated from the
					// authorization endpoint
					.exceptionHandling((exceptions) -> exceptions
							.defaultAuthenticationEntryPointFor(
									new LoginUrlAuthenticationEntryPoint("/login"),
									new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
							)
					);

			return http.build();
		}

		@Bean
		@Order(2)
		public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
				throws Exception {
			http
					.authorizeHttpRequests((authorize) -> authorize
							.anyRequest().authenticated()
					)
					.cors(cors->cors.configurationSource(corsConfigurationSource()))
					// Form login handles the redirect to the login page from the
					// authorization server filter chain
					.formLogin(Customizer.withDefaults());

			return http.build();
		}

		@Bean
		public UserDetailsService userDetailsService() {
			UserDetails userDetails = User.withDefaultPasswordEncoder()
					.username("user")
					.password("password")
					.roles("USER")
					.build();

			return new InMemoryUserDetailsManager(userDetails);
		}

		@Bean
		public RegisteredClientRepository registeredClientRepository() {
			RegisteredClient createClient = RegisteredClient.withId(UUID.randomUUID().toString())
					.clientId("store-security")
					.clientSecret("{noop}VxubZgAXyyTq9lGjj3qGvWNsHtE4SqTq")
					.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
					.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
					.scopes(scope-> scope.addAll(List.of(OidcScopes.OPENID,"ADMIN")))
					.tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofMinutes(10))
							.accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED).build())
					.build();

			RegisteredClient pkceClient = RegisteredClient.withId(UUID.randomUUID().toString())
					.clientId("store-security-pkce")
					.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
					.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
					.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
					.scope(OidcScopes.OPENID).scope(OidcScopes.EMAIL)
					.clientSettings(ClientSettings.builder().requireProofKey(true).build())
					.redirectUri("http://localhost:4200/welcome")
					.tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofMinutes(10))
							.refreshTokenTimeToLive(Duration.ofMinutes(8)).reuseRefreshTokens(false).accessTokenFormat(
									OAuth2TokenFormat.SELF_CONTAINED
							).build()).build();


			return new InMemoryRegisteredClientRepository(createClient,pkceClient);
		}

		@Bean
		public JWKSource<SecurityContext> jwkSource() {
			KeyPair keyPair = generateRsaKey();
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			RSAKey rsaKey = new RSAKey.Builder(publicKey)
					.privateKey(privateKey)
					.keyID(UUID.randomUUID().toString())
					.build();
			JWKSet jwkSet = new JWKSet(rsaKey);
			return new ImmutableJWKSet<>(jwkSet);
		}

		private static KeyPair generateRsaKey() {
			KeyPair keyPair;
			try {
				KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
				keyPairGenerator.initialize(2048);
				keyPair = keyPairGenerator.generateKeyPair();
			}
			catch (Exception ex) {
				throw new IllegalStateException(ex);
			}
			return keyPair;
		}

		@Bean
		public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
			return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
		}

		@Bean
		public AuthorizationServerSettings authorizationServerSettings() {
			return AuthorizationServerSettings.builder().build();
		}

		@Bean
		public CorsConfigurationSource corsConfigurationSource() {
			CorsConfiguration corsConfiguration = new CorsConfiguration();
			corsConfiguration.addAllowedOrigin("http://localhost:4200");
			corsConfiguration.setAllowCredentials(true);
			corsConfiguration.setMaxAge(3600L);
			corsConfiguration.addAllowedHeader("*");
			corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE","PATCH"));
			corsConfiguration.addExposedHeader("Authorization");
			return new CorsConfigurationSource() {
				@Override
				public CorsConfiguration getCorsConfiguration(
						HttpServletRequest request) {
					return corsConfiguration;
				}
			};
		}

	}

