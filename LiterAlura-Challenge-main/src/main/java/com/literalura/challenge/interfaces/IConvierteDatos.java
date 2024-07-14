package com.literalura.challenge.interfaces;

public interface IConvierteDatos {
    <T> T adquiereDatos(String json,Class<T> clase);
}
