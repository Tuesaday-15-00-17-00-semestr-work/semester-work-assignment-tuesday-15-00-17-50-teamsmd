package lib.smd.SMDLIB.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private CustomUserDetailsService userDetSer;
	
	@Autowired
	public SecurityConfig(CustomUserDetailsService userDetSer) {
		this.userDetSer = userDetSer;
	}
	
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
	
	@Bean	//ADMIN
	@Order(2)
	public SecurityFilterChain AdminStuff(HttpSecurity http) throws Exception{
		http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(auth -> auth
					.requestMatchers("/**").hasRole("Admin")
					.anyRequest().authenticated()
			)
			.securityMatcher("/**")
			.httpBasic(Customizer.withDefaults());
		
		return http.build();
	}
	
	@Bean	//USER
	@Order(3)
	public SecurityFilterChain UserStuff(HttpSecurity http) throws Exception{
		http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(auth -> auth
					.anyRequest().authenticated()
			)
			.securityMatcher("/lib/transactions/user/**")
			.securityMatcher("/lib/books/user/**")
			.httpBasic(Customizer.withDefaults());
		
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
	
}
