package com.literalura.challenge.repository;

import com.literalura.challenge.modelosDB.Autor;
import com.literalura.challenge.modelosDB.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibrosRepository extends JpaRepository<Libro,Long> {
    Optional<Libro> findByTituloLibroAndAutor_NombreAutor(String tituloLibro, String nombreAutor);
    @Query("SELECT l FROM Libro l ORDER BY l.nDescargas DESC LIMIT 10 ")
    List<Libro> top10LibrosDescargados();
}
