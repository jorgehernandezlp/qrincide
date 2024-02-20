package es.prw.repositories;

import es.prw.models.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {
    // Aquí puedes definir métodos personalizados si los necesitas
}