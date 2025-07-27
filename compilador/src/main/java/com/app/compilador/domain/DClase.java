package com.app.compilador.domain;

import java.util.List;

public record DClase(List<String>modificadores, String nombre, DMetodo metodo) {
    @Override
    public String toString(){
        StringBuilder sb= new StringBuilder();
        sb.append("DeclaracionClase:")
                .append("\n\t\t")
                .append("nombre= ")
                .append(nombre)
                .append("\n\t\t")
                .append("Modificadores=");
        modificadores.forEach(m->sb.append(m).append(" "));
        sb.append("\n\t\t")
                .append(metodo);
        return sb.toString();
    }
}
