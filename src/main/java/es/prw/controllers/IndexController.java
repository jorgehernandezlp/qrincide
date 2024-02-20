package es.prw.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.dsw.connectors.MySqlConnection;
import es.prw.models.Usuario;
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
	 


	    @GetMapping("/usuarios")
	    public String gestionUsuarios(Model model) {    	
	    	
	        System.out.println("Dentro de gestionUsuarios");	        
	        MySqlConnection objMySqlConnection = new MySqlConnection();
			objMySqlConnection.open();		
				
	        List<Usuario> listaUsuarios = new ArrayList<>();
	        try {
	            ResultSet rs = objMySqlConnection.executeSelect("SELECT * FROM Usuarios;");
	            while (rs != null && rs.next()) {
	                Usuario usuario = new Usuario(rs); 
	                listaUsuarios.add(usuario);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            objMySqlConnection.close();
	        }

	        model.addAttribute("usuarios", listaUsuarios);
	        return "usuarios";
	    }
	    
	    @PostMapping(value= "/agregarUsuario")
	    public String agregarUsuario(@RequestParam("nombre") String nombre,
	                                  @RequestParam("contraseña") String contraseña,
	                                  @RequestParam("email") String email,
	                                  @RequestParam("rol") String rol,
	                                  Model model,  RedirectAttributes redirectAttributes) {
	    	System.out.println("Agregar usuario");
	    	MySqlConnection objMySqlConnection = new MySqlConnection();
	    	objMySqlConnection.open();
	        String sql = "INSERT INTO Usuarios (nombre, contraseña, email, rol) VALUES ('" + nombre + "', '" + contraseña + "', '" + email + "', '" + rol + "')";
	        System.out.println(sql);
	        ResultSet rs = objMySqlConnection.executeInsert(sql);
	        if (rs != null) {
	            // Operación exitosa
	        	redirectAttributes.addFlashAttribute("mensaje", "Usuario agregado correctamente.");
	            return "redirect:/usuarios"; // Redirige a la página de usuarios
	        } else {
	            // Error en la operación
	        	redirectAttributes.addFlashAttribute("error", "Error al agregar el usuario. Inténtelo de nuevo.");
	        	return "redirect:/usuarios"; // Página de gestión de usuarios
	        }
	    }
	    
	    @GetMapping("/borrarUsuario")
	    public String borrarUsuario(@RequestParam("idUsuario") String idUsuario) {
	    	System.out.println("Borrar usuario" +idUsuario);
	        return "redirect:/usuarios"; // Redirige a la página de usuarios después del borrado
	    }

}
