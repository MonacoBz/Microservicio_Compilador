package com.app.semantica.domain;

import java.util.*;

public class AnalizadorSemantico {
    public  Map<String,String> tablaSimbolos=new HashMap<>();
    private String nombre;
    private String tipo;
    private List<String>expresion;
    int size;
    private List<String> tipos=new ArrayList<>();
    private List<String> errores=new ArrayList<>();
    private Map<String,Object>mapa = new HashMap<>();
    public AnalizadorSemantico(ArbolAst arbol,Token tipo) {
        List<Token> data = obtenlista(arbol.raiz);
        List<String> dataS = new LinkedList<>(data.stream()
                .map(Token::lexema)
                .toList());
        if (tipo.token().equals("PalabraReservada"))((LinkedList<String>) dataS).addFirst(tipo.lexema());
        tablaSimbolos.putAll(configura(dataS));
        expresion=data.subList(size,data.size()).stream()
                .map(Token::lexema)
                .toList();
    }
    public AnalizadorSemantico(List<ArbolAst> arboles,List<Token> tipos) {
        for (int i = 0; i <arboles.size(); i++) {
            List<Token> data = obtenlista(arboles.get(i).raiz);
            if(data.isEmpty())continue;
            List<String> dataS = new LinkedList<>(data.stream()
                    .map(Token::lexema)
                    .toList());
            if (tipos.get(i).token().equals("PalabraReservada"))((LinkedList<String>) dataS).addFirst(tipos.get(i).lexema());
            tablaSimbolos.putAll(configura(dataS));
            expresion=data.subList(size,data.size()).stream()
                    .map(Token::lexema)
                    .toList();
            analizaAsignacion();
        }
        mapa.put("Tipos",this.tipos);
        mapa.put("Errores",this.errores);
    }
    public Map<String,Object> obtenMapa(){
        return mapa;
    }
    private Map<String,String>configura(List<String>data){
        Map<String,String>mapa = new HashMap<>();
        size=data.subList(0,data.indexOf("=")).size();
        if(size==2){
            nombre=data.get(1);
            System.out.println(size);
            tipo=(tablaSimbolos.containsKey(nombre))?"Declarada":data.getFirst();
            mapa.put(nombre,tipo);

        }else{
            nombre=data.getFirst();
            this.tipo=(tablaSimbolos.containsKey(nombre))?"Existente":"Desconocido";

        }
        return mapa;
    }
    private void analizaAsignacion(){
        switch (tipo) {
            case "Desconocido" -> {
                tipos.add("");
                errores.add("El valor no se a declarado " + nombre);
                return;
            }
            case "Declarada" -> {
                tipos.add("");
                errores.add("El valor ya se a declarado " + nombre);
                return;
            }
            case "Existente" -> {
                tipo = tablaSimbolos.get(nombre);
                tipos.add(tipo);
            }
            default -> tipos.add(tipo);
        }
        for(String num :expresion){
            if(!(num.equals("+")||num.equals("=")||num.equals("-")||num.equals("/")||num.equals("*"))) {
                try {
                    switch (tipo) {
                        case "int" -> Integer.parseInt(num);
                        case "double" -> Double.parseDouble(num);
                        case "long" -> Long.parseLong(num);
                        case "short" -> Short.parseShort(num);
                        case "byte" -> Byte.parseByte(num);
                        case "float" -> Float.parseFloat(num);
                        case "boolean" -> {
                            if(!(num.equals("true")||num.equals("false")))throw new NumberFormatException();
                        }
                        default -> errores.add("Error en el Token: "+num);
                    }
                    ;
                }catch (NumberFormatException e){
                    errores.add("Error en la promoción a "+tipo+": "+num);
                }
            }
        }
    }
    public List<Token> obtenlista(Nodo raiz){
        List<Token> orden = new ArrayList<>();
        listainOrder(raiz,orden);
        return orden;
    }
    public void listainOrder(Nodo nodo,List<Token>lista){
        if(nodo!=null){
            listainOrder(nodo.nodoIzq,lista);
            lista.add(nodo.token);
            listainOrder(nodo.nodoDer,lista);
        }
    }
}
