package es.prw.app;
import static org.springframework.security.config.Customizer.withDefaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import es.dsw.connectors.MySqlConnection;
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	public static InMemoryUserDetailsManager InMemory;
	@Bean
	SecurityFilterChain securityFIlterChain(HttpSecurity http) throws Exception {
		
		http
			.authorizeHttpRequests((authorize) -> authorize
									.requestMatchers("/styles/**").permitAll()
									.requestMatchers("/bootstrap/**").permitAll()
									.requestMatchers("/js/**").permitAll()
									.requestMatchers("/img/**").permitAll()			
									.requestMatchers("/buscarEquipos").permitAll()
									.anyRequest().authenticated()
									)
			.httpBasic(withDefaults())
			.formLogin(form -> form
								.loginPage("/login")
								.loginProcessingUrl("/logginprocess")
								.defaultSuccessUrl("/index", true) 
								.permitAll()
								)
			.logout((logout) -> logout.logoutSuccessUrl("/login?logout").permitAll());
			;
			
			
		
		
		
		
		return http.build();		
	}
	@Bean
	InMemoryUserDetailsManager userDetailsService() {
		MySqlConnection objMySqlConnection = new MySqlConnection();
		objMySqlConnection.open();		
		
		if (!objMySqlConnection.isError()) {
		    ResultSet result = objMySqlConnection.executeSelect("SELECT * FROM Usuarios;");

		    InMemory = new InMemoryUserDetailsManager();
		    try {		    	
		        while (result.next()) {
		        	
		        	String[] authorities = Arrays.stream(result.getString("Rol").split(",\\s*"))
                            .map(role -> "ROLE_" + role)
                            .toArray(String[]::new);
		        	
		        	String userName = result.getString("Nombre");
		        	String pass = result.getString("Contraseña");	
		        	System.out.println("Nombre de usuario: " + userName);
		        	System.out.println("Contraseña: " + pass);	
		            System.out.println("Rol: "+result.getString("Rol"));
		            
		            System.out.println("------------------");
		            
		            @SuppressWarnings("deprecation")
		            UserDetails user = User.withDefaultPasswordEncoder()
                    .username(userName)
                    .password(pass)
                    .authorities(authorities) 
                    .build();	            
		           
		    	
		    		InMemory.createUser(user);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        objMySqlConnection.close();
		    }
		}
		
		
		return InMemory;
	}
	
}
