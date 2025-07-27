package com.app.compilador.domain;

public record Parametro(String tipo,String nombre) {
    @Override
    public String toString() {
        StringBuilder sb= new StringBuilder();
        sb.append("nombre:")
                .append(nombre)
                .append(" ,tipo:")
                .append(tipo);
        return sb.toString();
    }
}
