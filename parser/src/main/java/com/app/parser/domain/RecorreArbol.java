package com.app.parser.domain;

public class RecorreArbol {
    ArbolAst arbol;

    public RecorreArbol(ArbolAst arbol) {
        this.arbol = arbol;
    }
    public String []obtenOrdenes(){
        StringBuilder preOrderResult = new StringBuilder();
        StringBuilder inOrderResult = new StringBuilder();
        StringBuilder postOrderResult = new StringBuilder();
        postOrder(arbol.raiz,postOrderResult);
        preOrder(arbol.raiz,preOrderResult);
        inOrder(arbol.raiz,inOrderResult);
        return new String[]{postOrderResult.toString(),preOrderResult.toString(),inOrderResult.toString()};
    }

    private void postOrder(Nodo nodo,StringBuilder dato){
        if(nodo!=null){
            postOrder(nodo.nodoIzq,dato);
            postOrder(nodo.nodoDer,dato);
            dato.append(nodo.token.lexema());
        }
    }

    private void inOrder(Nodo nodo,StringBuilder dato){
        if(nodo!=null){
            inOrder(nodo.nodoIzq,dato);
            dato.append(nodo.token.lexema());
            inOrder(nodo.nodoDer,dato);
        }

    }

    private void preOrder(Nodo nodo, StringBuilder dato){
        if(nodo!=null){
            dato.append(nodo.token.lexema());
            preOrder(nodo.nodoIzq,dato);
            preOrder(nodo.nodoDer,dato);
        }
    }
}
