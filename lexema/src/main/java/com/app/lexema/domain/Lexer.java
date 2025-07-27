package com.app.lexema.domain;



import com.app.lexema.exceptions.TokenNoValidEx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
public Map<String,Object> listaToken (String data){
    Map<String,Object> JSON = new HashMap<>();
    List<Token> lista = new ArrayList<>();
    List<String> misEx = new ArrayList<>();
    Matcher matcher = regex().matcher(data.trim());
    List<String> Tokens = List.of("PalabraReservada","ValoresLiterales","Operadores","Delimitadores","Identificadores","TokenInvalido");
    while(matcher.find()){
        for(String token:Tokens){
            String group = matcher.group(token);

            if(group!=null){
                if(token.equals("TokenInvalido")){
                    misEx.add(new TokenNoValidEx("El token :"+group+" es invalido").getMessage());
                    continue;
                }
                lista.add(new Token(token,group));
                break;
            }
        }
    }
    JSON.put("Tokens",lista);
    JSON.put("Errores",misEx);
    return JSON;
}
private Pattern regex(){
    return Pattern.compile("(?<PalabraReservada>public|private|static|void|if|else|while|byte|short|char|int|float|long|double|boolean)|" +
            "(?<ValoresLiterales>-?[0-9]+(\\.?[0-9]*))|" +
            "(?<Operadores>[*+\\-=<>/]+)|" +
            "(?<Delimitadores>[{}()\\[\\];])|" +
            "(?<Identificadores>[A-Za-z_][A-Za-z0-9_]*)|" +
            "(?<TokenInvalido>[^A-Za-z0-9_{}()\\[\\];*+\\-=<>\\s]+)");
}
}
