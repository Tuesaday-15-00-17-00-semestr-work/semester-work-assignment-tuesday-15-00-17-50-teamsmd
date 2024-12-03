package lib.smd.SMDLIB.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
	
	@Bean
	public SecurityFilterChain filterchain(HttpSecurity http) throws Exception{
		http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(auth -> auth
					.anyRequest().permitAll()
			)
			.securityMatcher("/auth/**")
			.httpBasic(Customizer.withDefaults());
		
		return http.build();
	}
	
	/*@Bean
	public UserDetailsService users() {
		UserDetails admin = User.withUsername("admin").password("123").roles("ADMIN").build();
		UserDetails user = User.withUsername("user").password("456").roles("USER").build();
		return new InMemoryUserDetailsManager(admin, user);
	}*/
	
	@Bean //????????????????
	public AuthenticationManager authMang(AuthenticationConfiguration authconf) throws Exception{
		return authconf.getAuthenticationManager();
	}
	
	@Bean
	PasswordEncoder passEnc() {
		return new BCryptPasswordEncoder();
	}
	
}
