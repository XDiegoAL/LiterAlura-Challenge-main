package com.literalura.challenge.modelosurl;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResultsUrl(
        @JsonAlias("id") Integer idWeb,
        @JsonAlias("title") String tituloLibro,
        @JsonAlias("authors")List<AutoresUrl> autores,
        @JsonAlias("subjects")List<String> categorias,
        @JsonAlias("languages")List<String> lenguajesLibro,
        @JsonAlias("download_count") Integer nDescargas
) {
}
