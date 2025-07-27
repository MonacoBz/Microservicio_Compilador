package com.app.lexema.service;


import com.app.lexema.domain.Lexer;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ParserService {


    public Map<String,Object> getTokens(String data) {
        Lexer lexer =  new Lexer();
        return lexer.listaToken(data);
    }
}
