package in.ramit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import in.ramit.service.CustomService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private CustomService customService;
	
	@Autowired
	private CustomAuthSucessHandler customAuthSucessHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(customService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.csrf(c -> c.disable())
				.authorizeHttpRequests(request -> request
						.requestMatchers("/user/**").hasAuthority("EMPLOYEE")
						.requestMatchers("/admin/**").hasAuthority("ADMIN")
						.requestMatchers("/**").permitAll())

				.formLogin(form -> form
						.loginPage("/login").loginProcessingUrl("/dashboard")
						.successHandler(customAuthSucessHandler)
						.permitAll())

				.logout(form -> form
						.invalidateHttpSession(true).clearAuthentication(true)
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.logoutSuccessUrl("/login?logout")
						.permitAll());

		return httpSecurity.build();

	}
}
