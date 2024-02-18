package es.prw.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.dsw.connectors.MySqlConnection;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class IndexController {
	
	@GetMapping(value= {"/","/index"})
	public String index(Model model, Authentication authentication, HttpServletResponse response) {		
		System.out.println("Dentro de index");
		
		if (authentication.isAuthenticated()) {
		//COOKIE FECHA			 
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
				String dateTimeNow = LocalDateTime.now().format(formatter);
				String encodedDateTime;
				encodedDateTime = URLEncoder.encode(dateTimeNow, StandardCharsets.UTF_8.toString());
				Cookie lastAccessCookie = new Cookie("lastAccess", encodedDateTime);
				lastAccessCookie.setMaxAge(7 * 24 * 60 * 60); 
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			
			MySqlConnection objMySqlConnection = new MySqlConnection();
			objMySqlConnection.open();	
			
			String nombreUsuario = authentication.getName();
			model.addAttribute("nombreUsuario", nombreUsuario);
			System.out.println(nombreUsuario);			
		}			
		
					
					
		return "home";		
	}
	
	@GetMapping(value= {"/login"})
	public String login(Model model, HttpServletRequest request) {
		try {
		 Cookie[] cookies = request.getCookies();
		    if (cookies != null) {
		        for (Cookie cookie : cookies) {
		            if ("lastAccess".equals(cookie.getName())) {
		                String decodedDateTime;
						
							decodedDateTime = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8.toString());
					
		                model.addAttribute("lastAccessTime", decodedDateTime);
		                break;
		            }
		        }
		    }
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "login";		
	}
	
	 @GetMapping("/incidencias")
	    public String gestionIncidencias() {
	        return "incidencias"; 
	    }
}
