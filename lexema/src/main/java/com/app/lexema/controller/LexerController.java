package com.app.lexema.controller;



import com.app.lexema.service.ParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin(value="*")
@Validated
public class LexerController {
    @Autowired
    private ParserService LEXEMA;



    @PostMapping("/Lexema")
    public ResponseEntity<Map<String,Object>> getTokens(@RequestBody String data){
        return ResponseEntity.ok(LEXEMA.getTokens(data));
    }
}
