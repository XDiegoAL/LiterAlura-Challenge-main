package com.literalura.challenge.modelosurl;

import java.text.Normalizer;
import java.util.regex.Pattern;

public enum Lenguajes {
    ESPAÑOL("es","español"),
    INGLES("en","ingles"),
    FRANCES("fr","frances"),
    PORTUGUES("pt","portugues");

    private String categoriaLenguaje;
    private String getCategoriaLenguajeEspanol;

    Lenguajes(String lenguaje,String esp){
        this.categoriaLenguaje = removeAccents(lenguaje.toLowerCase());
        this.getCategoriaLenguajeEspanol = removeAccents(esp.toLowerCase());
    }

    public static Lenguajes fromString(String text){
        for (Lenguajes lenguaje: Lenguajes.values()) {
            if(lenguaje.categoriaLenguaje.equalsIgnoreCase(removeAccents(text.toLowerCase()))){
                return lenguaje;
            }
        }
        throw new IllegalArgumentException("Ningún lenguaje encontrado: "+text);
    }

    public static Lenguajes fromEspanol(String text){
        for (Lenguajes lenguaje: Lenguajes.values()) {
            if(lenguaje.getCategoriaLenguajeEspanol.equalsIgnoreCase(removeAccents(text.toLowerCase()))){
                return lenguaje;
            }
        }
        throw new IllegalArgumentException("Ningún lenguaje encontrado: "+text);
    }


    public static String removeAccents(String input) {
        // Normaliza el texto para descomponer los caracteres acentuados en sus formas básicas
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        // Utiliza una expresión regular para eliminar los caracteres diacríticos
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }
}
