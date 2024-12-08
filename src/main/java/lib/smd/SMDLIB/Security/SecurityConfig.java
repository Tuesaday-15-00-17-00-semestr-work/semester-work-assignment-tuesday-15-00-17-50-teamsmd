package lib.smd.SMDLIB.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	/*private JWTAuthEntry JWTentry;
	
	@Autowired
	public SecurityConfig(JWTAuthEntry JWTentry) {
		this.JWTentry = JWTentry;
	}*/
	
	@Bean	//REGISTER and LOGIN
	@Order(1)
	public SecurityFilterChain RegisterLogin(HttpSecurity http) throws Exception{
		http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(auth -> auth
					.anyRequest().permitAll()
			)
			.securityMatcher("/auth/**")
			.httpBasic(Customizer.withDefaults());
		
		return http.build();
	}
	
	@Bean	//USER
	@Order(2)
	public SecurityFilterChain UserStuff(HttpSecurity http) throws Exception{
		http
			.csrf(AbstractHttpConfigurer::disable)
			/*.exceptionHandling((exception) -> exception
					.authenticationEntryPoint(JWTentry)
			)*/
			.authorizeHttpRequests(auth -> auth
					.anyRequest().authenticated()
			)
			.sessionManagement(session -> session
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.securityMatcher("/lib/transactions/user/**")
			.securityMatcher("/lib/books/user/**")
			.httpBasic(Customizer.withDefaults());
		http.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean	//ADMIN
	@Order(3)
	public SecurityFilterChain AdminStuff(HttpSecurity http) throws Exception{
		http
			.csrf(AbstractHttpConfigurer::disable)
			/*.exceptionHandling((exception) -> exception
					.authenticationEntryPoint(JWTentry)
			)*/
			.authorizeHttpRequests(auth -> auth
					.requestMatchers("/**").hasRole("Admin")
					.anyRequest().authenticated()
			)
			.sessionManagement(session -> session
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.securityMatcher("/**")
			.httpBasic(Customizer.withDefaults());
		http.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean
	public AuthenticationManager authMang(AuthenticationConfiguration authconf) throws Exception{
		return authconf.getAuthenticationManager();
	}
	
	@Bean
	PasswordEncoder passEnc() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public JWTFilter jwtAuthFilter() {
		return new JWTFilter();
	}
	
}
