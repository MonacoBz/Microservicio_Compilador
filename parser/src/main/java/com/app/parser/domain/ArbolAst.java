package com.app.parser.domain;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class ArbolAst {
    public Nodo raiz;

    public ArbolAst() {
    }

    public Nodo getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
    }

    public ArbolAst(List<Token>expresion){
        raiz=arbol(expresion,raiz);
    }
    private Nodo arbol(List<Token>lista,Nodo nodo){
        if(lista.isEmpty())return nodo;
        List<String> lexemas=lista.stream().map(Token::lexema).toList();
        lexemas.forEach(System.out::print);
        System.out.println();
        int menor = buscaMenor(lexemas);
        int posicion=buscaPosicion(menor,lexemas);
        if(posicion==-1) return nodo;
        if(nodo==null){
            nodo = new Nodo(lista.get(posicion));
        }
        List<Token> izq = creaSublista(lista.subList(0,posicion));
        List<Token> der = creaSublista(lista.subList(posicion+1,lista.size()));
        nodo.nodoDer=(der.size()==1)?new Nodo(der.getFirst()):arbol(der,nodo.nodoDer);
        nodo.nodoIzq=(izq.size()==1)?new Nodo(izq.getFirst()):arbol(izq,nodo.nodoIzq);
        return nodo;
    }

    private int buscaMenor(List<String>lista){
        List<String>lexema = ignoraParentesis(lista);
        return (lexema.contains("="))?1:
                (lexema.contains("+")||lexema.contains("-"))?2:
                        (lexema.contains("/")||lexema.contains("*"))?3:-1;
    }

    private int buscaPosicion(int menor,List<String> listaS){
        //movi aqui
        List<String>lista=ignoraParentesis(listaS);
        return switch (menor){
            case 1:yield lista.indexOf("=");
            case 2:{
                yield (lista.contains("+")&&lista.contains("-"))?
                        Math.max(lista.lastIndexOf("+"),lista.lastIndexOf("-")):
                        (lista.lastIndexOf("+")!=-1)?lista.lastIndexOf("+"):lista.lastIndexOf("-");
            }
            case 3:{
                yield (lista.contains("*")&&lista.contains("/"))?
                        Math.max(lista.lastIndexOf("*"),lista.lastIndexOf("/")):
                        (lista.lastIndexOf("*")!=-1)?lista.lastIndexOf("*"):lista.lastIndexOf("/");
            }
            default:yield -1;
        };
    }

    private List<String>ignoraParentesis(List<String>lista){
        if(!lista.contains("("))return lista;
        List<String>resultado=new ArrayList<>();
        int balance = 0;
        for(String lexema:lista){
            if(lexema.equals("(")){
                balance++;
                resultado.add("_");
                continue;
            }else if(lexema.equals(")")){
                balance--;
                resultado.add("_");
                continue;
                }
            if(balance==0)resultado.add(lexema);
            else resultado.add("_");
        }
        resultado.forEach(System.out::print);
        System.out.println();
        return resultado;
    }

    private List<Token>creaSublista(List<Token> lista){
        if(!esParentesis(lista.stream().map(Token::lexema).toList()))return lista;
        return eliminaParentesisInecesarios(lista);
    }
    private List<Token> eliminaParentesisInecesarios(List<Token> lista) {
        List<Token> actual = lista;
        List<Token> nueva;
        do {
            nueva = eliminaParentesis(actual);
            if (nueva.size() == actual.size()) break;
            actual = nueva;
        } while (true);
        return actual;
    }
    private boolean esParentesis(List<String>lista){
        lista=ignoraParentesis(lista);
        for(String t:lista){
            if(!t.equals("_"))return false;
        }
        return true;
    }

    private List<Token> eliminaParentesis(List<Token> lista){
        if(lista.isEmpty())return lista;
        Deque<Token> stack = new ArrayDeque<>();
        List<Token> nuevaLista = new ArrayList<>();
         for(Token token: lista){
             if(token.lexema().equals("(")){
                 if(!stack.isEmpty())nuevaLista.add(token);
                 stack.push(token);
             }else if(token.lexema().equals(")")){
                 stack.pop();
                 if(!stack.isEmpty())nuevaLista.add(token);
             }else nuevaLista.add(token);
         }

         return nuevaLista;
    }
}
