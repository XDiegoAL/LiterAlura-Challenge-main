package com.literalura.challenge.modelosDB;

import com.literalura.challenge.modelosurl.AutoresUrl;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreAutor;
    private Integer anioNacimiento;
    private Integer anioMuerte;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();

    public Autor(){}

    public Autor(AutoresUrl autoresUrl){
        this.nombreAutor = autoresUrl.nombreAutor();
        this.anioNacimiento = (autoresUrl.anioNacimiento() != null)?autoresUrl.anioNacimiento():0;
        this.anioMuerte = (autoresUrl.anioMuerte()!=null)?autoresUrl.anioMuerte():5000;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public Integer getAnioNacimiento() {
        return anioNacimiento;
    }

    public void setAnioNacimiento(Integer anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }

    public Integer getAnioMuerte() {
        return anioMuerte;
    }

    public void setAnioMuerte(Integer anioMuerte) {
        this.anioMuerte = anioMuerte;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }



    @Override
    public String toString() {
        return "\n------------ Autor ------------\n" +
                "Nombre='" + nombreAutor + '\n' +
                "Año de nacimiento=" + anioNacimiento + '\n' +
                "Año de muerte=" + anioMuerte + '\n' +
                "Libros registrados=" + (libros != null ? libros.stream().map(Libro::getTituloLibro).collect(Collectors.toList()) : "Sin libros") + '\n' +
                "--------------------------------\n";
    }
}
