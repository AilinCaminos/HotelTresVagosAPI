package ar.com.ada.api.hoteltresvagos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;
import ar.com.ada.api.hoteltresvagos.entities.*;

@Repository
public interface HuespedRepository extends JpaRepository<Huesped, Integer> {
    Huesped findByNombre(String nombreHuesped);

    Huesped findByDni(Integer dni);

    // @Query arriba del metodo que queremos "crear" por interface
    //se hace SELECT sobre el Objeto Huesped. En este
    // caso se usa un Like para traer aquellos que contengan ese nombre
    // El parametro ?1 indica que al query se le pasara UN parametro.
    @Query("FROM Huesped WHERE nombre like CONCAT('%', ?1,'%')")
    List<Huesped> findAllByNombreContiene(String nombre);

    // Igual al caso anterior pero con 2 parametros.
    @Query("SELECT h FROM Huesped h WHERE h.nombre = ?1 AND h.dni = ?2")
    List<Huesped> findAllByNombreAndDNI(String nombre, Integer dni);
    
    // En este caso hace un select sobre las huespedes ordenada por nombre
    // recordar que esta sintaxis es JPQL
    @Query("select h from Huesped h order by nombre")
    List<Huesped> findAllOrderByNombre();

    // Mismo caso anterior, salvo que en este se le pone "nombre" al paremtro. En
    // nuestro caso el nombre del parametro es ":dni"
    @Query("SELECT h FROM Huesped h WHERE h.dni = :dni")
    List<Huesped> findByDNIVersion2(@Param("dni") Integer descripcion);

}