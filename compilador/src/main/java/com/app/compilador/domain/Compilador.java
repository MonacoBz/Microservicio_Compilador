package com.app.compilador.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Compilador {
    public DClase clase;
    public Compilador(List<Token> datos){
        compila(datos);
    }
    private void compila(List<Token>datos){
        List<String> modificadores=new ArrayList<>();
        String nombre ="";
        int i = 0;
        for(;i<datos.size();i++){
            Token t = datos.get(i);
            if(t.token().equals("Delimitadores"))break;
            switch (t.token()){
                case("PalabraReservada")->modificadores.add(t.lexema());
                case("Identificadores")->nombre=t.lexema();
            }
        }
        DMetodo metodo = creaMetodo(datos.subList(i+1,datos.size()));
        clase=new DClase(modificadores,nombre,metodo);
    }
    private DMetodo creaMetodo(List<Token>datos){
        List<String> modificadores=new ArrayList<>();
        String retorno="";
        String nombre="";
        int i=0;
        Set<String> tiposPrimitivos = Set.of("void", "byte", "short", "char", "int", "float", "long", "double", "boolean");
        int contadorP=0;
        for(;i<datos.size();i++){
            Token t = datos.get(i);
            if (t.lexema().equals("(")) break;
            switch (t.token()){
                case "PalabraReservada"->{
                    if(tiposPrimitivos.contains(t.lexema()))retorno=t.lexema();
                    else modificadores.add(t.lexema());
                }
                case "Identificadores"->nombre=t.lexema();
            }
        }
        int cierre = buscaCierre(datos.subList(0,datos.size()));
        Parametro parametro = obtenParametro(datos.subList(i+1,cierre));
        List<Dvariable> variables = obtenVariables(datos.subList(cierre+2,datos.size()));
        return new DMetodo(modificadores,retorno,nombre,parametro,variables);
    }

    private Parametro obtenParametro(List<Token> datos){
        String nombre = datos.getLast().lexema();
        StringBuilder tipo = new StringBuilder();
        for (int i = 0; i <datos.size()-1 ; i++) {
            tipo.append(datos.get(i).lexema());
        }
        return new Parametro(tipo.toString(),nombre);
    }

    private List<Dvariable> obtenVariables(List<Token>datos){
        List<Dvariable> variables = new ArrayList<>();
        boolean encontreE = false;
        int iniciador=0;
        int contadorP=0;
        List<Token>expresion = new ArrayList<>();
        for(int i = 0;i<datos.size();i++){
            Token t = datos.get(i);
            if(t.lexema().equals("{")||t.lexema().equals("}"))break;
            if(t.lexema().equals(";")||encontreE){
                if(!encontreE)expresion.add(t);
                variables.add(new Dvariable(expresion,""));
                iniciador=i+1;
                expresion=new ArrayList<>();
                contadorP=0;
                encontreE=false;
            }else {
                if(t.token().equals("PalabraReservada"))contadorP++;
                if(contadorP>1){
                    encontreE=true;
                    i-=2;
                }else expresion.add(t);
            }
        }
        if(!expresion.isEmpty())variables.add(new Dvariable(expresion,datos.get(iniciador).lexema()));
        return variables;
    }

    private int buscaCierre(List<Token> datos){
        int i=0;
        while(!datos.get(i++).lexema().equals(")"));
        return i-1;
    }

    @Override
    public String toString() {
        StringBuilder db = new StringBuilder();
        db.append("Compilador");
        db.append("\n\t");
        db.append(clase);
        return db.toString();
    }
}
