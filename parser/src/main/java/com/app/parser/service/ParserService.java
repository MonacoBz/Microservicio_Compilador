package com.app.parser.service;

import com.app.parser.domain.ArbolAst;
import com.app.parser.domain.Token;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class ParserService {
//    public ArbolAst obtenExpresiones(List<Token> lista){
//        List<String> error = compruebaLista(lista);
//        if(!error.isEmpty())return new ArbolAst(new ArrayList<>());
//        if(lista.getFirst().token().equals("PalabraReservada"))return new ArbolAst(lista.subList(1,lista.size()-1));
//        return new ArbolAst(lista);
//    }
    public Map<String,Object> obtenExpresiones(List<Token>lista){
        Map<String,Object>mapa=new HashMap<>();
        List<String> error = compruebaLista(lista);
        mapa.put("Errores",error);
        if(!error.isEmpty())mapa.put("Arbol",new ArbolAst(new ArrayList<>()));
        else if(lista.getFirst().token().equals("PalabraReservada"))mapa.put("Arbol",new ArbolAst(lista.subList(1,lista.size()-1)));
        else mapa.put("Arbol",new ArbolAst(lista.subList(0,lista.size()-1)));
        return mapa;
    }
    private List<String> compruebaLista(List<Token>lista){
        List<String> errores = new ArrayList<>();
        if(!lista.stream().map(Token::lexema).toList().contains("="))errores.add("falta el =");
        for(Token t:lista){
            if(t.lexema().equals("{")||t.lexema().equals("}")||t.token().equals("TokenInvalido"))errores.add("Tu expresión contiene: "+t.lexema());
        }
        if(!errores.isEmpty()){
        int reservada = 0,identificador=0,operador=0,literal=0,pA=0,pC=0;
        for (Token t : lista) {
            switch (t.token()) {
                case "PalabraReservada" -> {
                    reservada += 1;
                    if (reservada == 2)
                        errores.add("No puede haber más de una palabra reservada en la misma expresión");
                }
                case "Identificadores" -> identificador += 1;

                case "Operadores" -> {
                    operador++;
                    if (t.lexema().equals("=")) {
                        if (operador > 1) errores.add("hay más de un operador =");
                        else if (literal > 0) errores.add("no puedo haber un valor literal antes del =");
                        else if (identificador > 1)
                            errores.add("no puede haber más de dos identificadores antes del =");
                        operador = 0;
                        identificador = 0;
                    }
                }
                case "ValoresLiterales" -> literal++;

                case "Delimitadores" -> {
                    if (t.lexema().equals(";")) {
                        if (pA - pC != 0) errores.add("Sobran ( o faltan )");
                        else if(identificador+literal==0)errores.add("No hay valores válidos (identificadores o literales) en la expresión");
                        else if ((identificador + literal) - operador != 1) errores.add("tienes un operador de sobra");
                        reservada = 0;
                        identificador = 0;
                        literal = 0;
                    }
                    if (t.lexema().equals("(")) pA += 1;
                    if (t.lexema().equals(")")) pC += 1;
                }
            }
        }
        }
        errores.addAll(parentesisVacios(lista));
        return errores;
    }
    private List<String> parentesisVacios(List<Token>lista){
        for (int i = 0; i < lista.size() - 1; i++) {
            Token actual = lista.get(i);
            Token siguiente = lista.get(i + 1);

            if (actual.lexema().equals("(") && siguiente.lexema().equals(")")) {
                return List.of("Paréntesis vacío detectado");
            }
        }
        return new ArrayList<>();
    }
    private static boolean parentesisBalanceados(String expr) {
        int contador = 0;
        for (char c : expr.toCharArray()) {
            if (c == '(') contador++;
            else if (c == ')') contador--;
            if (contador < 0) return false; // cierre sin apertura
        }
        return contador == 0;

    }

    private boolean tieneParentesisRedundantes(String expresion) {
        expresion = expresion.trim();
        while (expresion.contains("(") && expresion.contains(")")) {
            int balance = 0;
            boolean esRedundante = true;
            for (int i = 0; i < expresion.length(); i++) {
                char c = expresion.charAt(i);
                if (c == '(') balance++;
                else if (c == ')') balance--;

                if (balance == 0 && i < expresion.length() - 1) {
                    esRedundante = false;
                    break;
                }
            }
            if (esRedundante) {
                // elimina los paréntesis externos
                expresion = expresion.substring(1, expresion.length() - 1).trim();
            } else {
                return false; // paréntesis no son completamente externos
            }
        }
        return true; // se eliminaron todos los paréntesis externos y todo seguía balanceado
    }
}
