package com.literalura.challenge.repository;

import com.literalura.challenge.modelosDB.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor,Long> {
    Optional<Autor> findByNombreAutor(String s);

    @Query("SELECT a FROM Autor a WHERE a.anioNacimiento BETWEEN :startYear AND :endYear OR a.anioMuerte BETWEEN :startYear AND :endYear")
    List<Autor> findAutoresEntreAnios(@Param("startYear") Integer startYear, @Param("endYear") Integer endYear);

    @Query("SELECT a FROM Autor a WHERE LOWER(a.nombreAutor) LIKE LOWER(concat('%', :nombre, '%'))")
    Optional<Autor> encuentraAutorPorNombre(@Param("nombre") String nombre);

    @Query("SELECT a FROM Autor a WHERE a.anioNacimiento >= :milenio AND a.anioNacimiento < :milenioMas")
    List<Autor> encuentraAutoresPorMilenio(@Param("milenio") Integer year,@Param("milenioMas") Integer yearMore);

}
