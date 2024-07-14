package com.literalura.challenge.modelosDB;

import com.literalura.challenge.modelosurl.Lenguajes;
import com.literalura.challenge.modelosurl.ResultsResponse;
import com.literalura.challenge.modelosurl.ResultsUrl;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer idWeb;
    private String tituloLibro;
    private String categoria;

    private Lenguajes lenguaje;

    int nDescargas;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    public Libro(){}

    public Libro(ResultsUrl resultsUrl) {
        this.idWeb = resultsUrl.idWeb();
        this.tituloLibro = resultsUrl.tituloLibro();
        this.categoria = resultsUrl.categorias().get(0);
        this.lenguaje = Lenguajes.fromString(resultsUrl.lenguajesLibro().get(0));
        this.nDescargas = resultsUrl.nDescargas();
    }


    public Long getId() {
        return id;
    }

    public Integer getIdWeb() {
        return idWeb;
    }

    public void setIdWeb(Integer idWeb) {
        this.idWeb = idWeb;
    }

    public String getTituloLibro() {
        return tituloLibro;
    }

    public void setTituloLibro(String tituloLibro) {
        this.tituloLibro = tituloLibro;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Lenguajes getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(Lenguajes lenguaje) {
        this.lenguaje = lenguaje;
    }

    public int getnDescargas() {
        return nDescargas;
    }

    public void setnDescargas(int nDescargas) {
        this.nDescargas = nDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "\n--------- Libro ---------\n" +
                "idWeb=" + idWeb + '\n'+
                "TituloLibro='" + tituloLibro + '\n'+
                "Categoria='" + categoria + '\n'+
                "Idioma=" + lenguaje + '\n'+
                "Número de descargas=" + nDescargas+'\n'+
                "Autor: " + (autor != null ? autor.getNombreAutor() : "Sin autor") + '\n'+
                "-------------------------\n";
    }
}
