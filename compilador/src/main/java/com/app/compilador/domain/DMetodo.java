package com.app.compilador.domain;

import java.util.List;

public record DMetodo(List<String>modificadores,String retorno,String nombre,Parametro parametros,List<Dvariable>variables) {

    @Override
    public String toString() {
        StringBuilder sb= new StringBuilder();
        sb.append("Declaracion Metodo")
                .append("\n\t\t\t").append("nombre=")
                .append(nombre)
                .append("\n\t\t\t").append("retorno=")
                .append(retorno)
                .append("\n\t\t\t")
                .append("Modificadores=");
        modificadores.forEach(m->sb.append(m).append(" "));
        sb.append("\n\t\t\t").append("parametro=")
                .append(parametros)
                .append("\n\t\t\t")
                .append("Declaracion Variables:");
        variables.forEach(v->sb.append(v));
        return sb.toString();
    }
}
