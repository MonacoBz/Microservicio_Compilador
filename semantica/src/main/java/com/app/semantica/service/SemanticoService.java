package com.app.semantica.service;

import com.app.semantica.domain.AnalizadorSemantico;
import com.app.semantica.domain.ArbolAst;
import com.app.semantica.domain.Token;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SemanticoService {


//    public Map<String,Object> analizadorMapa(Map<String,Object> info){
//        ObjectMapper mapper = new ObjectMapper();
//        ArbolAst arbol = mapper.convertValue(info.get("Arbol"), ArbolAst.class);
//        Token tipo = mapper.convertValue(info.get("Token"),Token.class);
//        return new AnalizadorSemantico(arbol,tipo).iniciaAnalizador();
//    }
   public Map<String,Object> analizadorMapa(Map<String,Object>data){
       ObjectMapper mapper = new ObjectMapper();
       List<Map<String, Object>> rawList = (List<Map<String, Object>>) data.get("arboles");
       List<ArbolAst> arboles = rawList.stream()
               .map(item -> mapper.convertValue(item, ArbolAst.class))
               .toList();
       List<Map<String, Object>> tokensMap = (List<Map<String, Object>>) data.get("tokens");
       List<Token> tokens = tokensMap.stream()
               .map(t -> new Token(
                       t.get("token").toString(),
                       t.get("lexema").toString()))
               .toList();
       return new AnalizadorSemantico(arboles,tokens).obtenMapa();
   }

}
