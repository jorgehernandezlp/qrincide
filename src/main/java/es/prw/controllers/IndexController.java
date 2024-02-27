package es.prw.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.dsw.connectors.MySqlConnection;
import es.prw.models.CustomUserDetails;
import es.prw.models.Equipo;
import es.prw.models.Incidencia;
import es.prw.models.Usuario;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Controller
public class IndexController {
	
	private final InMemoryUserDetailsManager InMemoryUserDetailsManager;

	public IndexController(InMemoryUserDetailsManager inMemoryUserDetailsManager) {
	    this.InMemoryUserDetailsManager = inMemoryUserDetailsManager;
	}
	
	
	
	@GetMapping(value = { "/", "/index" })
	public String index(Model model, Authentication authentication, HttpServletResponse response) {

		if (authentication.isAuthenticated()) {
			String nombreUsuario = authentication.getName();
			int idUsuario = 0;
			MySqlConnection objMySqlConnection = new MySqlConnection();
			objMySqlConnection.open();
			List<Incidencia> listaIncidencias = new ArrayList<>();
			
			try {
				String query;
				String queryIdUsuario = "SELECT * FROM db_inside.usuarios WHERE Nombre = '"+nombreUsuario+"'";
				ResultSet rsID = objMySqlConnection.executeSelect(queryIdUsuario);
				while (rsID != null && rsID.next()) {
					Usuario usuario = new Usuario(rsID);	
					idUsuario = usuario.getIdUsuario();
				}
				
				Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
				
				for (GrantedAuthority authority : authorities) {
					System.out.println("Inicio de sesión del usuario: '" + nombreUsuario + "' con rol: "
							+ authority.getAuthority());
				}

				if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_Administrador")
						|| auth.getAuthority().equals("ROLE_Soporte"))) {
					query = "SELECT Incidencias.*, Usuarios.Nombre AS UsuarioNombre, Equipos.Marca AS EquipoMarca, Equipos.Modelo AS EquipoModelo "
							+ "FROM Incidencias " + "JOIN Usuarios ON Incidencias.ID_Usuario = Usuarios.ID_Usuario "
							+ "JOIN Equipos ON Incidencias.ID_Equipo = Equipos.ID_Equipo;";
				} else {					
					
					query = "SELECT Incidencias.*, Usuarios.Nombre AS UsuarioNombre, Equipos.Marca AS EquipoMarca, Equipos.Modelo AS EquipoModelo "
							+ "FROM Incidencias " + "JOIN Usuarios ON Incidencias.ID_Usuario = Usuarios.ID_Usuario "
							+ "JOIN Equipos ON Incidencias.ID_Equipo = Equipos.ID_Equipo "
							+ "WHERE Incidencias.ID_Usuario = " + idUsuario + ";";
				}
				
				ResultSet rs = objMySqlConnection.executeSelect(query);
				
				while (rs != null && rs.next()) {
					Incidencia incidencia = new Incidencia(rs);
					listaIncidencias.add(incidencia);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				objMySqlConnection.close();
			}
			int totalIncidencias = listaIncidencias.size();
			model.addAttribute("incidencias", listaIncidencias);
			model.addAttribute("nombreUsuario", nombreUsuario);
			model.addAttribute("idUsuario", idUsuario);
			model.addAttribute("isHome", true);
			model.addAttribute("totalIncidencias", totalIncidencias);
		}

		return "home";
	}

	private Long getUserId(Authentication authentication) {
		if (authentication == null) {
			// Opcionalmente, puedes obtener el objeto Authentication de
			// SecurityContextHolder si no se pasa como parámetro
			authentication = SecurityContextHolder.getContext().getAuthentication();
		}
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			if (userDetails instanceof CustomUserDetails) {
				return ((CustomUserDetails) userDetails).getId();
			}
		}
		return null;

	}

	@GetMapping(value = { "/login" })
	public String login(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
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
		return "login";
	}

	@GetMapping("/incidencias")
	public String gestionIncidencias(Model model) {
		MySqlConnection objMySqlConnection = new MySqlConnection();
		try {

			objMySqlConnection.open();

			List<Incidencia> listaIncidencias = new ArrayList<>();
			ResultSet rs = objMySqlConnection.executeSelect(
					"SELECT Incidencias.*, Usuarios.Nombre AS UsuarioNombre, Equipos.Marca AS EquipoMarca, Equipos.Modelo AS EquipoModelo "
							+ "FROM Incidencias " + "JOIN Usuarios ON Incidencias.ID_Usuario = Usuarios.ID_Usuario "
							+ "JOIN Equipos ON Incidencias.ID_Equipo = Equipos.ID_Equipo;");
			while (rs != null && rs.next()) {
				Incidencia incidencia = new Incidencia(rs);
				listaIncidencias.add(incidencia);
			}

			model.addAttribute("incidencias", listaIncidencias);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			objMySqlConnection.close();
		}

		return "incidencias";
	}

	@GetMapping("/usuarios")
	public String gestionUsuarios(Model model) {

		System.out.println("Gestión de usuarios");
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

	@PostMapping(value = "/agregarUsuario")
	public String agregarUsuario(
			@RequestParam("nombre") String nombre, 
			@RequestParam("contraseña") String contraseña,
			@RequestParam("email") String email, 
			@RequestParam("rol") String rol, Model model,
			RedirectAttributes redirectAttributes) {
		
		
		
		System.out.println("Agregar usuario");
		MySqlConnection objMySqlConnection = new MySqlConnection();
		objMySqlConnection.open();
		String sql = "INSERT INTO Usuarios (nombre, contraseña, email, rol) VALUES ('" + nombre + "', '" + contraseña
				+ "', '" + email + "', '" + rol + "')";
		System.out.println(sql);
		ResultSet rs = objMySqlConnection.executeInsert(sql);
		try {
			if (rs != null) {
				List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_" + rol);
	            UserDetails user = User.withUsername(nombre)
	                                   .password("{noop}" + contraseña) // Usa un PasswordEncoder para producción
	                                   .authorities(authorities)
	                                   .build();
	            InMemoryUserDetailsManager.createUser(user);

	            redirectAttributes.addFlashAttribute("mensaje", "Usuario agregado correctamente.");
	            return "redirect:/usuarios"; // Redirige a la página de usuarios
			} else {
				// Error en la operación
				redirectAttributes.addFlashAttribute("error", "Error al agregar el usuario. Inténtelo de nuevo.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			objMySqlConnection.close();
		}
		
		
		return "redirect:/usuarios"; // Página de gestión de usuarios

	}

	@PostMapping("/borrarUsuario")
	public String borrarUsuario(@RequestParam("idUsuario") String idUsuario) {
		int idUsuarioInt = Integer.parseInt(idUsuario);
		System.out.println("Borrar usuario " + idUsuarioInt);

		MySqlConnection objMySqlConnection = new MySqlConnection();
		objMySqlConnection.open();

		try {
			int numRowsAffected = objMySqlConnection.deleteUserById(idUsuarioInt);
			System.out.println("Número de usuarios borrados: " + numRowsAffected);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			objMySqlConnection.close();
		}

		return "redirect:/usuarios"; // Redirige a la página de usuarios después del borrado
	}

	@GetMapping("/equipos")
	public String gestionEquipos(Model model) {
		System.out.println("Gestión de equipos");
		MySqlConnection objMySqlConnection = new MySqlConnection();
		objMySqlConnection.open();

		List<Equipo> listaEquipos = new ArrayList<>();
		try {
			ResultSet rs = objMySqlConnection.executeSelect("SELECT * FROM Equipos;");
			while (rs != null && rs.next()) {
				Equipo equipo = new Equipo(rs);
				listaEquipos.add(equipo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			objMySqlConnection.close();
		}

		model.addAttribute("equipos", listaEquipos);
		return "equipos"; // Asegúrate de que este es el nombre correcto de tu plantilla Thymeleaf
	}

	@PostMapping(value = "/agregarEquipo")
	public String agregarEquipo(@RequestParam("tipo") String tipo, @RequestParam("marca") String marca,
			@RequestParam("modelo") String modelo, @RequestParam("descripcion") String descripcion, Model model,
			RedirectAttributes redirectAttributes) {
		System.out.println("Agregar equipo");
		MySqlConnection objMySqlConnection = new MySqlConnection();
		objMySqlConnection.open();
		String sql = "INSERT INTO equipos (tipo, marca, modelo, descripción) VALUES ('" + tipo + "', '" + marca + "', '"
				+ modelo + "', '" + descripcion + "')";
		System.out.println(sql);
		ResultSet rs = objMySqlConnection.executeInsert(sql);
		try {
			if (rs != null) {
				// Operación exitosa
				redirectAttributes.addFlashAttribute("mensaje", "Equipo agregado correctamente.");
				return "redirect:/equipos"; // Redirige a la página de usuarios
			} else {
				// Error en la operación
				redirectAttributes.addFlashAttribute("error", "Error al agregar el equipo. Inténtelo de nuevo.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			objMySqlConnection.close();
		}
		return "redirect:/equipos"; // Redirige a la página de gestión de equipos
	}

	@PostMapping("/borrarEquipo")
	public String borrarEquipo(@RequestParam("idEquipo") String idEquipo) {
		int idEquipoInt = Integer.parseInt(idEquipo);
		System.out.println("Borrar Equipo " + idEquipo);

		MySqlConnection objMySqlConnection = new MySqlConnection();
		objMySqlConnection.open();

		try {
			int numRowsAffected = objMySqlConnection.deleteEquipoById(idEquipoInt);
			System.out.println("Número de equipos borrados: " + numRowsAffected);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			objMySqlConnection.close();
		}

		return "redirect:/equipos"; // Redirige a la página de usuarios después del borrado
	}
	
	//Servicio rest para la consulta de equipos
	@GetMapping("/buscarEquipos")
	public ResponseEntity<List<Equipo>> buscarEquipos() {
		MySqlConnection objMySqlConnection = new MySqlConnection();
		objMySqlConnection.open();
		List<Equipo> resultados = new ArrayList<>();
		try {
			ResultSet rs = objMySqlConnection.executeSelect("SELECT * FROM Equipos;");
			while (rs != null && rs.next()) {
				Equipo equipo = new Equipo(rs);
				resultados.add(equipo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			objMySqlConnection.close();
		}
	    return ResponseEntity.ok(resultados);
	}
	
	//Agregar incidencia
	@PostMapping(value = "/agregarIncidencia")
	public String agregarIncidencia(
			 @RequestParam("idUsuario") int idUsuario,
	            @RequestParam("equipo") int equipo,
	            @RequestParam("titulo") String titulo,
	            @RequestParam("descripcion") String descripcion,
	            @RequestParam("ubicacion") String ubicacion,
	            @RequestParam("fechaCreacion") String fechaCreacion,
	            @RequestParam("prioridad") String prioridad,RedirectAttributes redirectAttributes) { 
		System.out.println("Agregar incidencia");
		// La fecha de creación recibida como un String       
        
        // Definir el formateador para parsear la fecha de creación
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm - dd/MM/yyyy");
        
        // Parsear la fecha de creación a un objeto LocalDateTime
        LocalDateTime dateTime = LocalDateTime.parse(fechaCreacion, formatter);
        
        // Convertir la fecha al formato DATETIME de MySQL (yyyy-MM-dd HH:mm:ss)
        String formattedDate = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        // Ahora puedes usar `formattedDate` para insertarlo en la base de datos
        System.out.println(formattedDate);
		MySqlConnection objMySqlConnection = new MySqlConnection();
		objMySqlConnection.open();
		
		String sql = "INSERT INTO db_inside.incidencias (ID_Usuario, ID_Equipo, Titulo, Descripción, Ubicación, FechaHoraAsignación, Estado, Prioridad) "
				+ "VALUES ("+idUsuario+", "+equipo+", '"+titulo+"', '"+descripcion+"', '"+ubicacion+"', '"+formattedDate+"', '"
						+ "Pendiente', '"+prioridad+"')";

		System.out.println(sql);
		ResultSet rs = objMySqlConnection.executeInsert(sql);
		try {
			
			if (rs != null) {
				// Operación exitosa
				redirectAttributes.addFlashAttribute("mensaje", "Equipo agregado correctamente.");
				return "redirect:/index"; // Redirige a la página de usuarios
			} else {
				// Error en la operación
				System.out.println("Error");
				redirectAttributes.addFlashAttribute("error", "Error al agregar el equipo. Inténtelo de nuevo.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			objMySqlConnection.close();
		}
		return "home"; // Redirige a la página de gestión de equipos
	}
	
	 	@GetMapping("/registrarIncidencia")	    
	    public String registrarIncidencia(@RequestParam(name = "idEquipo") int idEquipo, Model model) {
	 	model.addAttribute("idEquipo", idEquipo);
        System.out.println("Registrar incidencia del equipo idEquipo: "+idEquipo);
        return "registrarIncidencia";
    }


}
