package com.app.semantica.controller;


import com.app.semantica.service.SemanticoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("*")
public class SemanticoController {
    @Autowired
    private SemanticoService Analizador;
    @PostMapping("/semantica")
    public ResponseEntity<Map<String,Object>> semantica(@RequestBody Map<String,Object> data){
        return ResponseEntity.ok(Analizador.analizadorMapa(data));
    }


}
