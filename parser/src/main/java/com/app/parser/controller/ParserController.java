package com.app.parser.controller;


import com.app.parser.domain.ArbolAst;
import com.app.parser.domain.Token;
import com.app.parser.service.ParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin("*")
public class ParserController {

    @Autowired
    private ParserService servicio;
    @PostMapping("/Parser")
    public ResponseEntity<Map<String,Object>> obtenArbol(@RequestBody List<Token> expresion){

        return ResponseEntity.ok(servicio.obtenExpresiones(expresion));
    }
}
