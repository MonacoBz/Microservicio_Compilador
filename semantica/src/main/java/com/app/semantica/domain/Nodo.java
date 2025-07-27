package com.app.semantica.domain;

public class Nodo {
    public Token token;
    public Nodo nodoIzq;
    public Nodo nodoDer;
    public Nodo(Token t){token=t;}
    public Nodo(){}
    public Nodo getNodoDer() {
        return nodoDer;
    }

    public void setNodoDer(Nodo nodoDer) {
        this.nodoDer = nodoDer;
    }

    public Nodo getNodoIzq() {
        return nodoIzq;
    }

    public void setNodoIzq(Nodo nodoIzq) {
        this.nodoIzq = nodoIzq;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
