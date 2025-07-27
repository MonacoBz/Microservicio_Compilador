package com.app.compilador.domain;

import java.util.ArrayList;
import java.util.List;

public class Dvariable {
    String tipo;
    ArbolAst arbol;
    List<Token> expresion;

    public Dvariable( List<Token> expresion, String tipo) {
        this.expresion = expresion;
        this.tipo = tipo;
    }

    public ArbolAst getArbol() {
        return arbol;
    }

    public void setArbol(ArbolAst arbol) {
        this.arbol = arbol;
    }

    public List<Token> getExpresion() {
        return expresion;
    }

    public void setExpresion(List<Token> expresion) {
        this.expresion = expresion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<String>arbolR = obtenlista().stream().map(Token::lexema).toList();
        sb.append("\n\t\t\t\t")
                .append("tipo=")
                .append(tipo)
                .append("\n\t\t\t\t");
            sb.append("arbol: ");
            arbolR.forEach(sb::append);

        return sb.toString();
    }
    private List<Token> obtenlista(){
        List<Token> orden = new ArrayList<>();
        listainOrder(arbol.raiz,orden);
        return orden;
    }
    private void listainOrder(Nodo nodo,List<Token>lista){
        if(nodo!=null){
            listainOrder(nodo.nodoIzq,lista);
            lista.add(nodo.token);
            listainOrder(nodo.nodoDer,lista);
        }
    }
}
